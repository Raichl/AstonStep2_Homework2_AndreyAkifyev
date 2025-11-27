package app.services;

import app.config.PropertiesTextManager;
import app.model.dto.NotificationDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTests {

    @Mock
    private EmailService emailService;

    @InjectMocks
    private NotificationService notificationService;

    private MockedStatic<PropertiesTextManager> propertiesTextManagerMock;

    private NotificationDto createNotificationDto;
    private NotificationDto deleteNotificationDto;
    private NotificationDto unknownNotificationDto;

    @BeforeEach
    public void setup() {
        propertiesTextManagerMock = mockStatic(PropertiesTextManager.class);


        createNotificationDto = new NotificationDto();
        createNotificationDto.setEmail("test@test.com");
        createNotificationDto.setNotificationType("create");

        deleteNotificationDto = new NotificationDto();
        deleteNotificationDto.setEmail("test@test.com");
        deleteNotificationDto.setNotificationType("remove");

        unknownNotificationDto = new NotificationDto();
        unknownNotificationDto.setEmail("test@test.com");
        unknownNotificationDto.setNotificationType("sms What i didnt want see");
    }

    @AfterEach
    void tearDown() {
        if (propertiesTextManagerMock != null) {
            propertiesTextManagerMock.close();
        }
    }

    @Test
    void sendNotification_WithDeleteType_ShouldSendDeleteMessage() {
        String expectedMessage = "Здравствуйте! Ваш аккаунт был удалён.";
        propertiesTextManagerMock.when(() -> PropertiesTextManager.get("notification.delete"))
                .thenReturn(expectedMessage);


        notificationService.sendNotification(deleteNotificationDto);

        propertiesTextManagerMock.verify(() -> PropertiesTextManager.get("notification.delete"));
        verify(emailService, times(1))
                .sendEmail("test@test.com", "no reply", expectedMessage);
    }

    @Test
    void sendNotification_WithCreateType_ShouldSendCreateMessage() {
        String expectedMessage = "Здравствуйте! Ваш аккаунт на сайте ваш сайт был успешно создан.";
        propertiesTextManagerMock.when(() -> PropertiesTextManager.get("notification.create"))
                .thenReturn(expectedMessage);

        notificationService.sendNotification(createNotificationDto);

        propertiesTextManagerMock.verify(() -> PropertiesTextManager.get("notification.create"));
        verify(emailService, times(1))
                .sendEmail("test@test.com", "no reply", expectedMessage);
    }

    @Test
    void sendNotification_WithUnknownType_ShouldThrowException() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            notificationService.sendNotification(unknownNotificationDto);
        });

        propertiesTextManagerMock.verifyNoInteractions();
        verify(emailService, never()).sendEmail(anyString(), anyString(), anyString());
    }
}
