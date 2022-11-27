package com.joyintech.service.risk.valtblimport.controller;

import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.service.risk.valtblimport.dto.*;
import com.joyintech.service.risk.valtblimport.service.RskValTableQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 *  估值表查询
 * 〈功能详细描述〉
 * @author zhangnian
 * @version 1.0 2019年1月3日
 * @see RskValTableQueryController
 * @since 1.0
 */
@RestController("rskValTableQuery_dtx")
@RequestMapping("/RskValTableQuery")
public class RskValTableQueryController {

    @Autowired
    private RskValTableQueryService rskValTableQueryService;

    /**
     * 查询
     * @param param
     * @param page
     * @param length
     * @return
     * @throws Exception
     * @see
     * @since 1.0
     */
    @GetMapping("/getFileName")
    public List<SelectOptionDto> getFileName(String fileName) {
        return rskValTableQueryService.getFileName(fileName);
    }
    @GetMapping("/getFileHeader")
    public List<SelectOptionDto> getFileHeader(String fileHeader) {
        return rskValTableQueryService.getFileHeader(fileHeader);
    }
    @GetMapping("/getSubCode")
    public List<SelectOptionDto> getSubCode(String subCode) {
        return rskValTableQueryService.getSubCode(subCode);
    }
   @PostMapping("findPage/{page}/{length}")
    public Page<RskValtblFileInfoDto> selectAll(@RequestBody Map<String, Object> param,
                                                @PathVariable("page") int page, @PathVariable("length") int length) {
        return rskValTableQueryService.selectForPage(page, length, param);
    }
    @PostMapping("/insert")
    public int insert(@RequestBody RskValtblFileInfoDto rskValtblFileInfoDto){
        return rskValTableQueryService.insert(rskValtblFileInfoDto);
    }
    @PostMapping("/insertForLog")
    public int insertForLog(@RequestBody RskValtblAnlsLogDto rskValtblAnlsLogDto){
        return rskValTableQueryService.insertForLog(rskValtblAnlsLogDto);
    }


    @PostMapping("/selectName")
    public List<String> selectName(String name){
        return rskValTableQueryService.selectName( name);
    }
    @PostMapping("/deleteName")
    public int deleteName(String name){
        return rskValTableQueryService.deleteName(name);
    }
    @PostMapping("/selectNameDate")
    public List<String> selectNameDate(@RequestBody Map<String,Object> map){
        return rskValTableQueryService.selectNameDate(map);
    }
    @PostMapping("/deleteNameDate")
    public int deleteNameDate(@RequestBody Map<String,Object> map){
        return rskValTableQueryService.deleteNameDate(map);
    }
    @PostMapping("/deleteNameData")
    public int deleteNameData(String name){
        return rskValTableQueryService.deleteNameData(name);
    }
    @PostMapping("/deleteNameDateData")
    public int deleteNameDateData(@RequestBody Map<String,Object> map){
        return rskValTableQueryService.deleteNameDateData(map);
    }

   /* @PostMapping("selectRskValTableDetaile")
    public List<RskValtblAssetSubDto> selectAll(@RequestBody Map<String, Object> param){
        return rskValTableQueryService.selectRskValTableDetaile(param);
    }*/
   @PostMapping("/insertForData")
   public int insert(@RequestBody List<RskValtblDataDto> rskValtblDataDtoList){
       return rskValTableQueryService.insertForData(rskValtblDataDtoList);
   }
    @PostMapping("/selectForLog")
    public String selectForLog(String infoName){
       return rskValTableQueryService.selectForLog(infoName);
    }

}
