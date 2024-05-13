package com.example.backend_staffoji_game.service;

import com.example.backend_staffoji_game.dto.NotificationDto;
import com.example.backend_staffoji_game.exception.UserAlreadyExistsException;
import com.example.backend_staffoji_game.exception.UserDoesNotExistsException;
import com.example.backend_staffoji_game.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void createNotification_negativeTest() {
        // Check if database is empty
        assertTrue(databaseIsEmpty());

        // Create a notification
        notificationObject = new NotificationDto("testNegative", "testNegative", "testNegative", now, true);

        // Save user
        notificationService.createNotification(notificationObject);

        // Try to create another notification with the same title
        NotificationDto duplicateNotification = new NotificationDto("testNegative", "testNegative", "testNegative", now, true);

        // Assert that a UserAlreadyExistsException is thrown
        assertThrows(UserDoesNotExistsException.class, () -> notificationService.createNotification(duplicateNotification));
    }

    @Test
    void createNotification_checkingToAvoidRaceCondition() {
        // Check if database is empty
        assertTrue(databaseIsEmpty());


        for (int i = 0; i < 100; i++) {
            // Create a notification
            notificationObject = new NotificationDto("test" + i, "test", "test", now, true);
            notificationService.createNotification(notificationObject);

        }

        // Check if user is saved
        var result = notificationRepository.findAll();

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(result.size(), 100);
    }

    @Test
    void checkIfIsSendNowFalse_NotSendPassTime_NegativeTest() {
        // Check if database is empty
        assertTrue(databaseIsEmpty());

        // Create a notification
        notificationObject = new NotificationDto("test", "test", "test", now.minusMinutes(1), false);

        // Assert that a UserAlreadyExistsException is thrown
        assertEquals("Send time must be in the future", assertThrows(RuntimeException.class, () -> notificationService.createNotification(notificationObject)).getMessage());
    }

    @Test
    void checkIfIsSendNowFalse_SendingInFuture_PositiveTest() {
        // Check if database is empty
        assertTrue(databaseIsEmpty());

        // Create a notification
        notificationObject = new NotificationDto("test", "test", "test", now.plusMinutes(1), false);

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

    @Test
    void createNotification_NullValues_EdgeCaseTest() {
        // Create a notification with null values
        notificationObject = new NotificationDto(null, null, null, null, false);

        // Assert that a RuntimeException is thrown
        assertThrows(RuntimeException.class, () -> notificationService.createNotification(notificationObject));
    }

    private boolean databaseIsEmpty() {
        return notificationRepository.findAll().isEmpty();
    }
}

