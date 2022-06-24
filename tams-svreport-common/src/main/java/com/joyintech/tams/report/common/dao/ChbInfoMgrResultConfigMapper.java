
/**  
* @Title: ChbInfoMgrResultConfigMapper.java
* @Package com.joyintech.tams.report.common.dao
* @Description: TODO(用一句话描述该文件做什么)
* @author gaojian
* @date 2019年7月3日
* @since V1.0  
* @Co.,Ltd. 安徽兆尹信息科技有限责任公司
*/
    
package com.joyintech.tams.report.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.joyintech.tams.common.base.entity.ChbInfoMgrResultConfig;

/**
* @ClassName: ChbInfoMgrResultConfigMapper
* @Description: TODO(这里用一句话描述这个类的作用)
* @author gaojian
* @date 2019年7月3日 
*
*/
@Repository
public interface ChbInfoMgrResultConfigMapper {
	/**
	* @Title: queryAllchbMgrTableInfo
	* @Description: (查询显示的列)
	* @param @param selectParam
	* @param @return    参数
	* @return List<ChbInfoMgrResultConfig>    返回类型
	* @throws
	*/
	List<ChbInfoMgrResultConfig> queryAllchbMgrTableInfo(Map<String, Object> selectParam);

	/**
	 *
	 * @Description TODO
	 * @Author      高健
	 * @Date        2019年12月6日 上午9:57:46
	 * @param @param selectParam
	 * @param @return 参数
	 * @return List<ChbInfoMgrResultConfig> 返回类型
	 * @throws
	 */
	List<ChbInfoMgrResultConfig> queryAllchbMgrTableInfoEx(Map<String, Object> selectParam);
	/**
	* @Title: deleteQueryResult
	* @Description: (删除查询结果配置)
	* @param @param tableId
	* @param @return    参数
	* @return int    返回类型
	* @throws
	*/
	int deleteQueryResult(@Param("mgrTableId") String mgrTableId);
	
	/**
	* @Title: insertBatch
	* @Description: (写入新的查询结果配置)
	* @param @param chbInfoMgrResultConfigList
	* @param @return    参数
	* @return int    返回类型
	* @throws
	*/
	int insertBatch(@Param("insertList") List<ChbInfoMgrResultConfig> chbInfoMgrResultConfigList);

	String getTableNameByTableId(@Param("mgrTableId") String mgrTableId);
	
	List<String>getAllColumnByTableId(@Param("mgrTableId") String mgrTableId);

	List<ChbInfoMgrResultConfig> queryCloumnMapping(Map<String, Object> param);
}
