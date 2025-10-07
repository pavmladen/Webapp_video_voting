package com.example.voting.servlet;

import com.example.voting.dao.VideoDao;
import com.example.voting.model.Video;
import com.example.voting.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.File;
import java.nio.file.Paths;


import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@WebServlet("/admin/videos")
@MultipartConfig
public class AdminVideoServlet extends HttpServlet {

    private final VideoDao videoDao = new VideoDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        String action = req.getParameter("action");
        String idParam = req.getParameter("id");

        if ("edit".equals(action) && idParam != null) {
            int id = Integer.parseInt(idParam);
            Video video = videoDao.findById(id);
            req.setAttribute("video", video);
            req.getRequestDispatcher("/WEB-INF/views/video_form.jsp").forward(req, resp);
            return;
        }

        if ("delete".equals(action) && idParam != null) {
            int id = Integer.parseInt(idParam);
            videoDao.deleteById(id);
            resp.sendRedirect(req.getContextPath() + "/admin/videos");
            return;
        }

        if ("add".equals(action)) {
            req.setAttribute("video", null);
            req.getRequestDispatcher("/WEB-INF/views/video_form.jsp").forward(req, resp);
            return;
        }

        List<Video> videos = videoDao.findAllSortedByRank();
        req.setAttribute("videos", videos);
        req.getRequestDispatcher("/WEB-INF/views/videos.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        int id = req.getParameter("id") != null ? Integer.parseInt(req.getParameter("id")) : 0;
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String youtubeId = req.getParameter("youtubeId");

        String thumbnailUrl = "https://img.youtube.com/vi/" + youtubeId + "/0.jpg";

        Part filePart = req.getPart("thumbnail");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

            String uploadsDir = getServletContext().getRealPath("/uploads/");
            File uploadsFolder = new File(uploadsDir);
            if (!uploadsFolder.exists()) uploadsFolder.mkdirs();

            File uploadedFile = new File(uploadsFolder, fileName);
            if (!uploadedFile.exists()) {
                filePart.write(uploadedFile.getAbsolutePath());
            }

            thumbnailUrl = "uploads/" + fileName;
        } else {
            thumbnailUrl = "https://img.youtube.com/vi/" + youtubeId + "/0.jpg";
        }

        if (id > 0) {
            videoDao.update(id, title, description, youtubeId, thumbnailUrl);
        } else {
            videoDao.create(title, description, youtubeId, thumbnailUrl);
        }

        resp.sendRedirect(req.getContextPath() + "/admin/videos");
    }

}