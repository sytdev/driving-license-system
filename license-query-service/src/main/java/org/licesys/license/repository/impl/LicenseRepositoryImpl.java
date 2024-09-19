package org.licesys.license.repository.impl;

import ch.qos.logback.core.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.licesys.common.documents.License;
import org.licesys.license.repository.LicenseRepository;
import org.licesys.license.repository.LicenseTemplateRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class LicenseRepositoryImpl implements LicenseTemplateRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<License> findLicensesByFilters(String status, String type, String licenseNumber) {

        Query query = new Query();

        if (StringUtils.hasText(status)) {
            query.addCriteria(Criteria.where("status").is(status));
        }

        if (StringUtils.hasText(type)) {
            query.addCriteria(Criteria.where("type").is(type));
        }

        if (StringUtils.hasText(licenseNumber)) {
            query.addCriteria(Criteria.where("licenseNumber").is(licenseNumber));
        }

        return mongoTemplate.find(query, License.class);
    }
}
