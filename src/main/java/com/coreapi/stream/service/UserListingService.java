package com.coreapi.stream.service;

import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.coreapi.stream.model.UserListingVO;
import com.coreapi.stream.model.request.UserListingRequest;
import com.coreapi.stream.repositoryimpl.UserListingRepositoryImpl;

@Service
public class UserListingService {
	@Autowired
	private UserListingRepositoryImpl userListingRepository;

	public ResponseEntity<StreamingResponseBody> getUsersList(UserListingRequest userListingRequest) {

		StreamingResponseBody stream = outputStream -> {

			List<UserListingVO> userDataResult = userListingRepository.getUsersListIncludingChannel(userListingRequest);

			try (ObjectOutputStream oos = new ObjectOutputStream(outputStream)) {

				for (UserListingVO user : userDataResult) {
					oos.write(user.toString().getBytes());
				}
			}
		};

		return new ResponseEntity<>(stream, HttpStatus.OK);
	}

	public ResponseEntity<StreamingResponseBody> getUsersAsStream(UserListingRequest userListingRequest) {

		StreamingResponseBody stream = outputStream -> {

			Stream<String> userDataResult = userListingRepository.getStreamOfUsersIncludingChannel(userListingRequest);

			try (ObjectOutputStream oos = new ObjectOutputStream(outputStream)) {

				Iterator<String> itr = userDataResult.iterator();

				while (itr.hasNext()) {
					oos.write(itr.next().getBytes());
				}

				userDataResult.close();
			}
		};

		return new ResponseEntity<>(stream, HttpStatus.OK);
	}
}
