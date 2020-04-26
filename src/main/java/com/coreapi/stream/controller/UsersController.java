package com.coreapi.stream.controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.coreapi.stream.entity.UsersEntity;
import com.coreapi.stream.repository.UsersRepository;

@RestController
@RequestMapping("/api")
public class UsersController {
	@Autowired
	private UsersRepository usersRepository;

	@Transactional(readOnly = true)
	@GetMapping(value = "/userstream")
	public ResponseEntity<StreamingResponseBody> fetchUsersStreaming() {

		StreamingResponseBody stream = new StreamingResponseBody() {

			@Transactional(readOnly = true)
			@Override
			public void writeTo(OutputStream outputStream) throws IOException {

				Stream<UsersEntity> usersResultStream = usersRepository.findAllByCustomQueryAndStream();

				try (ObjectOutputStream oos = new ObjectOutputStream(outputStream)) {

					usersResultStream.forEach(user -> {
						try {
							oos.write(user.toString().getBytes());
						} catch (IOException e) {
							e.printStackTrace();
						}
					});
				}
			}
		};

		return new ResponseEntity<>(stream, HttpStatus.OK);
	}
}
