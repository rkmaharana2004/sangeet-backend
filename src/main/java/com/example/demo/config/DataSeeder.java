package com.example.demo.config;

import com.example.demo.model.Song;
import com.example.demo.repository.SongRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    // CommandLineRunner is a special tool that runs exactly once when the app starts
    @Bean
    CommandLineRunner initDatabase(SongRepository repository) {
        return args -> {
            // Only add these songs if the database is completely empty
            if (repository.count() == 0) {

                // Using the constructor we made in the Song Model
                repository.save(new Song("Nocturne in Black", "The Paper Ensemble", "/audio/nocturne.mp3", "/art/nocturne.jpg", 185));
                repository.save(new Song("Ink & Groove", "The Monochromes", "/audio/ink.mp3", "/art/ink.jpg", 210));
                repository.save(new Song("Rough Draft", "Silent Notes", "/audio/rough.mp3", "/art/rough.jpg", 195));

                System.out.println("✅ Database successfully seeded with 3 dummy songs!");
            } else {
                System.out.println("ℹ️ Database already has data. Skipping seed.");
            }
        };
    }
}