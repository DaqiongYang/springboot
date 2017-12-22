package com.github.xuybin.springboot.controller;

import com.github.xuybin.springboot.model.ErrInfo;
import com.github.xuybin.springboot.model.ErrThrowable;
import com.github.xuybin.springboot.model.ErrType;
import com.github.xuybin.springboot.model.FamilyInfo;
import com.github.xuybin.springboot.service.DBService;
import io.swagger.annotations.*;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.sqlite.SQLiteDataSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.UUID;

@Api( tags= { "数据处理" }, description = "上传,下载等接口")
@RestController
@RequestMapping("/data")
public class UploadController {
    protected Logger logger =  LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DBService dbService;

    private static final String UPLOAD_PATH = "upload";
    @ApiOperation(value = "数据上传",  tags={ "数据处理", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "返回数据库影响行数", response = Integer.class),
            @ApiResponse(code = 400, message = "错误(4XX:客户端引起的;5XX:服务发生的错误)", response = ErrInfo.class)})

    @RequestMapping(value = "/upload/",consumes = { "multipart/form-data"},method = RequestMethod.POST)
    ResponseEntity<Integer> uploadPost(@ApiParam(value = "认证Token" ,required=true,defaultValue="Bearer ")@RequestHeader(value="Authorization") String authorization,@ApiParam(value = "*.db文件",required=true) @RequestPart("dbFile") MultipartFile dbFile,HttpServletRequest req) throws ErrThrowable {
        if(!StringUtils.equals(FilenameUtils.getExtension(dbFile.getOriginalFilename()),"db")){
            throw new ErrThrowable().errType(ErrType.PARAM_ERR).errDescr("Incorrect file extension.");
        }
        try {
            File targetFile = new File(FilenameUtils.concat(req.getServletContext().getRealPath(UPLOAD_PATH),FilenameUtils.getBaseName(dbFile.getOriginalFilename())+UUID.randomUUID().toString()+".db"));
            FileUtils.forceMkdirParent(targetFile);
            FileUtils.writeByteArrayToFile(targetFile,dbFile.getBytes());
            SQLiteDataSource dataSource= new SQLiteDataSource();
            dataSource.setUrl("jdbc:sqlite:"+targetFile.getAbsolutePath());
            QueryRunner runner=new QueryRunner(dataSource);
            //String sql="SELECT * FROM "+tableName+" where id =? ADN name=? ";
            //User u=runner.query(sql, new BeanHandler<User>(User.class),"2","myname");
            String sql="SELECT * FROM tb_family_info;";
            List<FamilyInfo> familyInfos=runner.query(sql, new BeanListHandler<FamilyInfo>(FamilyInfo.class));
            Integer rowsAffect=0;
            if(familyInfos!=null && familyInfos.size()>=0){
//                logger.info("Begin insert familyInfo:"+ familyInfos.get(0).toString());
//                for(FamilyInfo familyInfo:familyInfos){
//                    rowsAffect+=dbService.insertFamilyInfo(familyInfo);
//                }
                rowsAffect+=dbService.insertFamilyInfoList(familyInfos);
            }else {
                logger.info("red familyInfo is empty.");
            }
            return ResponseEntity.ok().body(rowsAffect);
        } catch (Throwable t){
            throw new ErrThrowable().errType(ErrType.SERVER_ERR).errDescr(t.getMessage());
        }
    }

    @ApiOperation(value = "数据下载", tags={ "数据处理", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "文件流", response = Void.class),
            @ApiResponse(code = 400, message = "错误(4XX:客户端引起的;5XX:服务发生的错误)", response = ErrInfo.class)})

    @RequestMapping(value = "/download/{fileName:.+}",consumes = { "*/*"}, method = RequestMethod.GET)
    ResponseEntity<Void> downloadGet(@ApiParam(value = "认证Token" ,required=true,defaultValue="Bearer ")@RequestHeader(value="Authorization",required = false) String authorization,@PathVariable("fileName") String fileName,HttpServletRequest req,HttpServletResponse resp) throws ErrThrowable {
        File targetFile = new File(FilenameUtils.concat(req.getServletContext().getRealPath(UPLOAD_PATH),fileName));
        if(!StringUtils.equals(FilenameUtils.getExtension(fileName),"db") || !targetFile.exists()){
            throw new ErrThrowable().errType(ErrType.PARAM_ERR).errDescr("Incorrect file name.");
        }
        try {
            resp.setContentType("application/octet-stream");
            resp.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            byte[] data=FileUtils.readFileToByteArray(targetFile);
            resp.addHeader("Content-Length", "" + data.length);
            resp.getOutputStream().write(data);
            resp.getOutputStream().flush();
            resp.getOutputStream().close();
            return ResponseEntity.ok().build();
        } catch (Throwable t){
            throw new ErrThrowable().errType(ErrType.SERVER_ERR).errDescr(t.getMessage());
        }
    }
}
