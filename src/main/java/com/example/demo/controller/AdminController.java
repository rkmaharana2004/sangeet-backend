package com.example.demo.controller;

import com.example.demo.service.MusicSyncService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final MusicSyncService syncService;

    public AdminController(MusicSyncService syncService) {
        this.syncService = syncService;
    }

    // You hit this endpoint to trigger the folder scan
    @PostMapping("/sync")
    public String syncMusicFolder() {
        return syncService.syncMusic();
    }
}