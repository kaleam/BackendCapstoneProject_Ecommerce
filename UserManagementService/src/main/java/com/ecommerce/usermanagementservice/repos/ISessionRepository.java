package com.ecommerce.usermanagementservice.repos;

import com.ecommerce.usermanagementservice.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findByTokenAndUser_Id(String token, Long userId);
}
