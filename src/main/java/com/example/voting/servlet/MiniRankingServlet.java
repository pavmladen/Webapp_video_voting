package com.example.voting.servlet;

import com.example.voting.model.Video;
import com.example.voting.util.JPAUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.persistence.*;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/home")
public class MiniRankingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            List<Video> videos = em.createQuery("SELECT v FROM Video v", Video.class).getResultList();

            double z = 1.96;
            videos.forEach(v -> {
                int n = v.getTotalVotes();
                if (n == 0) {
                    v.setRankScore(0);
                } else {
                    double phat = v.getPositiveVotes() / (double) n;
                    double score = (phat + z * z / (2 * n) - z * Math.sqrt((phat * (1 - phat) + z * z / (4 * n)) / n)) /
                                   (1 + z * z / n);
                    v.setRankScore(score);
                }
            });

            List<Video> top5 = videos.stream()
                .sorted(Comparator.comparingDouble(Video::getRankScore).reversed())
                .limit(5)
                .collect(Collectors.toList());

            req.setAttribute("topVideos", top5);
            req.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(req, resp);
        } finally {
            em.close();
        }
    }
}