package com.github.xuybin.springboot.configuration;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.xuybin.springboot.model.DBUserDetails;
import com.github.xuybin.springboot.service.DBUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private DBUserDetailsService dbUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.urlkey}")
    private String tokenUrlkey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String username = null;
        String authToken = null;
        String requestHeader=request.getHeader(tokenHeader);
        if (StringUtils.startsWithIgnoreCase(requestHeader,"bearer ")) {
            authToken = requestHeader.substring(7);
        }else if(request.getMethod().equalsIgnoreCase("get")){
            //只有在Get方法时,才尝试从url获取token,防止csrf()攻击
            authToken=request.getParameter(tokenUrlkey);
        }

        if(!StringUtils.isEmpty(authToken)){
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
            }catch (ExpiredJwtException e) {
                logger.warn("the token is expired."+e.getMessage());
                //throw  new CredentialsExpiredException(e.getMessage());
            }catch (Throwable e) {
                logger.warn("an illegal token attack."+e.getMessage());
                //throw  new BadCredentialsException(e.getMessage());
            }

            if (username != null) {
                try{
                    DBUserDetails dbUserDetails=this.dbUserDetailsService.getUserDetailsByUsername(username);

                    if (dbUserDetails!=null && jwtTokenUtil.validateToken(authToken, dbUserDetails)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(dbUserDetails, null, dbUserDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        logger.info("authenticated user " + username + ", setting security context.");
                    }else{
                        logger.info("authenticated user " + username + ", valid token.");
                    }
                }catch (Throwable throwable){
                    logger.warn("authenticated user " + username + " error "+throwable.getMessage());
                }
            }
        }
        chain.doFilter(request, response);
    }
}