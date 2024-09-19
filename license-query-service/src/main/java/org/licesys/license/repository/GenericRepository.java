package org.licesys.license.repository;

import org.licesys.common.documents.License;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository<T, ID> extends MongoRepository<T, ID> {
}
