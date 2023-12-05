package com.rocky.config;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWTUtil;
import com.gmw.model.LocalUser;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //禁用CSRF,关闭网站请求伪造保护，在默认的15个过滤器中移除了 csrfFilter
        http.csrf().disable()
                .logout().disable()
                .headers().disable()
                //禁用 HttpSession，不再使用httpsession获取上下文信息
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //处理认证不通过
                .and().exceptionHandling().authenticationEntryPoint(
                        (request, response, authException) -> {
                            response.setContentType("application/json;charset=utf-8");
                            //未授权统一处理
                            Dict dict = Dict.create().set("code", 401).set("message", "未授权");
                            response.getWriter().write(JSONUtil.toJsonStr(dict));
                        })
                .and()
                // 权限验证
                .authorizeRequests()
                .antMatchers("/api/user/register", "/api/user/login").anonymous() //允许匿名访问
                .anyRequest().authenticated()
                .and().addFilter(new TokeFilter(authenticationManager())); //添加自定义过滤器
    }
    static class TokeFilter extends BasicAuthenticationFilter {
        public TokeFilter(AuthenticationManager authenticationManager) {
            super(authenticationManager);
        }
        @Override
        protected void doFilterInternal(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, javax.servlet.FilterChain chain) throws java.io.IOException, javax.servlet.ServletException {
            //获取请求头中的token
            String token = request.getHeader("token");
            if(ObjectUtil.isNotEmpty(token)){
                LocalUser localUser = JSONUtil.toBean(JWTUtil.parseToken(token).getPayload().toString(), LocalUser.class);
                String[] roles = localUser.getRoles();
                List<GrantedAuthority> authorities = new ArrayList<>();
                for (String role : roles) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
                }
                authorities.add(new SimpleGrantedAuthority("ROLE_admin"));
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(localUser.getId(), "", authorities);
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            chain.doFilter(request, response);
        }
    }
}
