package com.example.testtaskbv.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@Service
@AllArgsConstructor
public class CacheVisualizationService {

    private final CacheManager cacheManager;

    public Map<String, Object> getCacheContents(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            throw new IllegalArgumentException("Cache not found: " + cacheName);
        }

        Map<String, Object> cacheContents = new HashMap<>();
        log.info("Retrieving contents of cache: {}", cacheName);
        cacheContents.put("cacheName", cacheName);
        cacheContents.put("contents", getCacheEntries(cache));
        return cacheContents;
    }

    public List<String> getAvailableCaches() {
        return new ArrayList<>(cacheManager.getCacheNames());
    }

    private Map<Object, Object> getCacheEntries(Cache cache) {
        Map<Object, Object> entries = new HashMap<>();
        if (cache.getNativeCache() instanceof ConcurrentMap<?, ?>) {
            ConcurrentMap<?, ?> nativeCache = (ConcurrentMap<?, ?>) cache.getNativeCache();
            nativeCache.forEach((key, value) -> entries.put(key, value));
        } else {
            log.warn("Cache provider does not support direct entry retrieval: {}", cache.getClass());
        }
        return entries;
    }
}