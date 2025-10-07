package com.example.voting.servlet;

import com.example.voting.dao.VideoDao;
import com.example.voting.model.Video;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/videos/random")
public class RandomVideoServlet extends HttpServlet {

    private final VideoDao videoDao = new VideoDao();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Video> videos = videoDao.getTwoRandomVideos();

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String json = gson.toJson(videos);
        resp.getWriter().write(json);
    }
}