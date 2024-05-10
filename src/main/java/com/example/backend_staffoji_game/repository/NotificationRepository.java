package com.example.backend_staffoji_game.repository;

import com.example.backend_staffoji_game.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{
}
