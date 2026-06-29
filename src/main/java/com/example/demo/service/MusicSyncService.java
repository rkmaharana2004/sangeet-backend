package com.example.demo.service;

import com.example.demo.model.Song;
import com.example.demo.repository.SongRepository;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class MusicSyncService {

    private final SongRepository repository;

    // This matches the volume path we set in docker-compose.yml
    private final String MUSIC_DIR = "/app/music_files";

    public MusicSyncService(SongRepository repository) {
        this.repository = repository;
    }

    public String syncMusic() {
        File rootDir = new File(MUSIC_DIR);
        if (!rootDir.exists() || !rootDir.isDirectory()) {
            return "Error: Could not find music directory at " + MUSIC_DIR;
        }

        int addedCount = 0;

        // 1. Loop through artist folders (e.g., "Arijit Singh")
        File[] artistFolders = rootDir.listFiles();
        if (artistFolders == null) return "No artist folders found.";

        for (File artistFolder : artistFolders) {
            if (artistFolder.isDirectory()) {
                String artistName = artistFolder.getName();

                // 2. Loop through songs inside the artist folder
                File[] songs = artistFolder.listFiles();
                if (songs == null) continue;

                for (File songFile : songs) {
                    if (songFile.isFile() && songFile.getName().endsWith(".mp3")) {
                        String originalName = songFile.getName();

                        // 3. CLEANUP: Remove emojis and special characters from the filename
                        // Keeps letters, numbers, dots, and hyphens. Replaces everything else with underscores.
                        String cleanFileName = originalName.replaceAll("[^a-zA-Z0-9.-]", "_").replaceAll("_+", "_");
                        File cleanFile = new File(artistFolder, cleanFileName);

                        // Actually rename the file on the Ubuntu server!
                        if (!originalName.equals(cleanFileName)) {
                            songFile.renameTo(cleanFile);
                        }

                        // 4. Create the URL path that Android will use
                        String filePath = "/audio/" + artistName + "/" + cleanFileName;

                        // 5. Save to database (ONLY if it doesn't already exist)
                        if (!repository.existsByFilePath(filePath)) {
                            // Make a nice readable title for the Android UI
                            String title = cleanFileName.replace(".mp3", "").replace("_", " ");

                            Song newSong = new Song();
                            newSong.setTrackName(title);
                            newSong.setSingerName(artistName);
                            newSong.setFilePath(filePath);
                            newSong.setCoverArt("/art/default.jpg");
                            newSong.setLengthInSec(200);

                            repository.save(newSong);
                            addedCount++;
                        }
                    }
                }
            }
        }
        return "Sync Complete! Successfully added " + addedCount + " new songs to the database.";
    }
}