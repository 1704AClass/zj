package com.ningmeng.filesystem.dao;

import com.ningmeng.framework.domain.filesystem.FileSystem;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by 周周 on 2020/2/21.
 */
public interface FileSystemApplication extends MongoRepository<FileSystem,String>{

}
