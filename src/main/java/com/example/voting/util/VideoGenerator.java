package com.example.voting.util;

import com.example.voting.model.Video;
import com.example.voting.util.JPAUtil;
import jakarta.persistence.EntityManager;
import com.github.javafaker.Faker;

import java.util.List;
import java.util.Random;

public class VideoGenerator {

    private static final List<String> YOUTUBE_IDS = List.of(
        "QiJ_JYQLS2s",
        "I2TU72mLl_E",
        "S1OBAiLx6fI",
        "UAcTf9NhotE",
        "olt1ERyqZCc",
        "25BGvZ-SSro",
        "gSJeHDlhYls",
        "rTKpYJ80OVQ",
        "ymsZQy638iM",
        "qNV1Zf70gXI"
    );

    public static void main(String[] args) {
        EntityManager em = JPAUtil.getEntityManager();
        Faker faker = new Faker();
        Random random = new Random();

        try {
            em.getTransaction().begin();

            for (int i = 1; i <= 100; i++) {
                String title = faker.book().title() + " " + i;
                String description = faker.lorem().sentence();

                int group = (i - 1) / 10;
                String youtubeId = YOUTUBE_IDS.get(group);
                String thumbnailUrl = "https://img.youtube.com/vi/" + youtubeId + "/0.jpg";

                Video video = new Video(title, description, youtubeId, thumbnailUrl);
                em.persist(video);
            }

            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
            JPAUtil.shutdown();
        }
    }
}