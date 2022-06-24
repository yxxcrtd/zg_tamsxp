package com.joyintech.tams.svreport.chb.businessdatamaintain.chbmstbondcreditrating.dao;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbmstbondcreditrating.dto.ChbMstBondCreditRatingDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author 76587
* @description 针对表【MST_BOND_CREDIT_RATING(债券信用评级)】的数据库操作Mapper
* @createDate 2022-04-01 14:47:17
* @Entity generator.domain.MstBondCreditRating
*/
@Repository
public interface ChbMstBondCreditRatingDao {


    void update(ChbMstBondCreditRatingDto entity);

    void insert(ChbMstBondCreditRatingDto entity);

    ChbMstBondCreditRatingDto selcetByKey(@Param("oCode") String oCode,@Param("gradeOrgId") String gradeOrgId,@Param("gradeDate") Date gradeDate, @Param("oType") String oType);

    List<ChbMstBondCreditRatingDto> selectAll(Map<String, Object> pageParam);

    void batchInsert(@Param("list") List<ChbMstBondCreditRatingDto> list);

    List<SuggestDto> getFinProdAbbr(@Param("param") String param);

    List<SelectOptionDto> getMutliCode(Map<String, Object> map);

    int selectValidRecords(@Param("list") List<Map<String, String>> list);
}
