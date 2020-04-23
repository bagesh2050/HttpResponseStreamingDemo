package com.coreapi.stream.model.request;

public class UserListingRequest {
	private String userType;
	private String channelCategoryCode;
	private String channelDomainCode;

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getChannelCategoryCode() {
		return channelCategoryCode;
	}

	public void setChannelCategoryCode(String channelCategoryCode) {
		this.channelCategoryCode = channelCategoryCode;
	}

	public String getChannelDomainCode() {
		return channelDomainCode;
	}

	public void setChannelDomainCode(String channelDomainCode) {
		this.channelDomainCode = channelDomainCode;
	}

	@Override
	public String toString() {
		return "UserListingRequest [userType=" + userType + ", channelCategoryCode=" + channelCategoryCode
				+ ", channelDomainCode=" + channelDomainCode + "]";
	}
}
