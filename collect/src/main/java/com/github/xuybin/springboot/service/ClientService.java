package com.github.xuybin.springboot.service;


import com.github.xuybin.springboot.model.ClientInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClientService {

    @Select("SELECT * FROM `client_user` ORDER BY `client_id` LIMIT #{start},#{size} ")
     List<ClientInfo> getPaginator(@Param("start") int start, @Param("size") int size);

    @Select("SELECT COUNT(1) FROM `client_user`")
    int getPaginatorTotalCount();


    @Insert("<script>INSERT INTO `client_user` (`client_id`,`client_secret`,`enabled`)  " +
            "VALUES " +
            "<foreach collection =\"clientInfoList\" item=\"item\" index= \"index\" separator =\",\">" +
            "(#{item.client_id},#{item.client_secret},#{item.enabled})" +
            "</foreach >" +
            "</script>")
    int insert(@Param("clientInfoList") List<ClientInfo> clientInfoList);

    @Update("<script>UPDATE `client_user` SET `update_time`=CURRENT_TIMESTAMP " +
            " <if test=\"client_secret!=null and client_secret!=''\"> ,`client_secret`=#{client_secret} </if> " +
            " <if test=\"enabled!=null and enabled!=''\"> ,`enabled`=#{enabled} </if> " +
            " WHERE `client_id`=#{client_id} </script>")
    int  update(ClientInfo clientInfo);

    @Delete(" Delete FROM `client_user` WHERE `client_id`=#{client_id}")
    int delete(String client_id);
}
