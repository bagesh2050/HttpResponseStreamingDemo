package com.coreapi.stream.repository;

import static org.hibernate.jpa.QueryHints.HINT_FETCH_SIZE;

import java.util.stream.Stream;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.coreapi.stream.entity.UsersEntity;

@Transactional(readOnly = true)
@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Integer> {

	@Transactional(readOnly = true)
	@Query("select u from UsersEntity u")
	@QueryHints(value = @QueryHint(name = HINT_FETCH_SIZE, value = "0"))
	Stream<UsersEntity> findAllByCustomQueryAndStream();
}
