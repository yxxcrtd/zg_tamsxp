package com.joyintech.tams.batch.report.chbastposregistexport.dao;

import com.joyintech.tams.report.system.subinfobatchlog.dto.ChbSubinfoBatchLog;
import com.joyintech.tams.svreport.chb.astposregist.dto.ChbAstPosRegist001Dto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 资产持仓登记批处理导出mapper
 *
 * @author zhoubin
 * @version 1.0 2022年07月18日
 * @see ChbAstPosRegistExportMapper
 * @since 1.0
 */
@Repository
public interface ChbAstPosRegistExportMapper {

    List<ChbSubinfoBatchLog> getBatchLog();
    int countExportNum(Map<String,Object> map);

    /**
     *
     * 分页查询导出数据
     * @param map 查询条件
     * @return 结果列表
     * @see
     * @since 1.0
     */
    List<ChbAstPosRegist001Dto> selectAllDataByPage(Map<String,Object> map);
}
