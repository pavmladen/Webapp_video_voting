package com.example.voting.model;

public class VoteRequest {
    private long videoId;
    private boolean positive;
    private long otherVideoId;

    public long getVideoId() {
        return videoId;
    }

    public void setVideoId(long videoId) {
        this.videoId = videoId;
    }

    public boolean isPositive() {
        return positive;
    }

    public void setPositive(boolean positive) {
        this.positive = positive;
    }

    public long getOtherVideoId() {
        return otherVideoId;
    }

    public void setOtherVideoId(long otherVideoId) {
        this.otherVideoId = otherVideoId;
    }
}

