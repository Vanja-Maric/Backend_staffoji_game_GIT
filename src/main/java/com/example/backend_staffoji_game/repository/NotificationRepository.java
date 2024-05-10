package com.example.backend_staffoji_game.repository;

import com.example.backend_staffoji_game.model.Notification;
import com.example.backend_staffoji_game.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{

    Optional<Notification> findByTitle(String notificationTitle);
}
