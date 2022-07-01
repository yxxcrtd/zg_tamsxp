package com.joyintech.tams.svreport.chb.businessdatamaintain.chbboksubject.dao;

import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbboksubject.dto.ChbBokSubjectDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author wubingbing
 * @date 2022-03-28 16:18
 */
@Repository
public interface ChbBokSubjectDao {
    /**
     * 检索上级科目代码
     *
     * @return 上级科目代码列表
     * @see
     * @since 1.0
     */
    List<SuggestDto> getFSubjectId();

    /**
     * 获取科目级别联动
     *
     * @return 科目代码列表
     * @see
     * @since 1.0
     */
    List<SelectOptionDto> getSubjectLevel(Map<String, Object> map);
    /**
     * 获取科目号联想文本输入框
     *
     * @return 科目代码列表
     * @see
     * @since 1.0
     */
    List<SelectOptionDto> getSubjectId2(Map<String, Object> map);

    /**
     * 科目名称 检索
     * @param pageParam
     * @return
     */

    List<SelectOptionDto> getSubjectName(Map<String, Object> pageParam);

    /**
     * 科目定义 查询
     * @param pageParam
     */
    List<ChbBokSubjectDto> selectAll(Map<String, Object> pageParam);

    /**
     * 判断科目号是否存在
     * @param subjectNo
     * @return
     */
    int isExists(@Param("booksetId") String booksetId,@Param("subjectNo") String subjectNo);

    /**
     * 插入科目
     * @param entity
     */
    void insert(ChbBokSubjectDto entity);

    ChbBokSubjectDto selcetByKey(@Param("subjectNo") String subjectNo,@Param("booksetId") String booksetId);

    void update(ChbBokSubjectDto entity);

    void batchInsert(@Param("list") List<ChbBokSubjectDto> list);

    int  selectValidRecords(@Param("list") List<Map<String,String>> list);

    int countDuplicatedData(List<Map<String, String>> list);

    List<ChbBokSubjectDto> selectByKeys(@Param("list") List<ChbBokSubjectDto> list);
}
