package com.github.xuybin.springboot.controller;

import com.github.xuybin.springboot.model.ErrInfo;
import com.github.xuybin.springboot.model.ErrThrowable;
import com.github.xuybin.springboot.model.FamilyInfo;
import com.github.xuybin.springboot.model.ErrType;
import com.github.xuybin.springboot.service.DBService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api( tags= { "测试" }, description = "测试事务")
@RestController
public class TestController {
    @Autowired
    private DBService dbService;

    @ApiOperation(value = "事务测试", tags={ "测试" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "返回数据库影响行数", response = Integer.class),
            @ApiResponse(code = 400, message = "错误(4XX:客户端引起的;5XX:服务发生的错误)", response = ErrInfo.class)})

    @RequestMapping(value = "/auth/test/", method = RequestMethod.GET)
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = ErrThrowable.class)
    public ResponseEntity<Integer> Transactional() throws ErrThrowable {
        Integer i=0;
        try {
            //`family_id` VARCHAR(225) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '户唯一标识',
            //  `person_name` VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '户主名字',
            //  `person_card` VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '户主名字身份证',
            // PRIMARY KEY (`family_id`),
            //  UNIQUE KEY `person_card` (`person_card`)
            FamilyInfo familyInfo1=new FamilyInfo();
            familyInfo1.setFamily_id("family_id1");
            familyInfo1.setPerson_name("person_name1");
            familyInfo1.setPerson_card("52201");

            FamilyInfo familyInfo2=new FamilyInfo();
            familyInfo2.setFamily_id("family_id2");
            familyInfo2.setPerson_name("person_name2");
            familyInfo2.setPerson_card("52202");

            FamilyInfo familyInfo3=new FamilyInfo();
            familyInfo3.setFamily_id("family_id3");
            familyInfo3.setPerson_name("person_name3");
            familyInfo3.setPerson_card("52203");

            i+= dbService.insertFamilyInfo(familyInfo1);
            i+=dbService.insertFamilyInfo(familyInfo2);
            i+=dbService.insertFamilyInfo(familyInfo1);

            return ResponseEntity.ok().body(i);
        }catch (Throwable t){
            throw new ErrThrowable().errType(ErrType.PARAM_ERR).errDescr(t.getMessage());
        }
    }
}
