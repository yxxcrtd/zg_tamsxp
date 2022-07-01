package com.joyintech.tams.report.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.joyintech.tams.common.base.entity.BaseInformationDto;


/**
 * 业务基础表操作层
 * @author Administrator
 *
 */
@Repository
public interface BaseInformationMapper {
	/**
	 * 获取所有业务表基础信息
	 * @return 所有业务表基础信息
	 */
	List<BaseInformationDto> queryAllBaseInformation();
	
	/**
	 * 根据基础表主键集合获取基础表信息集合
	 * @param baseInformationIdList 基础表主键集合
	 * @return 基础表信息集合
	 */
	List<BaseInformationDto> queryBaseInfoById(@Param("list") List<String> baseInformationIdList);
	
	/**
	 * 获取所有基础表信息
	 * @return 所有基础表信息
	 */
	List<BaseInformationDto> queryAllTableInfo();
	
	/**
	 * 根据基础表代码获取对应基础表所有信息（查询用）
	 * @param tableId 基础表代码
	 * @return 对应基础表信息（查询用）
	 */
	List<BaseInformationDto> queryColumnByTableId(@Param("tableId") String tableId);
	
	/**
	 * 根据表名获取基础表信息
	 * @param tableId 表名
	 * @return 基础表信息
	 */
	List<BaseInformationDto> queryBaseInfoByTableId(@Param("tableId") String tableId);
	
	/**
	 * 根据前台传入的参数查询具体表信息
	 * @param tableId 表名
	 * @param list 字段及参数集合
	 * @return 具体表信息
	 */
	List<Object> queryBaseInfoByParams(@Param("tableId") String tableId, @Param("list") List<BaseInformationDto> list, @Param("flag") boolean flag,@Param("assetType") String assetType);
	
	
	/**
	 * 根据前台传入的参数查询具体表信息
	 * @param tableId 表名
	 * @param list 字段及参数集合
	 * @return 具体表信息
	 */
	List<Object> queryBaseInfoPageByParams(@Param("tableId") String tableId, @Param("list") List<BaseInformationDto> list, 
			@Param("beginIndex") int beginIndex,@Param("endIndex") int endIndex);
	
	/**
	 * 根据前台传入的参数查询具体表信息(条数)
	 * @param tableId 表名
	 * @param list 字段及参数集合
	 * @return 具体表信息
	 */
	int queryBaseInfoCountByParams(@Param("tableId") String tableId, @Param("list") List<BaseInformationDto> list);
	
	
	/**
	 * 根据表名和字段名查询对应数据字典
	 * @param tableId 表名
	 * @param columnIds 字段名
	 * @return 对应的数据字典
	 */
	String queryDictCode(@Param("tableId") String tableId, @Param("columnId") String columnId);
	
	/**
	 * 根据字典代码获取参数键值对
	 * @param dictCode 字典代码
	 * @return 参数键值对
	 */
	List<Object> queryItemByDictCode(@Param("dictCode") String dictCode);
	
	/**
	 * 根据表名和字段列表获取对应基础信息表主键信息集合
	 * @param tableId 表名
	 * @param columnList 字段列表
	 * @return 基础信息表主键集合
	 */
	List<String> queryBaseInfoIdList(@Param("tableId") String tableId, @Param("columnList") List<String> columnList);
	
	/**
	 * 根据过滤器ID获取已保存方案信息
	 * @param filterId
	 * @return
	 */
	List<BaseInformationDto> queryPlanByfilterId(@Param("filterId") String filterId);
	
	/**
	 * 根据表名获取日期类型字段名集合
	 * @param tableId 表名
	 * @return 日期类型字段名集合
	 */
	List<String> queryDateColumnList(@Param("tableId") String tableId);
	
	/**
	 * 根据表名获取时间类型字段名集合
	 * @param tableId 表名
	 * @return 时间类型字段名集合
	 */
	List<String> queryTimeColumnList(@Param("tableId") String tableId);
	
	/**
	 * 根据表名获取主键集合
	 * @param tableId 表名
	 * @return 主键集合
	 */
	List<String> queryPrimarykeyByTableName(@Param("tableId") String tableId);
	
	/**
	 * 根据表名和主键信息删除数据(状态置为D)
	 * @param tableId 表名
	 * @param list 主键信息
	 * @return 删除条数
	 */
	int deleteData(@Param("tableId") String tableId, @Param("map") Map<String, String> map);
	
	/**
	 *  根据表名和主键信息删除数据(直接删除)
	 * @param tableId
	 * @param map
	 * @return
	 */
	int deleteDataWithOutStatus(@Param("tableId") String tableId, @Param("map") Map<String, String> map);
	
	/**
	 * 根据表名页面数据向数据库插入一条新数据
	 * @param tableId 表名
	 * @param map 页面参数
	 * @return 插入条数
	 */
	int insertData(@Param("tableId") String tableId, @Param("map") Map<String, String> map);
	
	/**
	 * 根据表名，页面参数，主键信息修改数据
	 * @param tableId 表名
	 * @param map 页面参数
	 * @param pkMap 主键信息
	 * @return 修改条数
	 */
	int updateData(@Param("tableId") String tableId, @Param("map") Map<String, String> map, @Param("pkMap") Map<String, String> pkMap);
	
	/**
	 * 根据表名和字段名查询字段类型
	 * @param tableId 表名
	 * @param curColumn 字段名
	 * @return 字段类型
	 */
	String queryColumnTypeByCondition(@Param("tableId") String tableId, @Param("curColumn") String curColumn);
	
	/**
	 * 根据表名和字段名查询字段名称
	 * @param tableId 表名
	 * @param curColumn 字段名
	 * @return 字段名称
	 */
	String queryColumnNameByCondition(@Param("tableId") String tableId, @Param("curColumn") String curColumn);
	
	/**
	 * 根据表名查询业务主键集合
	 * @param tableId 表名
	 * @return 业务主键集合
	 */
	List<String> queryBisinessPkListByTable(@Param("tableId") String tableId);
	
	/**
	 * 根据表名查询数据对象
	 * @param tableId 表名
	 * @return 数据对象
	 */
	List<String> querydataObjectByTable(@Param("tableId") String tableId);
	
	/**
	 * 根据数据对象查询表名
	 * @param interfType 数据对象
	 * @return 表名
	 */
	List<BaseInformationDto> queryTableIdByInterfType(@Param("interfType") String interfType);
	/**
	 * 根据传入的主键组合查询数据是否已经存在
	 * @param map 主键组合
	 * @param tableId 待查询表
	 * @return 查询到的数据条数
	 */
	int checkDataIsExist(@Param("map") Map<String, String> map, @Param("tableId") String tableId);

	/**
	 * 根据传入的主键组合查询没有字段Status数据是否已经存在
	 * @param map 主键组合
	 * @param tableId 待查询表
	 * @return 查询到的数据条数
	 */
	int checkNoStatusDataIsExist(@Param("map") Map<String, String> map, @Param("tableId") String tableId);

	/**
	 * 根据表名查询所有字段名集合
	 * @param tableId 表名
	 * @return 索引字段名集合
	 */
	List<String> queryAllColumns(@Param("tableId") String tableId);
	
	/**
	 * 根据接口类型查询对应的表名
	 * @param dataObject 接口类型
	 * @return 表名
	 */
	List<String> queryTableIdByDataObject(@Param("dataObject") String dataObject);
	
	
	/**
	* @Title: queryConditionPage
	* @Description: 根据待维护表ID得到查询条件列表
	* @param @param map
	* @param @return    参数
	* @return List<BaseInformationDto>    查询条件列表
	* @throws
	*/
	List<BaseInformationDto> queryConditionPage(Map<String,Object> map);
	
	/**
	* @Title: deleteQueryCondition
	* @Description: (根据表名删除查询条件)
	* @param @param tableId
	* @param @return    参数
	* @return int    返回类型
	* @throws
	*/
	int deleteQueryCondition(@Param("mgrTableId") String mgrTableId);
	
	/**
	* @Title: insertBatch
	* @Description: (批量插入查询条件信息)
	* @param @param baseInformationDtoList
	* @param @return    参数
	* @return int    返回类型
	* @throws
	*/
	int insertBatch(@Param("insertList") List<BaseInformationDto> baseInformationDtoList);

	/**
	 *
	 * @Description 根据表中文名查询配置中对应的基本信息
	 * @Author      高健
	 * @Date        2019年12月13日 下午4:06:27
	 * @param @param tableName 表中文名
	 * @param @return 参数
	 * @return List<BaseInformationDto> 返回类型
	 * @throws
	 */
	List<BaseInformationDto> queryTableInfoByTableName(@Param("tableName") String tableName);

	/**
	 *
	 * @Description 查询出CHB_ASSET_INFO在配置表中的中文名
	 * @Author      张明明
	 * @Date        2020年03月03日
	 * @return 		String 返回类型
	 * @throws
	 */
	Integer selectHeaderNumByChiName(@Param("chiName") String chiName);

	/**
	 * 根据前台传入的参数查询具体表信息
	 * @param tableId 表名
	 * @param list 字段及参数集合
	 * @return 具体表信息
	 */
	List<Object> queryChbLoanDetailInfoByParams(@Param("tableId") String tableId, @Param("list") List<BaseInformationDto> list, @Param("flag") boolean flag,@Param("assetType") String assetType);

}
