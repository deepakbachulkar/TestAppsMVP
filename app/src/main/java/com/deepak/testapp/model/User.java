package com.deepak.testapp.model;

import java.io.Serializable;


public class User implements Serializable {
	String userName, mobileNo;

	public User(String userName, String mobileNo){
		this.userName = userName;
		this.mobileNo = mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public String getUserName() {
		return userName;
	}

}
