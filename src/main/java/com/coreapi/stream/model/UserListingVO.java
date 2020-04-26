package com.coreapi.stream.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserListingVO {
	private String msisdn = "";
	private String loginId = "";
	private String userType = "";
	private String domainCode = "";
	private String domainName = "";
	private String categoryCode = "";
	private String categoryName = "";
	private String status = "";
	private String createdOn = "";
	@JsonIgnore
	private BigDecimal myRow;
	@JsonIgnore
	private BigDecimal totalCount;

	public void setMsisdn(String msisdn) {
		if (msisdn != null)
			this.msisdn = msisdn;

		System.out.println("Msisdn=" + msisdn);
	}

	public void setLoginId(String loginId) {
		if (loginId != null)
			this.loginId = loginId;
	}

	public void setUserType(String userType) {
		if (userType != null)
			this.userType = userType;
	}

	public void setDomainCode(String domainCode) {
		if (domainCode != null)
			this.domainCode = domainCode;
	}

	public void setDomainName(String domainName) {
		if (domainName != null)
			this.domainName = domainName;
	}

	public void setCategoryCode(String categoryCode) {
		if (categoryCode != null)
			this.categoryCode = categoryCode;
	}

	public void setCategoryName(String categoryName) {
		if (categoryName != null)
			this.categoryName = categoryName;
	}

	public void setStatus(String status) {
		if (status != null)
			this.status = status;
	}

	public void setCreatedOn(String createdOn) {
		if (createdOn != null)
			this.createdOn = createdOn;
	}

	public void setMyRow(BigDecimal myRow) {
		this.myRow = myRow;
	}

	public void setTotalCount(BigDecimal totalCount) {
		this.totalCount = totalCount;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public String getLoginId() {
		return loginId;
	}

	public String getUserType() {
		return userType;
	}

	public String getDomainCode() {
		return domainCode;
	}

	public String getDomainName() {
		return domainName;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public String getStatus() {
		return status;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public BigDecimal getMyRow() {
		return myRow;
	}

	public BigDecimal getTotalCount() {
		return totalCount;
	}

	@Override
	public String toString() {
		return "UserListingVO [msisdn=" + msisdn + ", loginId=" + loginId + ", userType=" + userType + ", domainCode="
				+ domainCode + ", domainName=" + domainName + ", categoryCode=" + categoryCode + ", categoryName="
				+ categoryName + ", status=" + status + ", createdOn=" + createdOn + "]";
	}
}
