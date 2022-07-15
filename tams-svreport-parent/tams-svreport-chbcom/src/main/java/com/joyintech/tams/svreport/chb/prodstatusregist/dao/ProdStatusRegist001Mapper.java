package com.joyintech.tams.svreport.chb.prodstatusregist.dao;

import com.joyintech.tams.common.base.dao.ChbPrdStatusSubMapper;
import com.joyintech.tams.svreport.chb.prodstatusregist.dto.ProdStatusRegist001Dto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Repository
public interface ProdStatusRegist001Mapper extends ChbPrdStatusSubMapper {


	List<ProdStatusRegist001Dto> selectAllDataByList(Map<String, Object> pageParam);
	
	/**   
	* @Function: ProdTerminationRegist001Mapper.java
	* @Description: 用REGIST_SEQNO查询产品终止登记数据
	*
	* @param: keyList
	* @return：产品终止登记数据
	*
	* @author: 王冠/wangguan
	* @date: 2019年6月25日 下午3:48:23 
	*/
	List<ProdStatusRegist001Dto> selectAllDataByKeyList(@Param("keyList") List<String> keyList);
	
	List<ProdStatusRegist001Dto> selectAllData(Map<String, Object> pageParam);

	void updateSubmitStatusToOk(@Param("list") List<String> registSeqList);

	/**
	 * @Description 手工处理
	 * @param sendStatus
	 * @param list
	 * @return void
	 * @version 3.0 2019年1月24日
	 */
	void updateInterfSubmitAndSeqBatch(@Param("sendStatus")String sendStatus, @Param("registList") List<ProdStatusRegist001Dto> list);
	/**
	 * @Description 更新所有检查通过的未报送数据，状态修改为接口报送
	 * @param sendStatus
	 * @return void
	 * @version 3.0 2019年1月24日
	 */
	void updateAllInterfSubmitAndSeqBatch(@Param("sendStatus") String sendStatus);
	/**
	 * @Description 取消手工处理
	 * @param registSeqList
	 * @return void
	 * @version 3.0 2019年1月24日
	 */
	void updateTerminationRegist(@Param("list") List<Date> registSeqList);


    /**
     * 产品终止登记批处理批量插入
     * @param dtolist
     * @return int
     */
    int insertBatch(@Param("insertList")List<ProdStatusRegist001Dto> dtolist);
    /**
     * 产品终止登记批处理批量更新
     * @param dtolist
     * @return int
     */
    int updateBatch(@Param("updateList")List<ProdStatusRegist001Dto> dtolist);



}