package com.example.testtaskbv.controller;

import com.example.testtaskbv.service.CacheVisualizationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cache")
@AllArgsConstructor
public class CacheVisualizationController {

    private final CacheVisualizationService cacheVisualizationService;

    @GetMapping("/available")
    public ResponseEntity<List<String>> getAvailableCaches() {
        List<String> caches = cacheVisualizationService.getAvailableCaches();
        return ResponseEntity.ok(caches);
    }

    @GetMapping("/{cacheName}")
    public ResponseEntity<Map<String, Object>> getCacheContents(@PathVariable String cacheName) {
        Map<String, Object> cacheContents = cacheVisualizationService.getCacheContents(cacheName);
        return ResponseEntity.ok(cacheContents);
    }
}