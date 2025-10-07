package com.example.voting.servlet;

import com.example.voting.model.Video;
import com.example.voting.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/api/videos/top")
public class TopVideosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        EntityManager em = JPAUtil.getEntityManager();

        List<Video> videos = em.createQuery("SELECT v FROM Video v", Video.class).getResultList();


        em.close();

        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json");
        mapper.writeValue(resp.getOutputStream(), videos);
    }
}