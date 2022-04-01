package com.joyintech.tams.svreport.chb.prodraisetotalregist.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.joyintech.tams.common.base.entity.ChbPrdDeal;
import com.joyintech.tams.svreport.position.dto.ChbPrdPosition;
import com.joyintech.tams.svreport.chb.prodraisetotalregist.dto.RaiseTotalRegist001Dto;



@Repository
public interface RaiseTotalRegist001Mapper {
    int deleteByPrimaryKey(String registSeqno);

    int insert(RaiseTotalRegist001Dto record);

    int insertSelective(RaiseTotalRegist001Dto record);

    RaiseTotalRegist001Dto selectByPrimaryKey(String registSeqno);

    int updateByPrimaryKeySelective(RaiseTotalRegist001Dto record);

    int updateByPrimaryKey(RaiseTotalRegist001Dto record);
    
	/**
	 * 功能:根据产品登记编码删除产品发行登记
	 * @since 1.6.0
	 * @version 1.6.0
	 * @author：weiyuqiang 2019年3月23日
	 * @param subList 
	 * void
	 */
    void deleteByProdRegCode(@Param("deleteList")List<RaiseTotalRegist001Dto> subList);
    /**
     * 查询所有的待处理数据
     * <功能详细描述>
     * @param pageParam
     * @see
     * @since 1.0
     */
    List<RaiseTotalRegist001Dto> selectAllData(Map<String, Object> pageParam);
    /**
     * 查询所有的待处理数据
     * <功能详细描述>
     * @param pageParam
     * @see
     * @since 1.0
     */
    List<RaiseTotalRegist001Dto>  selectAllDataByList(@Param("keyList") List<RaiseTotalRegist001Dto> dataList);
    
    void updateSubmitStatusToOk(@Param("list") List<String> registSeqList);
    /**
	 * @Description 手工处理
	 * @param sendStatus
	 * @param prodRaiseTotalList
	 * @return void
	 */
	void updateInterfSubmitAndSeqBatch(@Param("sendStatus")String sendStatus, @Param("registList") List<RaiseTotalRegist001Dto> prodRaiseTotalList);
	/**
	 * @Description 更新所有检查通过的未报送数据，状态修改为接口报送
	 * @param sendStatus
	 * @return void
	 */
	void updateAllInterfSubmitAndSeqBatch(@Param("sendStatus") String sendStatus);
	/**
	 * @Description 取消手工处理
	 * @param registSeqList
	 * @return void
	 */
	void updateRaiseTotalRegist(@Param("list") List<String> registSeqList);
	
    /**
     * @Description 查找交易数据
     * @param prodlistsum
     * @return List<ChbPrdDeal>
     */
    List<ChbPrdDeal> selectDealData(@Param("list")List<RaiseTotalRegist001Dto> prodlistsum);
	
    /**
     * @Description 统计投资者信息
     * @param prodlistsum
     * @return List<RaiseTotalRegist001Dto>
     */
    List<RaiseTotalRegist001Dto> selectInvestorNum(@Param("list")List<RaiseTotalRegist001Dto> prodlistsum);
    /**
     * @Description 查找头寸数据
     * @param prodlistsum
     * @return List<ChbPrdPosition>
     */
    List<ChbPrdPosition> selectPositionData(@Param("list")List<RaiseTotalRegist001Dto> prodlistsum);	
	/**
	 * @Description 批量插入
	 * @param dtolist
	 * @return int
	 */
	int insertBatch(@Param("insertList")List<RaiseTotalRegist001Dto> dtolist);
	
	/**
	 * @Description 查询是否存在该产品的登记数据
	 * @param dto
	 * @return String
	 */
	String selectIsExistAlreadyRegist(RaiseTotalRegist001Dto dto);
	
	/**
	 * @Description 批量更新
	 * @param dtolist
	 * @return int
	 */
	int updateBatch(@Param("updateList")List<RaiseTotalRegist001Dto> dtolist);
	
	/**
	 * @Description 查找需要生成登记数据的产品
	 * @param vdate
	 * @return List<RaiseTotalRegist001Dto>
	 */
	List<RaiseTotalRegist001Dto> selectRaiseTotallist (@Param("vdate")String vdate,@Param("prodIdList")List<String> prodIdList);
	
	int deleteBatch(@Param("list")List<String> list);
	
	int updateBySeqno(@Param("list")List<String> list);

	List<String> selectRegistSeqnoList(@Param("list")List<RaiseTotalRegist001Dto> prodTerminationList,@Param("flag")String flag);

	void deleteRegistSeqnoList(@Param("regList")List<String> list);

	void insertSeletForSub(@Param("regList")List<String> list);

	List<RaiseTotalRegist001Dto> selectRegPerProdList(@Param("list")List<RaiseTotalRegist001Dto> prodlistsum);

	List<RaiseTotalRegist001Dto> queryListByKeys(@Param("list")List<String> checkKeys);

	List<RaiseTotalRegist001Dto> getAllInterfRaiseSum();
	/**
	 * 从投资者明细获取个人、法人等客户数
	 * @param prodId
	 * @return
	 */
	List<RaiseTotalRegist001Dto>   getEquCount(String prodId);

	List<RaiseTotalRegist001Dto> selectAllDataForExport(Map<String, Object> pageParam);
}