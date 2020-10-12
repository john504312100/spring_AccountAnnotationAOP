package com.itheima.config;

import org.springframework.context.annotation.*;

/**
 * @author yuehan
 * @DATE 2020-10-12
 * @TIME 18:09
 */
@Configuration
@ComponentScan("com.itheima")
@EnableAspectJAutoProxy
@PropertySource("classpath:jdbcConfig.properties")
@Import(JdbcConfig.class)
public class SpringConfiguration {
}
