package com.example.voting.dao;

import com.example.voting.model.Video;
import com.example.voting.util.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class VideoDao {

    public List<Video> getTwoRandomVideos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            List<Video> allVideos = em.createQuery("SELECT v FROM Video v", Video.class).getResultList();
            Collections.shuffle(allVideos);

            List<Video> result = new ArrayList<>();
            for (int i = 0; i < allVideos.size(); i++) {
                for (int j = i + 1; j < allVideos.size(); j++) {
                    if (!allVideos.get(i).getYoutubeId().equals(allVideos.get(j).getYoutubeId())) {
                        result.add(allVideos.get(i));
                        result.add(allVideos.get(j));
                        return result;
                    }
                }
            }
            return allVideos.subList(0, Math.min(2, allVideos.size()));
        } finally {
            em.close();
        }
    }


    public void registerVote(int videoId, boolean isPositive) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Video video = em.find(Video.class, videoId);
            if (video != null) {
                video.registerVote(isPositive);
                em.merge(video);
            }
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            em.close();
        }
    }

    public List<Video> getTop5Videos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            List<Video> allVideos = em.createQuery("SELECT v FROM Video v", Video.class).getResultList();
            allVideos.sort(Comparator.comparingDouble(Video::getVotePercentage).reversed());
            return allVideos.subList(0, Math.min(5, allVideos.size()));
        } finally {
            em.close();
        }
    }

    public List<Video> findAllSortedByRank() {
    EntityManager em = JPAUtil.getEntityManager();
    try {
        List<Video> videos = em.createQuery("SELECT v FROM Video v", Video.class).getResultList();

        double z = 1.96;
        for (Video v : videos) {
            int n = v.getTotalVotes();
            if (n == 0) {
                v.setRankScore(0);
            } else {
                double phat = v.getPositiveVotes() / (double) n;
                double score = (phat + z * z / (2 * n) - z * Math.sqrt((phat * (1 - phat) + z * z / (4 * n)) / n)) /
                               (1 + z * z / n);
                v.setRankScore(score);
            }
        }
        videos.sort(Comparator.comparingDouble(Video::getRankScore).reversed());
        return videos;
    } finally {
        em.close();
    }
}


    public Video findById(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Video.class, id);
        } finally {
            em.close();
        }
    }

    public void create(String title, String description, String youtubeId, String thumbnailUrl) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Video video = new Video(title, description, youtubeId, thumbnailUrl);
            em.persist(video);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void update(int id, String title, String description, String youtubeId, String thumbnailUrl) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Video video = em.find(Video.class, id);
            if (video != null) {
                video.setTitle(title);
                video.setDescription(description);
                video.setYoutubeId(youtubeId);
                video.setThumbnailUrl(thumbnailUrl);
                em.merge(video);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void deleteById(int id) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
        em.getTransaction().begin();

        Video video = em.find(Video.class, id);
        if (video != null) {
            em.createQuery("DELETE FROM Vote v WHERE v.video.id = :vid")
              .setParameter("vid", id)
              .executeUpdate();

            em.remove(video);
        }

        em.getTransaction().commit();
    } catch (Exception e) {
        if (em.getTransaction().isActive()) em.getTransaction().rollback();
        throw e;
    } finally {
        em.close();
    }
}


}