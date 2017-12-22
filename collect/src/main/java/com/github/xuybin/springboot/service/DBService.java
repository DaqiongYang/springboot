package com.github.xuybin.springboot.service;

import com.github.xuybin.springboot.model.FamilyInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DBService {

    @Insert("INSERT INTO `collect_db`.`family_info` (`family_id`,`person_name`,`person_card`,`family_total`,`house_status`,`house_area`,`poor_status`,`household_where_code`,`operator`)  " +
            "VALUES(#{family_id},#{person_name},#{person_card},#{family_total},#{house_status},#{house_area},#{poor_status},#{household_where_code},#{operator})")
    int insertFamilyInfo(FamilyInfo FamilyInfo);

    @Insert("<script>INSERT INTO `collect_db`.`family_info` (`family_id`,`person_name`,`person_card`,`family_total`,`house_status`,`house_area`,`poor_status`,`household_where_code`,`operator`)  " +
            "VALUES " +
            "<foreach collection =\"familyInfoList\" item=\"familyInfo\" index= \"index\" separator =\",\">" +
            "(#{familyInfo.family_id},#{familyInfo.person_name},#{familyInfo.person_card},#{familyInfo.family_total},#{familyInfo.house_status},#{familyInfo.house_area},#{familyInfo.poor_status},#{familyInfo.household_where_code},#{familyInfo.operator})" +
            "</foreach >" +
            "</script>")
    int insertFamilyInfoList(@Param("familyInfoList") List<FamilyInfo> familyInfoList);
}
