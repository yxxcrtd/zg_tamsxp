package com.joyintech.tams;

import cn.hutool.core.date.StopWatch;
import com.joyintech.framework.JoyinApplication;
import com.joyintech.framework.config.WebApplication;
import com.joyintech.tams.common.ErrorPageFilter;
import org.springframework.context.annotation.Bean;

/**
 * @author 王波
 * @version 1.0 2019年04月23日
 * @see 
 * @since 1.0
 */
public class TamsApplication extends WebApplication {

    @Bean
    public ErrorPageFilter errorPageFilter() {
        return new ErrorPageFilter();
    }

    public static void main(String [] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        JoyinApplication.run(TamsApplication.class, args);
        stopWatch.stop();
        System.out.println("======================================== 启动成功！耗时：" + stopWatch.getTotalTimeMillis() / 1000 + "秒！========================================");
    }

}
