package com.github.xuybin.springboot.service;

import com.github.xuybin.springboot.model.DBUserDetails;
import com.github.xuybin.springboot.model.AuthUser;
import org.apache.ibatis.annotations.*;

import java.util.Date;

/**
 * Created by xuybin on 2016/9/9.
 */
@Mapper
public interface DBUserDetailsService{
    @Select("SELECT `user_id` AS userID,`user_name` AS username,`password` AS PASSWORD,TRUE AS credentialsNonExpired,TRUE AS accountNonExpired,TRUE AS accountNonLocked,`enabled` AS enabled,`last_password_reset_date` AS lastPasswordResetDate,`last_password_login_date` AS lastPasswordLoginDate " +
            " FROM `auth_user` WHERE mobile_no=#{mobileNO} AND mobile_no IS NOT NULL")
    DBUserDetails getUserDetailsByMobileNO(String mobileNO);

    @Select("SELECT `user_id` AS userID,`user_name` AS username,`password` AS PASSWORD,TRUE AS credentialsNonExpired,TRUE AS accountNonExpired,TRUE AS accountNonLocked,`enabled` AS enabled,`last_password_reset_date` AS lastPasswordResetDate,`last_password_login_date` AS lastPasswordLoginDate " +
            " FROM `auth_user` WHERE `id_card`=#{idCard} AND `id_card` IS NOT NULL")
    DBUserDetails getUserDetailsByIdCard(String idCard);

    @Select("SELECT `user_id` AS userID,`user_name` AS username,`password` AS PASSWORD,TRUE AS credentialsNonExpired,TRUE AS accountNonExpired,TRUE AS accountNonLocked,`enabled` AS enabled,`last_password_reset_date` AS lastPasswordResetDate,`last_password_login_date` AS lastPasswordLoginDate " +
            " FROM `auth_user` WHERE `user_name`=#{username} AND `user_name` IS NOT NULL")
    DBUserDetails getUserDetailsByUsername(String username);

    @Update("UPDATE `auth_user` SET `last_password_login_date`=CURRENT_TIMESTAMP WHERE `user_id`=#{userID} ")
    int updateLastPasswordLoginDate(DBUserDetails dbUserDetails);

    @Update("UPDATE `auth_user` SET `last_password_reset_date`=CURRENT_TIMESTAMP WHERE `user_id`=#{userID} ")
    int updateLastPasswordResetDate(DBUserDetails dbUserDetails);

    @Insert("INSERT INTO `auth_user`(`user_id`,`id_card`,`user_name`,`mobile_no`,`password`,`device`,`device_platform`) " +
            " VALUES (#{uuid},#{authUser.idCard},#{authUser.username},#{authUser.mobileNO},#{authUser.password},#{device},#{devicePlatform})")
    int insertOauthUser(@Param("uuid")String uuid,@Param("authUser") AuthUser authUser,@Param("device")String device,@Param("devicePlatform")String devicePlatform);

    @Select("SELECT CURRENT_TIMESTAMP")
    Date dbNow();
}
