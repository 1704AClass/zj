package com.ningmeng.manage_cms.dao;

import com.ningmeng.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by 周周 on 2020/2/27.
 */
public interface CmsSiteRepository extends MongoRepository<CmsSite,String>{
}
