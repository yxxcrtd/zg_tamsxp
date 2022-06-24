package com.joyintech.tams.svreport.chb.assetinfodetail.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wubingbing
 * @date 2022-06-07 10:11
 */
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean testFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new ParamFilter());
        String[] urls = {"/chb/assetinfodetail/AssetInfoDetail001/export"
                ,"/basicdata/perprodeclareinfo/SvrPerProDeclareInfo001/export"
                ,"/chb/businessdatamaintain/chbprdbaseinfo/chbprdbaseinfo001/export"
                ,"/chb/businessdatamaintain/chbexchangeinfo/ChbExchangeQuotationInfo001/export"};
        registration.addUrlPatterns(urls);
        registration.setOrder(1);
        return registration;
    }
}
