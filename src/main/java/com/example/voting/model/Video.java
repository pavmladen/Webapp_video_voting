package com.example.voting.model;

import jakarta.persistence.*;

@Entity
@Table(name = "videos")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "youtube_id", nullable = false)
    private String youtubeId;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "positive_votes")
    private int positiveVotes;

    @Column(name = "total_votes")
    private int totalVotes;

    @Transient
    private double rankScore;

    public Video() {}

    public Video(String title, String description, String youtubeId, String thumbnailUrl) {
        this.title = title;
        this.description = description;
        this.youtubeId = youtubeId;
        this.thumbnailUrl = thumbnailUrl;
        this.positiveVotes = 0;
        this.totalVotes = 0;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYoutubeId() {
        return youtubeId;
    }

    public void setYoutubeId(String youtubeId) {
        this.youtubeId = youtubeId;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getPositiveVotes() {
        return positiveVotes;
    }

    public void setPositiveVotes(int positiveVotes) {
        this.positiveVotes = positiveVotes;
    }

    public int getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(int totalVotes) {
        this.totalVotes = totalVotes;
    }

    public void registerVote(boolean isPositive) {
        this.totalVotes++;
        if (isPositive) {
            this.positiveVotes++;
        }
    }

    public double getVotePercentage() {
        return totalVotes == 0 ? 0.0 : ((double) positiveVotes / totalVotes) * 100;
    }

    public double getRankScore() {
        return rankScore;
    }

    public void setRankScore(double rankScore) {
        this.rankScore = rankScore;
    }
}