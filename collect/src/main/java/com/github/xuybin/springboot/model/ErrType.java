package com.github.xuybin.springboot.model;

public enum ErrType {
    PARAM_ERR(400,"PARAM_ERR"),
    PARAM_FORMAT_ERR(400,"PARAM_FORMAT_ERR"),
    UNAUTHORIZED_ERR(401,"UNAUTHORIZED_ERR"),
    FORBIDDEN_ERR(403,"FORBIDDEN_ERR"),
    NOTFOUND_ERR(404,"NOTFOUND_ERR"),
    METHOD_ERR(405,"METHOD_ERR"),
    SERVER_ERR(500,"SERVER_ERR"),
    MULTIPART_ERR(500,"MULTIPART_ERR"),
    UNKNOWN_ERR(500,"UNKNOWN_ERR"),
   ;
    final String string;
    final int httpStatus;
    private ErrType(int httpStatus,String string) {
        this.httpStatus = httpStatus;
        this.string = string;

    }

    public int toHttpStatus() {
        return httpStatus;
    }
    @Override
    public String toString() {
        return string;
    }

    public static ErrType toErrType(String error){
       switch (error){
           case "Method Not Allowed":
               return ErrType.METHOD_ERR;
           case "Forbidden":
               return ErrType.FORBIDDEN_ERR;
           case "Unauthorized":
               return ErrType.UNAUTHORIZED_ERR;
           case "Not Found":
               return ErrType.NOTFOUND_ERR;
           default:
               return ErrType.UNKNOWN_ERR;
       }
    }
}
