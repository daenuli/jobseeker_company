package com.jobseeker.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Utility class for monitoring performance of database operations
 */
@Component
public class PerformanceMonitor {
    
    private static final Logger logger = LoggerFactory.getLogger(PerformanceMonitor.class);
    
    /**
     * Monitor execution time of a method
     * @param methodName the name of the method being monitored
     * @param startTime the start time in milliseconds
     * @return the execution time in milliseconds
     */
    public long logExecutionTime(String methodName, long startTime) {
        long executionTime = System.currentTimeMillis() - startTime;
        
        if (executionTime > 1000) {
            logger.warn("SLOW QUERY: {} took {}ms", methodName, executionTime);
        } else if (executionTime > 500) {
            logger.info("MODERATE QUERY: {} took {}ms", methodName, executionTime);
        } else {
            logger.debug("FAST QUERY: {} took {}ms", methodName, executionTime);
        }
        
        return executionTime;
    }
    
    /**
     * Monitor cache hit/miss
     * @param cacheName the name of the cache
     * @param key the cache key
     * @param hit whether it was a cache hit or miss
     */
    public void logCacheAccess(String cacheName, String key, boolean hit) {
        if (hit) {
            logger.debug("CACHE HIT: {} - key: {}", cacheName, key);
        } else {
            logger.debug("CACHE MISS: {} - key: {}", cacheName, key);
        }
    }
    
    /**
     * Monitor database query performance
     * @param queryType the type of query (e.g., "ranking", "findAll", "findById")
     * @param recordCount the number of records processed
     * @param executionTime the execution time in milliseconds
     */
    public void logDatabaseQuery(String queryType, int recordCount, long executionTime) {
        double recordsPerSecond = recordCount > 0 ? (recordCount * 1000.0) / executionTime : 0;
        
        logger.info("DB QUERY: {} - {} records in {}ms ({} records/sec)", 
                   queryType, recordCount, executionTime, String.format("%.2f", recordsPerSecond));
        
        // Alert for performance issues
        if (executionTime > 2000 && recordCount > 1000) {
            logger.warn("PERFORMANCE ALERT: Large dataset query took {}ms for {} records", 
                       executionTime, recordCount);
        }
    }
    
    /**
     * Create a performance timer
     * @return current time in milliseconds
     */
    public long startTimer() {
        return System.currentTimeMillis();
    }
}