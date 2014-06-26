package com.onlinever.usercenter.model;

import java.util.Date;

import com.onlinever.commons.util.Utilities;

public class User {
    private Integer id;
    
    private String userName;
    
    private String loginName;

    private String password;

    private String payPassword;

    private String mobile;

    private String email;

    private Byte mobileStatus;

    private Byte emailStatus;

    private String mobileVerifyCode;

    private String emailVerifyCode;

    private String registerIp;

    private Date registerTime;

    private Date lastLoginTime;

    private String lastLoginIp;

    private Date lastModifyTime;

    private Byte gender;

    private String realName;

    private Date birthday;

    private Byte isActive;

    private Integer cityregionId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * 用户名
     */
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    /**
     * 密码
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Utilities.encodePassword(password);
    }

    /**
     * 支付密码
     */
    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }
    /**
     *手机号 
     */
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    /**
     *邮箱 
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    /**
     *手机验证状态 
     */
    public Byte getMobileStatus() {
        return mobileStatus;
    }

    public void setMobileStatus(Byte mobileStatus) {
        this.mobileStatus = mobileStatus;
    }
    /**
     *邮箱验证状态 
     */
    public Byte getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(Byte emailStatus) {
        this.emailStatus = emailStatus;
    }
    /**
     *手机验证码 
     */
    public String getMobileVerifyCode() {
        return mobileVerifyCode;
    }

    public void setMobileVerifyCode(String mobileVerifyCode) {
        this.mobileVerifyCode = mobileVerifyCode;
    }
    /**
     *邮箱验证码
     */
    public String getEmailVerifyCode() {
        return emailVerifyCode;
    }

    public void setEmailVerifyCode(String emailVerifyCode) {
        this.emailVerifyCode = emailVerifyCode;
    }
    /**
     *注册IP
     */
    public String getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }
    /**
     *注册时间
     */
    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }
    /**
     *最后登录时间
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
    /**
     *最后登录IP
     */
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }
    /**
     *最后修改时间
     */
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }
    /**
     *性别
     */
    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }
    /**
     *真实姓名
     */
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
    /**
     *生日
     */
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    /**
     *是否可用
     */
    public Byte getIsActive() {
        return isActive;
    }

    public void setIsActive(Byte isActive) {
        this.isActive = isActive;
    }
    /**
     *所在区/县
     */
    public Integer getCityregionId() {
        return cityregionId;
    }

    public void setCityregionId(Integer cityregionId) {
        this.cityregionId = cityregionId;
    }
    /**
     *登录用户名 
     */
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}