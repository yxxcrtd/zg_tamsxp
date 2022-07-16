package com.joyintech.tams.svreport.chb.astposregist.dao;

import com.joyintech.tams.common.base.entity.ChbAstPosSub;
import com.joyintech.tams.common.base.entity.ChbPrdStatusSub;
import com.joyintech.tams.svreport.chb.astposregist.dto.ChbAstPosBatchResultDto;
import com.joyintech.tams.svreport.chb.astposregist.dto.ChbAstPosRegist001Dto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface ChbAstPosRegist001Mapper {

    List<ChbAstPosRegist001Dto> selectAllData(Map<String, Object> pageParam);

    List<ChbAstPosRegist001Dto> selectAllDataByList(@Param("keyList") List<ChbAstPosRegist001Dto> dataList);

    List<String> selectRegistSeqnoList(@Param("list")List<ChbAstPosRegist001Dto> astposTerminationList,@Param("flag") String flag);

    void updateInterfSubmitAndSeqBatch(@Param("sendStatus")String sendStatus, @Param("registList") List<ChbAstPosRegist001Dto> astposTerminationList);

    void updateAllInterfSubmitAndSeqBatch(@Param("sendStatus") String sendStatus);

    void insertSubHisBatch(@Param("regList")List<String> list);

    void deleteRegistSeqnoList(@Param("regList")List<String> list);

    List<String> selectByprodId(@Param("regList")List<ChbAstPosRegist001Dto> prodTerminationList);

    void updateTerminationRegist(@Param("list") List<String> registSeqList);

    void insertSeletForSub(@Param("regList")List<String> list);

    void deleteBySeqno(@Param("seqnolist")List<String> list);

    List<ChbAstPosRegist001Dto> selectAllDataByKeyList(@Param("keyList") List<String> keyList);


    /**
     * 获取较小的日期
     * @param batchTime
     * @return
     */
    Date genExcuteDate(@Param("batchTime") String batchTime);

    List<String>  selectsubmitRecodeList(@Param("batchTime") String batchTime);

    /**
     * 删除资产持仓登记
     * @param termid
     */
    void   deleteAstPosSubByTerm(@Param("termid")String termid);

    /**
     * 删除产品状态登记
     * @param termid
     */
    void   deletePrdStatusByTerm(@Param("termid")String termid);

    /**
     * 根据资产基础表
     * @param objectDate
     * @return
     */
    List<ChbAstPosBatchResultDto>  selectAssetBasiscData(@Param("objectDate") String objectDate);

    /**
     * 根据科目余额表
     * @param objectDate
     * @return
     */
    List<ChbAstPosBatchResultDto>  selectBalanceData(@Param("objectDate") String objectDate);

    /**
     * 根据底层资产持仓表
     * @param objectDate
     * @return
     */
    List<ChbAstPosBatchResultDto>  selectBotAstData(@Param("objectDate") String objectDate);

    void insertChbAstPosBatchList(@Param("list")List<ChbAstPosSub>  list);

    void insertChbPrdStatusBatchList(@Param("list")List<ChbPrdStatusSub>  list);

//    List<ChbSecRepayInfoDto> selectSecRepayInfo(@Param("objectDate") String objectDate);
}