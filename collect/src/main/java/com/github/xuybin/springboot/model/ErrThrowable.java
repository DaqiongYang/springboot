package com.github.xuybin.springboot.model;


public class ErrThrowable extends Throwable{

    private ErrInfo errInfo = new ErrInfo();

    public ErrInfo getErrInfo() {
        return errInfo;
    }

    public ErrThrowable errType(ErrType errType) {
        this.errInfo.setErrType(errType);
        return this;
    }

    public ErrThrowable errDescr(String errDescr) {
        this.errInfo.setErrDescr(errDescr);
        return this;
    }

    @Override
    public String toString() {
        return "ErrThrowable{" +
                "errInfo=" + errInfo +
                '}';
    }
}
