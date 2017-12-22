package com.github.xuybin.springboot.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;



/**
 * 用户实体,登录必须字段.
 **/
@ApiModel(description = "用户实体,登录必须字段.")
public class AuthUser implements Serializable {
  private String username = "";
  private String idCard = null;
  private String mobileNO = null;
  private String password = "";

  private String verifyCode = null;

  public AuthUser username(String username) {
    this.username = username;
    return this;
  }

  @ApiModelProperty(required = true, value = "用户名")
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public AuthUser idCard(String idCard) {
    this.idCard = idCard;
    return this;
  }

  @ApiModelProperty(required = false, value = "身份证号")
  public String getIdCard() {
    return idCard;
  }

  public void setIdCard(String idCard) {
    this.idCard = idCard;
  }



  public AuthUser mobileNO(String mobileNO) {
    this.mobileNO = mobileNO;
    return this;
  }

  @ApiModelProperty(required = false, value = "手机号")
  public String getMobileNO() {
    return mobileNO;
  }

  public void setMobileNO(String mobileNO) {
    this.mobileNO = mobileNO;
  }

  public AuthUser password(String password) {
    this.password = password;
    return this;
  }

  @ApiModelProperty(required = true, value = "密码")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }



  public AuthUser verifyCode(String verifyCode) {
    this.verifyCode = verifyCode;
    return this;
  }
  @ApiModelProperty(required = true, value = "图形验证码或手机验证码.")
  public String getVerifyCode() {
    return verifyCode;
  }

  public void setVerifyCode(String verifyCode) {
    this.verifyCode = verifyCode;
  }

}

