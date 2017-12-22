package com.github.xuybin.springboot.model;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;


/**
 * 验证用户的信息,实现org.springframework.security.core.userdetails.UserDetails接口并扩展authCode字段.
 */
public class DBUserDetails implements org.springframework.security.core.userdetails.UserDetails{
    private String username = "";
    private String password = "";
    private Collection<? extends GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

    private String userID= "";//扩展 用户唯一标识(用于关联信息,不要出现在token信息里)
    private  Date lastPasswordResetDate;//扩展 最后一次重置密码的时间(重置后,最后一次登陆要失效)
    private  Date lastPasswordLoginDate;//扩展 最后一颁发token时间(登陆时间)

    public DBUserDetails userID(String userID) {
        this.userID = userID;
        return this;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public DBUserDetails lastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
        return this;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public DBUserDetails lastPasswordLoginDate(Date lastPasswordLoginDate) {
        this.lastPasswordLoginDate = lastPasswordLoginDate;
        return this;
    }

    public Date getLastPasswordLoginDate() {
        return lastPasswordLoginDate;
    }

    public void setLastPasswordLoginDate(Date lastPasswordLoginDate) {
        this.lastPasswordLoginDate = lastPasswordLoginDate;
    }

    public DBUserDetails username(String username) {
        this.username = username;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public DBUserDetails password(String password) {
        this.password = password;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public DBUserDetails accountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
        return this;
    }


    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public DBUserDetails accountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
        return this;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public DBUserDetails credentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
        return this;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public DBUserDetails enabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}

