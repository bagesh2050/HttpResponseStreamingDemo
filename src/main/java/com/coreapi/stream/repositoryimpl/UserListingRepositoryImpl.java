package com.coreapi.stream.repositoryimpl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.coreapi.stream.model.UserListingVO;
import com.coreapi.stream.model.request.UserListingRequest;
import com.coreapi.stream.repository.UserListingRepository;
import com.coreapi.stream.transformer.FluentHibernateResultTransformer;

@Repository
public class UserListingRepositoryImpl implements UserListingRepository {
	@PersistenceContext
	private EntityManager mEntityManager;

	@Override
	public List<UserListingVO> getUsersListIncludingChannel(UserListingRequest userListingRequest) {
		String query = getUsersQueryIncludingChannel(userListingRequest);

		try {
			if (!query.isEmpty()) {
				SQLQuery sqlQuery = mEntityManager.unwrap(Session.class).createSQLQuery(query);
				sqlQuery.setTimeout(10000);
				return sqlQuery.setResultTransformer(new FluentHibernateResultTransformer(UserListingVO.class)).list();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Collections.emptyList();
	}

	private String getUsersQueryIncludingChannel(UserListingRequest userListingRequest) {
		return "SELECT msisdn, " + "  loginId, " + "  userType, " + "  domainCode, " + "  domainName, "
				+ "  categoryCode, " + "  categoryName, " + "  status, " + "  createdOn , "
				+ "  COUNT(*) over() AS totalCount, " + "  Row_Number() OVER (ORDER BY created_on DESC) MyRow "
				+ "FROM " + "  (SELECT md.domain_code               AS domainCode, "
				+ "    u.login_id                         AS loginId, "
				+ "    md.domain_name                     AS domainName, "
				+ "    u.user_type                        AS userType, "
				+ "    mc.category_code                   AS categoryCode, "
				+ "    u.msisdn                           AS msisdn, "
				+ "    mc.category_name                   AS categoryName, "
				+ "    TO_CHAR(u.created_on,'YYYY-MM-DD') AS createdOn, "
				+ "    u.status                           AS status, " + "    u.created_on " + "  FROM users u, "
				+ "    mtx_domains md, " + "    mtx_categories mc " + "  WHERE u.category_code=mc.category_code "
				+ "  AND mc.domain_code   =md.domain_code " + "  AND u.status        <>'N' "
				+ "  AND u.user_Type      = 'CHANNEL' " + "  AND u.category_code  = '"
				+ userListingRequest.getChannelCategoryCode() + "'" + "  AND mc.domain_code   = '"
				+ userListingRequest.getChannelDomainCode() + "'" + "  UNION ALL "
				+ "  SELECT md.domain_code                AS domainCode, "
				+ "    u.login_id                         AS loginId, "
				+ "    md.domain_name                     AS domainName, "
				+ "    u.user_type                        AS userType, "
				+ "    mc.category_code                   AS categoryCode, "
				+ "    u.msisdn                           AS msisdn, "
				+ "    mc.category_name                   AS categoryName, "
				+ "    TO_CHAR(u.created_on,'YYYY-MM-DD') AS createdOn, "
				+ "    u.status                           AS status, " + "    u.created_on " + "  FROM users u, "
				+ "    mtx_domains md, " + "    mtx_categories mc " + "  WHERE u.category_code=mc.category_code "
				+ "  AND mc.domain_code   =md.domain_code " + "  AND u.status        <>'N' "
				+ "  AND u.category_code IN ( 'NWADM','SUADM' ) " + "  )";
	}
}