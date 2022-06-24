package com.joyintech.tams.common.svreport.base.service;

import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.file.SafelyFile;
import com.joyintech.framework.util.DateUtil;

import com.joyintech.framework.util.FileNameUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.report.framework.thread.JoyinThreadPoolExecutor;
import com.joyintech.tams.report.interf.common.imp.ImportGenerator;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpColumn;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpTable;
import com.joyintech.tams.systemtools.util.CommonUtil;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author zcw
 * @date 2022-03-31 14:52
 */
@Component
public class BatchInsertService {
    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(BatchInsertService.class);

    /**
     * 上游接口解析读取文件处理最大线程数
     */
    @Value("${application.interfMaxCorePoolSize}")
    private int interfMaxCorePoolSize;

    /**
     * 上游接口解析读取文件处理,单次sqlldr处理最大数据条数
     */
    @Value("${application.reportInterfOneTimeSqlLdrCommitCount}")
    private int reportInterfOneTimeSqlLdrCommitCount;

    /**
     * sqlldr用数据库连接
     */
    @Value("${spring.datasource.reportSqlldrDatabaseName}")
    private String reportSqlldrDatabaseName;

    /**
     * 上游接口解析读取文件处理,单次sqlldr处理最大数据条数
     */
    @Value("${application.reportInterfOneTimeSqlLdrDataCount}")
    private int reportInterfOneTimeSqlLdrDataCount;
    @Value("${spring.datasource.driverClassName}")
    private String driverName;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String password;

    /**
     * 批量插入共通方法共通方法
     * @param tmpPath 临时数据路径
     * @param list  数据list
     * @param table 表对象
     */
    public void exeBatchldr(String tmpPath, List<? extends Object> list, ImpTable table) {
        String tablename = table.getName();
        logger.info("执行批量插入开始---入表："+tablename +" 时间"+ DateUtil.getCurrentDateTime());
        tmpPath = tmpPath + (tmpPath.endsWith("/")?"": "/") + tablename + "/";

        // 创建一个线程池
        if (interfMaxCorePoolSize < 1) {
            interfMaxCorePoolSize = 20;
        }
        int size = list.size();
        int max = (int) Math.ceil((double) size / 1000);
        ExecutorService service = new JoyinThreadPoolExecutor( max>20?interfMaxCorePoolSize : max);
        List<Callable<Boolean>> tasks = new ArrayList<>();
        // 临时文件路径+文件名前缀
        String path = tmpPath + tablename;
        if (null != list) {
            int start = 0;
            int end = 0;
            for (int i = 0; i < max; i++) {
                start = end;
                end = Math.min(size, 1000 * (i + 1));
                List<? extends Object> subList = list.subList(start, end);
                logger.debug("生成批量文件"+i);
                tasks.add(new Callable<Boolean>() {
                    @Override
                    public Boolean call() {
                        logger.debug("生成批量文件----开始-----");
                        createDataFile(path, subList, table.getColumns());
                        logger.debug("生成批量文件----结束-----:");
                        return true;
                    }
                });
            }
        }
        // 为线程池添加任务
        List<Future<Boolean>> taskResult;
        try {
            taskResult = service.invokeAll(tasks);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
            return;
        }
        for (Future<Boolean> res : taskResult) {
            try {
                res.get();
            } catch (InterruptedException | ExecutionException e) {
                logger.error(e.getMessage(), e);
                return;
            }
        }
        // 任务结束，停止线程池的所有线程
        service.shutdown();
        Collection<File> files = FileUtils.listFiles(new SafelyFile(tmpPath), new String[] {"tmp" }, false);
        logger.debug("批量文件个数"+files.size());
        List<String> content = new ArrayList<>(list.size());
        for (File f : files) {
            if (f.getName().startsWith(tablename)) {
                try {
                    content.addAll(FileUtils.readLines(f, Charset.forName("UTF-8")));
                    logger.debug("查找批量文件"+f);
                } catch (IOException e) {
                    logger.debug("查找批量文件异常");
                    logger.error(e.getMessage(), e);
                } finally {
                    f.delete();
                }
            }
        }
        logger.debug("读取批量文件共"+content.size()+"条");
        // 系统是否使用的db2数据库
        if (CommonUtil.isDB2DataBase()) {
            // db2数据库处理
            if (CollectionUtils.isNotEmpty(content)) {
                try {
                    ImportGenerator.importToDBByPart(table, tmpPath, content,
                            reportInterfOneTimeSqlLdrDataCount, reportInterfOneTimeSqlLdrCommitCount, "del",
                            reportSqlldrDatabaseName);
                } catch (IOException | InterruptedException e) {
                    logger.error(e.getMessage(), e);
                    return;
                }
            }
        } else if(BaseCommonCheckService.isMYSQLDataBase()){
            if (StringUtil.isEmptyList(content)) {
                return ;
            }
            int dataCount = content.size();
            if (dataCount <= reportInterfOneTimeSqlLdrDataCount) {
                //临时文件
                String absoultPath = tmpPath + table.getName() + FileNameUtil.createFileName(".txt");
                File tmpFile = new SafelyFile(absoultPath);
                try {
                    FileUtils.writeLines(tmpFile, "UTF-8", content);
                    exeLoadData(absoultPath, table);
                }catch(Exception e){
                    logger.error(e.getMessage(), e);
                }finally {
                    // 删除临时文件
                    delete(absoultPath);
                }
            }else{
                List<String> newDataList = new ArrayList<String>();
                newDataList.addAll(content);
                // 分批处理
                int part = dataCount / reportInterfOneTimeSqlLdrDataCount; // 分批数
                for (int i = 0; i < part; i++ ) {
                    //临时文件
                    String absoultPath = tmpPath + table.getName() + FileNameUtil.createFileName(".txt");
                    File tmpFile = new SafelyFile(absoultPath);
                    try {
                        FileUtils.writeLines(tmpFile, "UTF-8", newDataList.subList(0, reportInterfOneTimeSqlLdrDataCount));
                        exeLoadData(absoultPath, table);
                    }catch(Exception e){
                        logger.error(e.getMessage(), e);
                    }finally {
                        // 删除临时文件
                        delete(absoultPath);
                    }
                    newDataList.subList(0, reportInterfOneTimeSqlLdrDataCount).clear();
                }
                // 最后剩下的数据
                if (CollectionUtils.isNotEmpty(newDataList)) {
                    //临时文件
                    String absoultPath = tmpPath + table.getName() + FileNameUtil.createFileName(".txt");
                    File tmpFile = new SafelyFile(absoultPath);
                    try {
                        FileUtils.writeLines(tmpFile, "UTF-8", newDataList);
                        exeLoadData(absoultPath, table);
                    }catch(Exception e){
                        logger.error(e.getMessage(), e);
                    }finally {
                        // 删除临时文件
                        delete(absoultPath);
                    }
                }
            }
        }else{
            // oracle原逻辑
            if (CollectionUtils.isNotEmpty(content)) {
                try {
                    logger.debug("sqlloader入库开始");
                    ImportGenerator.importToDBByPart(table, tmpPath, content,
                            reportInterfOneTimeSqlLdrDataCount, reportInterfOneTimeSqlLdrCommitCount, "txt",
                            reportSqlldrDatabaseName);
                    logger.debug("sqlloader入库结束");
                } catch (IOException | InterruptedException e) {
                    logger.debug("sqlloader入库报错");
                    logger.error(e.getMessage(), e);
                    return;
                }
            }
        }
        logger.info("执行sqlldr结束---入表："+tablename +" 时间"+ DateUtil.getCurrentDateTime());
    }

    /**
     * 创建数据文件
     *
     * @param path 文件路径
     * @param baseinfoList 对象列表
     * @param columns 数据库表对应的列
     */
    public void createDataFile(String path, List<? extends Object> baseinfoList, List<ImpColumn> columns) {
        // 生成txt文件
        List<String> lines = new ArrayList<>(baseinfoList.size());
        baseinfoList.forEach(item -> lines.add(ImportGenerator.buildTxt(item, columns)));
        try {
            // 临时文件路径+文件名
            FileUtils.writeLines(
                    new SafelyFile(path + GlobalConstant.UNDER_LINE + UUID.randomUUID().toString() + ".tmp"),
                    "UTF-8", lines, true);
        }
        catch (IOException e) {
            e.getMessage();
            logger.error(e.getMessage(),e);
        }
    }
    /**
     *  LOAD DATA LOCAL INFILE 大批量导入数据到 MySQL。
     *
     * @see
     * @since 1.0
     */
    public void exeLoadData(String absoultPath, ImpTable table) {

        List<ImpColumn> array = table.getColumns();
        List<String> colunm = new ArrayList<>();
        List<String> colunmDeal = new ArrayList<>();

        for (ImpColumn impColumn : array) {
            colunm.add("@"+impColumn.getName());
            colunmDeal.add(impColumn.getName() + " = CASE WHEN LENGTH("+"@"+impColumn.getName()+") = 0 THEN NULL ELSE "+"@"+impColumn.getName() + " END");
        }
        String insertColumnName = StringUtils.join(colunm, ",");

        String insertColumnNameDeal = StringUtils.join(colunmDeal, ",");

        FileInputStream fileInputStream = null;
        Connection conn = null;
        try {
            Class.forName(driverName);
            conn = DriverManager.getConnection(url, userName, password);
            File file = FileUtils.getFile(absoultPath);
            /*secure-file-priv特性
            secure-file-priv参数是用来限制LOAD DATA, SELECT ... OUTFILE, and LOAD_FILE()传到哪个指定目录的
            secure-file-priv的值为null ，表示限制mysqld 不允许导入|导出
            当secure_file_priv的值为/tmp/ ，表示限制mysqld 的导入|导出只能发生在/tmp/目录下
            当secure_file_priv的值没有具体值时，表示不对mysqld 的导入|导出做限制
            查询命令：show global variables like '%secure%';
            */
            String line = System.getProperty("os.name").toLowerCase().contains("windows")?"'\r\n'":"'\n'";
            String sql = "LOAD DATA LOCAL INFILE '"+absoultPath +"' IGNORE INTO TABLE " + table.getName() + " CHARACTER SET utf8 FIELDS TERMINATED  BY '|' LINES TERMINATED BY "+line+" (" + insertColumnName + ") set " + insertColumnNameDeal;

            logger.info("----------------sql:"+sql);
            fileInputStream = new FileInputStream(file);
            //批量插入数据。
            long beginTime = System.currentTimeMillis();
            int rows = 0;
            PreparedStatement statement = conn.prepareStatement(sql);
            rows = bulkLoadFromInputStream(statement, fileInputStream);
            long endTime = System.currentTimeMillis();
            logger.info(" LOAD DATA LOCAL INFILE :【插入" + rows + "行数据至MySql中，耗时" + (endTime - beginTime) + "ms。】");

        }catch (Exception e){
            logger.error("LOAD DATA 异常----");
            logger.error(e.getMessage(),e);
        }finally {
            try {
                if (null != fileInputStream) {
                    fileInputStream.close();
                }
                if (null != conn) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将数据从输入流加载到MySQL。
     *
     * @param loadDataSql SQL语句。
     * @param fileInputStream  输入流。
     * @return int         成功插入的行数。
     */
    private int bulkLoadFromInputStream(PreparedStatement statement,
                                        FileInputStream fileInputStream) throws SQLException {
        if (null == fileInputStream) {
            logger.info("输入流为NULL，没有数据导入。");
            return 0;
        }
        int result = 0;
        try {
//        mysql8  使用下面的方式
//        if (statement.isWrapperFor(JdbcStatement.class)) {
//            ClientPreparedStatement mysqlStatement = statement.unwrap(ClientPreparedStatement.class);
//            mysqlStatement.setLocalInfileInputStream(fileInputStream);
//            mysqlStatement.executeUpdate();
//        }
//        mysql5 使用下面的方式
            if (statement.isWrapperFor(com.mysql.jdbc.Statement.class)) {
                com.mysql.jdbc.PreparedStatement mysqlStatement = statement.unwrap(com.mysql.jdbc.PreparedStatement.class);
                mysqlStatement.setLocalInfileInputStream(fileInputStream);
                result = mysqlStatement.executeUpdate();
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }finally {
            if (statement != null) {
                statement.close();
            }
        }
        return result;
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     */
    private void delete(String filePath) {
        if (filePath != null && filePath.length() > 0) {
            File file = new SafelyFile(filePath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

}
