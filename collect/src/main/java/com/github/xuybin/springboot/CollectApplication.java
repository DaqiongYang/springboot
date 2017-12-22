package com.github.xuybin.springboot;

import com.github.xuybin.springboot.configuration.JwtAuthenticationTokenFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SpringBootApplication
@EnableSwagger2
public class CollectApplication {
    private static final Logger logger =  LoggerFactory.getLogger("CollectApplication");
    public static void main(String[] args) {
        SpringApplication.run(CollectApplication.class, args);
    }

    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("数据采集")
                        .description("API文档,可以进行测试")
                        .version("1.0")
                        .build())
                .select()
                .apis( RequestHandlerSelectors.basePackage( "com.rhzy.collect")).build();

    }

    @EnableWebSecurity
    public static class SecurityConfig extends WebSecurityConfigurerAdapter {
        @Bean
        public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
            return new JwtAuthenticationTokenFilter();
        }

        // @formatter:off
        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception {
            httpSecurity
                    // 只要正确使用(不要尝试把token写入cookie)的是JWT，这里不需要csrf
                    .csrf().disable()
                    // 基于浏览器存储,请求时head或URL带上token,所以不允许Spring-Security使用session绑定登陆信息,其他的如图片验证码仍可使用
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                    .exceptionHandling()
                        .authenticationEntryPoint(new AuthenticationEntryPoint(){
                            @Override
                            public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,e.getMessage());
                            }
                        })
                        .accessDeniedHandler(new AccessDeniedHandler(){

                            @Override
                            public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
                                httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN,e.getMessage());
                            }
                        })
                        .and()
                    .authorizeRequests()
                        // 允许对于静态资源的无授权访问
                        .antMatchers(
                                HttpMethod.GET,
                                "/",
                                "/*.html",
                                "/favicon.ico",
                                "/**/*.html",
                                "/**/*.css",
                                "/**/*.js",
                                "/**/*.png",
                                "/**/*.jpeg",
                                "/**/*.jpg",
                                "/**/*.woff",
                                "/**/*.ttf",
                                "/**/*.json"
                        ).permitAll()
                        .antMatchers("/auth/**").permitAll()
                        // 除上面外的所有请求全部需要鉴权认证
                        .anyRequest().authenticated();
            // 自定义JWT拦截器,一个请求值拦截一次
            httpSecurity
                    .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

            //禁用缓存控制
            httpSecurity.headers().cacheControl();
        }
        // @formatter:on
    }

}
