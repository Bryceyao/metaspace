package com.bryce.metaspace.job;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bryce.metaspace.infrastructure.MetaSpaceCacheHandle;

@Component
public class CachesRefreshJob {
    @Resource
    public MetaSpaceCacheHandle mapCacheHandle;
    
    @Scheduled(cron = "${job.CachesRefreshJob.schedule}")
    public void clean() {
        mapCacheHandle.refreshTypePropertyContent();

    }
}
