package com.example.garderieapi.Repository;

import com.example.garderieapi.Repository.UserRepositoryCustom;
import com.example.garderieapi.entity.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRoleRepositoryImpl implements UserRepositoryCustom {


        @PersistenceContext
        private EntityManager entityManager;





        @Override
        public List<Role> findRolesByUsername(String nom) {
            return entityManager.createQuery(
                            "SELECT u.roles FROM User u WHERE u.nom = :nom", Role.class)
                    .setParameter("nom", nom)
                    .getResultList();
        }
    }










