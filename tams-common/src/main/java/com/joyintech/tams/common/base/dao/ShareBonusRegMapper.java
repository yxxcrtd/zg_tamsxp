package com.joyintech.tams.common.base.dao;


import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.tams.common.base.entity.ShareBonusReg;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ShareBonusRegMapper {

    void insertSelective(ShareBonusReg entity);



}
