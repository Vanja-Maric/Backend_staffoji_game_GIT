package com.example.backend_staffoji_game.service;

import com.example.backend_staffoji_game.dto.NotificationDto;
import com.example.backend_staffoji_game.dto.UserDto;
import com.example.backend_staffoji_game.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class NotificationServiceTest_IntegrationTest {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private NotificationRepository notificationRepository;

    private NotificationDto notificationObject;

    LocalDateTime now = LocalDateTime.now();


    @BeforeEach
    public void setUp() {
        cleanUpDatabase();
        now = now.truncatedTo(ChronoUnit.MICROS);
    }

    private void cleanUpDatabase() {
        notificationRepository.deleteAll();
    }

    @Test
    void createNotification_positiveTest() {
        // Check if database is empty
        assertTrue(databaseIsEmpty());

        // Create a notification
        notificationObject = new NotificationDto("test", "test", "test", now, true);

        // Save user
        notificationService.createNotification(notificationObject);

        // Check if user is saved
        var result = notificationRepository.findByTitle(notificationObject.getTitle());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(result.get().getTitle(), notificationObject.getTitle());
        assertEquals(result.get().getMessage(), notificationObject.getMessage());
        assertEquals(result.get().getNotificationTarget(), notificationObject.getNotificationTarget());
        assertEquals(result.get().getSendTime(), notificationObject.getSendTime());
        assertEquals(result.get().isSendNow(), notificationObject.isSendNow());
    }

    private boolean databaseIsEmpty() {
        return notificationRepository.findAll().isEmpty();
    }
}

