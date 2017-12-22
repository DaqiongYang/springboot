package com.github.xuybin.springboot.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 错误对象.
 */
@ApiModel(description = "错误信息.")
public class ErrInfo implements Serializable {

  private ErrType errType = ErrType.UNKNOWN_ERR;

  private String errDescr = "";

  @ApiModelProperty(required = true, value = "错误类型.")
  public ErrType getErrType() {
    return errType;
  }

  public void setErrType(ErrType errType) {
    this.errType = errType;
  }

  public ErrInfo errType(ErrType errType) {
    this.errType = errType;
    return this;
  }

   /**
   * 错误信息.
   * @return errorDescription
  **/
  @ApiModelProperty(value = "错误说明.")
  public String getErrDescr() {
    return errDescr;
  }

  public void setErrDescr(String errDescr) {
    this.errDescr = errDescr;
  }

  public ErrInfo errDescr(String errDescr) {
    this.errDescr = errDescr;
    return this;
  }

  @Override
  public String toString() {
    return "ErrInfo{" +
            "errType=" + errType +
            ", errDescr='" + errDescr + '\'' +
            '}';
  }
}

