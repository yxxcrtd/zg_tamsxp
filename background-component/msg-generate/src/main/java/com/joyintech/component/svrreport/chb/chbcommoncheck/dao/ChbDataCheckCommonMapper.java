package com.joyintech.component.svrreport.chb.chbcommoncheck.dao;

import com.joyintech.component.svrreport.chb.chbcommoncheck.dto.ChbCheckRepType;
import com.joyintech.component.svrreport.chb.chbcommoncheck.dto.ChbCheckRule;
import com.joyintech.component.svrreport.chb.chbcommoncheck.dto.CheckCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChbDataCheckCommonMapper {

    /**
     *  根据画面Id查询中债前置规则表
     * @param checkruleid 画面Id
     * @return 中债前置规则表
     */
    List<CheckCondition> queryChbCheckCondition(@Param("checkruleid") String checkruleid, @Param("pageFlag") String pageFlag);

    /**
     *  根据画面Id查询中债匹配规则表
     * @param pageId 画面Id
     * @return 中债匹配规则表
     */
    List<ChbCheckRule> queryChbCheckRule(@Param("pageId") String pageId, @Param("pageFlag") String pageFlag);

    /**
     *  根据画面Id查询数据检查中债匹配类型表
     * @param pageId 画面Id
     * @param repType 匹配类型
     * @return 数据检查中债匹配类型表
     */
    List<ChbCheckRepType> queryChbCheckRepTypeList(@Param("pageId") String pageId, @Param("repType") String repType);
}
