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
import com.fasterxml.jackson.databind.json.JsonMapper;

@Service
public class UserService {

	@Autowired
	private UsersRepository usersRepository;

	@Transactional(readOnly = true)
	public void writeToOutputStream(final OutputStream outputStream) {
		try (Stream<UsersEntity> usersResultStream = usersRepository.findAllByCustomQueryAndStream()) {
			//try (ObjectOutputStream oos = new ObjectOutputStream(outputStream)) {

			JsonMapper mapper = new JsonMapper();
			
				usersResultStream.forEach(emp -> {
					try {
						
						mapper.writeValue(outputStream, emp);
				
						//outputStream.flush();
						
						//oos.write(emp.toString().getBytes());
						//oos.flush();
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			/*} catch (IOException e) {
				e.printStackTrace();
			}*/
				
				try {
					outputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
		}
	}
}
