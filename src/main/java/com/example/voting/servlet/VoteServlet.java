package com.example.voting.servlet;

import com.example.voting.model.Video;
import com.example.voting.model.Vote;
import com.example.voting.model.VoteRequest;
import com.example.voting.util.JPAUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/vote")
public class VoteServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        VoteRequest voteReq = mapper.readValue(req.getInputStream(), VoteRequest.class);

        long videoId = voteReq.getVideoId();
        boolean positive = voteReq.isPositive();
        long otherVideoId = voteReq.getOtherVideoId();

        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            Video voted = em.find(Video.class, videoId);
            Video other = em.find(Video.class, otherVideoId);

            if (voted == null || other == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            voted.setTotalVotes(voted.getTotalVotes() + 1);
            if (positive) {
                voted.setPositiveVotes(voted.getPositiveVotes() + 1);
            }

            other.setTotalVotes(other.getTotalVotes() + 1);

            em.merge(voted);
            em.merge(other);


            Vote vote = new Vote();
            vote.setVideo(voted);
            vote.setPositive(positive);
            em.persist(vote);

            em.getTransaction().commit();
            resp.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}