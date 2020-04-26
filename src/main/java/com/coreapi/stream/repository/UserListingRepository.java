package com.coreapi.stream.repository;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.repository.NoRepositoryBean;

import com.coreapi.stream.model.UserListingVO;
import com.coreapi.stream.model.request.UserListingRequest;

@NoRepositoryBean
public interface UserListingRepository {
	List<UserListingVO> getUsersListIncludingChannel(final UserListingRequest userListingRequest);

	Stream<String> getStreamOfUsersIncludingChannel(final UserListingRequest userListingRequest);
}
