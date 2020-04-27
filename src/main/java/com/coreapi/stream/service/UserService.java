package com.coreapi.stream.service;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coreapi.stream.entity.UsersEntity;
import com.coreapi.stream.repository.UsersRepository;

@Service
public class UserService {

	@Autowired
	private UsersRepository usersRepository;

	@Transactional(readOnly = true)
	public void writeToOutputStream(final OutputStream outputStream) {
		try (Stream<UsersEntity> usersResultStream = usersRepository.findAllByCustomQueryAndStream()) {
			try (ObjectOutputStream oos = new ObjectOutputStream(outputStream)) {

				usersResultStream.forEach(emp -> {
					try {
						oos.write(emp.toString().getBytes());
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
