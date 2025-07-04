package com.jobseeker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

/**
 * MongoDB configuration to ensure indexes are created automatically
 */
@Configuration
public class MongoIndexConfig extends AbstractMongoClientConfiguration {
    
    @Override
    protected String getDatabaseName() {
        return "jobseeker_company";
    }
    
    @Override
    protected boolean autoIndexCreation() {
        return true;
    }
}