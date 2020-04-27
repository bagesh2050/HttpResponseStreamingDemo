package com.coreapi.stream.controller;

import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.coreapi.stream.model.request.UserListingRequest;
import com.coreapi.stream.service.UserListingService;

@RestController
@RequestMapping("/api")
public class UserListingController {
	@Autowired
	private UserListingService userListingService;
	private UserListingRequest requestParams;

	@GetMapping(value = "/userlisting")
	public ResponseEntity<StreamingResponseBody> fetchUsersList(UserListingRequest requestParams) {
		return userListingService.getUsersList(requestParams);
	}

	@GetMapping(value = "/userlistingstream")
	public ResponseEntity<StreamingResponseBody> fetchUsersStream(UserListingRequest requestParams) {
		return userListingService.getUsersAsStream(requestParams);
	}

	@GetMapping(value = "/userlistingstream2")
	public ResponseEntity<StreamingResponseBody> fetchUsersStream2(UserListingRequest requestParams) {
		this.requestParams = requestParams;
		StreamingResponseBody stream = this::writeTo;

		return new ResponseEntity<>(stream, HttpStatus.OK);
	}

	private void writeTo(OutputStream outputStream) {
		userListingService.writeToOutputStream(outputStream, requestParams);
	}
}
