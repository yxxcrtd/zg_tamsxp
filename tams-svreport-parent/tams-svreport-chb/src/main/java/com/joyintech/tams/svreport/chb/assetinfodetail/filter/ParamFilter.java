package com.joyintech.tams.svreport.chb.assetinfodetail.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * @author wubingbing
 * @date 2022-06-07 9:15
 */
public class ParamFilter implements Filter {
    @Override
    public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ParameterRequestWrapper requestWrapper = new ParameterRequestWrapper((HttpServletRequest) request);
        requestWrapper.addParameter("elementId","");
        chain.doFilter(requestWrapper,response);
    }
    @Override
    public void destroy() {

    }
}
