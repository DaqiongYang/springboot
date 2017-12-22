package com.github.xuybin.springboot.controller;

import com.github.xuybin.springboot.model.ErrInfo;
import com.github.xuybin.springboot.model.ErrThrowable;
import com.github.xuybin.springboot.model.ErrType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartException;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@ApiIgnore
@RestController
public class ErrController implements ErrorController {
    protected Logger logger =  LoggerFactory.getLogger(this.getClass());
    private static final String PATH = "/error";
    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping(value = PATH)
    ErrInfo error(HttpServletRequest request, HttpServletResponse response) {
        try{
            Map<String, Object> em=getErrorAttributes(request);
            ErrThrowable errThrowable=getError(request);
            //来着控制器的错误
            if(errThrowable!=null){
                response.setStatus(errThrowable.getErrInfo().getErrType().toHttpStatus());
                logger.warn(errThrowable.toString());
                return errThrowable.getErrInfo();
            }else {
                //来着Spring-boot&Spring-Security的内部错误
                ErrInfo errInfo=new ErrInfo().errType(ErrType.toErrType((String)em.get("error"))).errDescr((String) em.get("message"));
                if(errInfo.getErrType()==ErrType.UNKNOWN_ERR){
                    //未知错误符号
                    errInfo.setErrDescr(((String)em.get("error"))+"->"+((String) em.get("message")));
                }
                response.setStatus(errInfo.getErrType().toHttpStatus());
                logger.warn(errInfo.toString());
                return errInfo;
            }
        }catch (Throwable throwable){
            //处理错误时,异常报错
            ErrInfo errInfo= new ErrInfo().errType(ErrType.UNKNOWN_ERR).errDescr(throwable.getMessage());

            response.setStatus(errInfo.getErrType().toHttpStatus());
            logger.warn(errInfo.toString());
            return errInfo;
        }
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return errorAttributes.getErrorAttributes(requestAttributes,true);
    }

    private ErrThrowable getError(HttpServletRequest request) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        Throwable throwable=errorAttributes.getError(requestAttributes);
        if(throwable!=null){
            if(throwable instanceof ErrThrowable){
                return (ErrThrowable)throwable;
            }else if(throwable.getCause() instanceof ErrThrowable){
                return (ErrThrowable)throwable.getCause();
            }

            if(throwable instanceof HttpMessageNotReadableException){
                return new ErrThrowable().errType(ErrType.PARAM_FORMAT_ERR).errDescr(throwable.getMessage());
            }else if(throwable.getCause() instanceof HttpMessageNotReadableException){
                return new ErrThrowable().errType(ErrType.PARAM_FORMAT_ERR).errDescr(throwable.getCause().getMessage());
            }
            if(throwable instanceof MultipartException){
                return new ErrThrowable().errType(ErrType.MULTIPART_ERR).errDescr(throwable.getMessage());
            }else if(throwable.getCause() instanceof MultipartException){
                return new ErrThrowable().errType(ErrType.MULTIPART_ERR).errDescr(throwable.getCause().getMessage());
            }
        }
        return null;
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
