package com.coreapi.stream.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

	@GetMapping(value = "/userlisting")
	public ResponseEntity<StreamingResponseBody> fetchUsersList(UserListingRequest requestParams) {
		return userListingService.getUsersList(requestParams);
	}

	@GetMapping(value = "/userlistingstream")
	public ResponseEntity<StreamingResponseBody> fetchUsersStream(UserListingRequest requestParams) {
		return userListingService.getUsersAsStream(requestParams);
	}
}
