package com.joyintech.tams.invest.common.service;

import com.joyintech.background.component.common.check.DataCheckCommonUtil;
import com.joyintech.background.component.common.check.dao.DataCheckCommonMapper;
import com.joyintech.background.component.common.check.dto.CheckRule;
import com.joyintech.framework.base.intf.Page;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.validation.ValidMessage;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 对外开放接口
 * @auth 何阳阳
 * @date 2021/7/7
 */
@Service
public class InvDataCheckService {

    @Autowired
    private DataCheckCommonUtil dataCheckCommonUtil;

    @Autowired
    private DataCheckCommonMapper dataCheckCommonMapper;

    public List<ValidMessage> chbCheckCommon(List<ValidMessage> checkResultList, Object entity, String pageId, String repType) {
        //获取对应画面的page add by zcw 20211124
        pageId = pageId==null? "" : pageId;
        String elementId = dataCheckCommonMapper.queryElementIdByPageId(pageId);
        Page page= CacheUtil.getPage(pageId);
        // 获取page中SYS_ELEMENT_CONTROL表配置的是否展示or隐藏画面元素。如果隐藏则跳过判断，如果展示则进入判断逻辑
        // modify by etliu 20211223 start
        // SYS_ELEMENT_CONTROL表没有配置，认为是显示，需要做check
//        if (null != page && null != page.getElementcontrol(elementId) && page.getElementcontrol(elementId).getIsShow()) {
        if (null != page && ((null != page.getElementcontrol(elementId) && page.getElementcontrol(elementId).getIsShow())
            || page.getElementcontrol(elementId) == null)) {
        // modify by etliu 20211223 end
            return dataCheckCommonUtil.chbCheckCommon(checkResultList, entity, pageId, repType, ValidMessage.class);
        }
        // return new ArrayList<ValidMessage>();
        return checkResultList;
    }

    public Map<String,String> getCheckRule(String pageId, String pageFlag) {
        List<CheckRule> chbCheckRuleList = dataCheckCommonMapper.queryChbCheckRule(pageId, pageFlag);
        if (CollectionUtils.isNotEmpty(chbCheckRuleList)){
            return chbCheckRuleList.stream().collect(Collectors.toMap(rule -> rule.getSrcproperty() + GlobalConstant.UNDER_LINE + rule.getCheckmethod(), rule -> rule.getRemark()));
        }
        return new HashMap<>();
    }
}
