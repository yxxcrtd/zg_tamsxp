package com.joyintech.tams.svreport.chb.subsidiamonthreport.dto;

import java.util.HashSet;
import java.util.Set;

import com.joyintech.framework.util.IDGenerator;
import com.joyintech.tams.svreport.chb.subsidiamonthreport.base.entity.ChbMonthReportSubDetail;

/**
 * 理财子公司月度报表明细表Dto
 *
 * @author 杜保军
 * @version 1.0 2020年6月3日
 * @since 1.0
 */
public class ChbMonthReportSubDetailDto extends ChbMonthReportSubDetail {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 3963685735411497229L;

    /**
     * 产品集合
     */
    private Set<String> prodSet;

    /**
     * 存续产品集合
     */
    private Set<String> durationProdSet;

    /**
     * 母行产品集合
     */
    private final Set<String> prodBanSet = new HashSet<>();

    /**
     * 子公司产品集合
     */
    private final Set<String> prodSubSet = new HashSet<>();

    /**
     * Gets the value of prodSet. *
     *
     * @return the value of prodSet
     */
    public Set<String> getProdSet() {
        return prodSet;
    }

    /**
     * Sets the prodSet. *
     * <p>
     * You can use getProdSet() to get the value of prodSet
     * </p>
     * * @param prodSet prodSet
     */
    public void setProdSet(Set<String> prodSet) {
        this.prodSet = prodSet;
    }

    /**
     * Gets the value of durationProdSet. *
     *
     * @return the value of durationProdSet
     */
    public Set<String> getDurationProdSet() {
        return durationProdSet;
    }

    /**
     * Sets the durationProdSet. *
     * <p>
     * You can use getDurationProdSet() to get the value of durationProdSet
     * </p>
     * * @param durationProdSet durationProdSet
     */
    public void setDurationProdSet(Set<String> durationProdSet) {
        this.durationProdSet = durationProdSet;
    }

    /**
     * @return the prodBanSet
     */
    public Set<String> getProdBanSet() {
        return prodBanSet;
    }

    /**
     * @param prodBan the prodBanSet to add
     */
    public void addProdBan(String prodBan) {
        this.prodBanSet.add(prodBan);
    }

    /**
     * @param prodBanSet the prodBanSet to add
     */
    public void addAllProdBan(Set<String> prodBanSet) {
        this.prodBanSet.addAll(prodBanSet);
    }

    /**
     * @return the prodSubSet
     */
    public Set<String> getProdSubSet() {
        return prodSubSet;
    }

    /**
     * @param prodSub the prodSubSet to add
     */
    public void addProdSub(String prodSub) {
        this.prodSubSet.add(prodSub);
    }

    /**
     * @param prodSubSet the prodSubSet to add
     */
    public void addAllProdSub(Set<String> prodSubSet) {
        this.prodSubSet.addAll(prodSubSet);
    }
    
    public ChbMonthReportSubDetailDto() {
        super.setDetailuuid(IDGenerator.generate(36));
    }
}
