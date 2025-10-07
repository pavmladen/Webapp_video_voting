package com.example.voting.dao;

import com.example.voting.model.Role;
import com.example.voting.util.JPAUtil;

import jakarta.persistence.EntityManager;
import java.util.List;

public class RoleDao {

    public List<Role> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT r FROM Role r", Role.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Role findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Role.class, id);
        } finally {
            em.close();
        }
    }

    public Role findByName(String name) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class)
                     .setParameter("name", name)
                     .getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public void create(Role role) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(role);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}