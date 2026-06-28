package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String trackName;
    private String singerName;
    private String filePath;
    private String coverArt;
    private int lengthInSec;

    public Song(){}

    public Song(String trackName, String singerName, String filePath, String coverArt, int lengthInSec) {
        this.trackName = trackName;
        this.singerName = singerName;
        this.filePath = filePath;
        this.coverArt = coverArt;
        this.lengthInSec = lengthInSec;
    }

    // --- Getters (Required so Spring Boot can read the data and send it as JSON) ---
    public Long getId() { return id; }
    public String getTrackName() { return trackName; }
    public String getSingerName() { return singerName; }
    public String getFilePath() { return filePath; }
    public String getCoverArt() { return coverArt; }
    public int getLengthInSec() { return lengthInSec; }

    // --- Setters ---
    public void setId(Long id) { this.id = id; }
    public void setTrackName(String trackName) { this.trackName = trackName; }
    public void setSingerName(String singerName) { this.singerName = singerName; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    public void setCoverArt(String coverArt) { this.coverArt = coverArt; }
    public void setLengthInSec(int lengthInSec) { this.lengthInSec = lengthInSec; }
}
