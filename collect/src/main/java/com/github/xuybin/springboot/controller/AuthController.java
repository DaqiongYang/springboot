package com.github.xuybin.springboot.controller;

import com.github.xuybin.springboot.configuration.JwtTokenUtil;
import com.github.xuybin.springboot.configuration.KaptchaExtendImpl;
import com.github.xuybin.springboot.model.*;
import com.github.xuybin.springboot.service.DBUserDetailsService;
import io.jsonwebtoken.JwtException;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DevicePlatform;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

@Api( tags= { "认证登陆" }, description = "注册,登陆等接口")
@RestController
@RequestMapping("/auth")
public class AuthController extends KaptchaExtendImpl {
    protected Logger logger =  LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DBUserDetailsService dbUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${verifycode.expiration}")
    private int verifyCodeExpiration;

    @ApiOperation(value = "图像验证码", notes = "获取图像验证码的同时,在session里写入验证码的值.", tags={ "认证登陆" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "返回验证码图片", response = Void.class) })
    @RequestMapping(value = "/verify/",
            produces = { "image/jpeg"},
            method = RequestMethod.GET)
    ResponseEntity<Void> verifyCodeJpgGet(HttpServletRequest req, HttpServletResponse resp) throws ErrThrowable {
        try {
            super.captcha(req, resp);
        } catch (Throwable throwable) {
            throw new ErrThrowable().errType(ErrType.SERVER_ERR).errDescr(throwable.getMessage());
        }
        return ResponseEntity.ok().build();
    }


    @ApiOperation(value = "登陆接口", tags={ "认证登陆"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "返回Token信息", response = Token.class),
            @ApiResponse(code = 400, message = "错误(4XX:客户端引起的;5XX:服务发生的错误)", response = ErrInfo.class)})
    @RequestMapping(value = "/login/", method = RequestMethod.POST)
    ResponseEntity<Token> loginPost(@ApiParam(value = "认证所需的用户信息" ,required=true ) @RequestBody AuthUser authUser, Device device, HttpServletRequest request) throws ErrThrowable{
        //前置检查
        if (StringUtils.isEmpty(authUser.getVerifyCode())
                || request.getSession().getAttribute(sessionKeyDateValue) == null
                || request.getSession().getAttribute(sessionKeyValue) == null
                || (new Date().getTime() - ((Date) request.getSession().getAttribute(sessionKeyDateValue)).getTime()) > verifyCodeExpiration * 1000
                || !StringUtils.equals((String)request.getSession().getAttribute(sessionKeyValue),authUser.getVerifyCode()) ) {
            throw new ErrThrowable().errType(ErrType.PARAM_ERR).errDescr("Incorrect verify code.");
        }
        DBUserDetails dbUserDetails=null;
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        try {
            dbUserDetails = dbUserDetailsService.getUserDetailsByUsername(authUser.getUsername());
        } catch (Throwable t){
            throw new ErrThrowable().errType(ErrType.SERVER_ERR).errDescr(t.getMessage());
        }
        if(dbUserDetails==null ||  !passwordEncoder.matches(authUser.getPassword(),dbUserDetails.getPassword())){
            throw new ErrThrowable().errType(ErrType.PARAM_ERR).errDescr("Incorrect username or password.");
        }
        try {
            //更新最后登陆时间,之前颁发的TOKEN立即失效
            dbUserDetailsService.updateLastPasswordLoginDate(dbUserDetails);
            //使用db的时间,确保,token在最后一次登陆后生成(保证同一时刻只有一人登陆).
            Date now=  dbUserDetailsService.dbNow();
            String accessToken=jwtTokenUtil.generateToken(dbUserDetails, device,now);
            Token token= new Token().accessToken(accessToken).tokenType("bearer")
                    .issuedAt(jwtTokenUtil.getIssuedAtDateFromToken(accessToken).getTime())
                    .expiresIn(jwtTokenUtil.getExpirationDateFromToken(accessToken).getTime());
            //返回token
            return ResponseEntity.ok().body(token);
        }catch (JwtException t){
            throw new ErrThrowable().errType(ErrType.SERVER_ERR).errDescr(t.getMessage());
        } catch (Throwable t){
            throw new ErrThrowable().errType(ErrType.SERVER_ERR).errDescr(t.getMessage());
        }
    }

    @ApiOperation(value = "注册接口", tags={ "认证登陆" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "返回数据库影响行数", response = Integer.class),
            @ApiResponse(code = 400, message = "错误(4XX:客户端引起的;5XX:服务发生的错误)", response = ErrInfo.class)})

    @RequestMapping(value = "/register/", method = RequestMethod.POST)
    ResponseEntity<Integer> registerPost(@ApiParam(value = "认证所需的用户信息" ,required=true ) @RequestBody AuthUser authUser, Device device, HttpServletRequest request) throws ErrThrowable{

        //前置检查
        if (StringUtils.isEmpty(authUser.getVerifyCode())
                || request.getSession().getAttribute(sessionKeyDateValue) == null
                || request.getSession().getAttribute(sessionKeyValue) == null
                || (new Date().getTime() - ((Date) request.getSession().getAttribute(sessionKeyDateValue)).getTime()) > verifyCodeExpiration * 1000
                || !StringUtils.equals((String)request.getSession().getAttribute(sessionKeyValue),authUser.getVerifyCode()) ) {
            throw new ErrThrowable().errType(ErrType.PARAM_ERR).errDescr("Incorrect verify code.");
        }

        String deviceStr="PC";
        if(device.isMobile()){
            deviceStr="Mobile";
        }else if(device.isTablet()){
            deviceStr="Tablet";
        }
        String devicePlatformStr="Windows";
        if(device.getDevicePlatform()== DevicePlatform.ANDROID){
            devicePlatformStr="ANDROID";
        }else if(device.getDevicePlatform()== DevicePlatform.IOS){
            devicePlatformStr="IOS";
        }
        if(StringUtils.isEmpty(authUser.getUsername()) || StringUtils.isEmpty(authUser.getPassword())){
            throw new ErrThrowable().errType(ErrType.SERVER_ERR).errDescr("username or password is empty.");
        }
        try {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            authUser.setPassword(passwordEncoder.encode(authUser.getPassword()));
            //返回影响行数
            return ResponseEntity.ok().body(dbUserDetailsService.insertOauthUser(UUID.randomUUID().toString(),authUser,deviceStr,devicePlatformStr));
        }catch (Throwable t){
            throw new ErrThrowable().errType(ErrType.PARAM_ERR).errDescr(t.getMessage());
        }
    }
}
