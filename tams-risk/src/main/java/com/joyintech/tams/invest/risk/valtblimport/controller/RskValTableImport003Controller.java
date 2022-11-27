package com.joyintech.tams.invest.risk.valtblimport.controller;

import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PageId("RskValTableImport003")
@RequestMapping("/invest/risk/valtblimport/RskValTableImport003")
public class RskValTableImport003Controller extends BaseController {
    @Override
    @RequestMapping(PAGE_INIT)
    public String init(Model model) {
        return super.init(model);
    }
}