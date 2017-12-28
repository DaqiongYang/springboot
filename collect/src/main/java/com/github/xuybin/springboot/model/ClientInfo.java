package com.github.xuybin.springboot.model;

import java.util.Date;

public class ClientInfo {
    private String client_id;//'客户端身份id',
    private String client_secret;// '客户端身份秘钥',
    private Integer enabled;//'是否可用',
    private Date create_time ;//'创建时间',
    private Date update_time;//'更新时间',
    private Date last_password_login_date;//'最后一颁发token时间(登陆时间)',

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public Date getLast_password_login_date() {
        return last_password_login_date;
    }

    public void setLast_password_login_date(Date last_password_login_date) {
        this.last_password_login_date = last_password_login_date;
    }
}
