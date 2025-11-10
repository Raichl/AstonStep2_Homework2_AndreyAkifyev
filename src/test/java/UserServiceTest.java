import dao.UserDao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import user.User;
import user.UserService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserDao userDao;

    private UserService userService;
    private User testUser;

    @BeforeEach
    void setUp(){
        userService = new UserService(userDao);
        testUser = new User("TestName","TestMail@test.com",18);
        testUser.setId(1);
    }

    @Test
    @DisplayName("Должен находить пользователя по id")
    void shouldFindUserById(){
        when(userDao.findById(1)).thenReturn(testUser);
        User result = userService.findUser(1);

        assertNotNull(result);
        assertEquals(1,result.getId());
        assertEquals("TestName",result.getName());
        verify(userDao,times(1)).findById(1);
    }

    @Test
    @DisplayName("Должен сохранять пользователей")
    void shouldSaveUser(){
        doNothing().when(userDao).save(testUser);

        userService.saveUser(testUser);

        verify(userDao,times(1)).save(testUser);
    }

    @Test
    @DisplayName("Должен обновлять пользователей пользователей")
    void shouldUpdateUser(){
        doNothing().when(userDao).update(testUser);

        userService.updateUser(testUser);

        verify(userDao,times(1)).update(testUser);
    }

    @Test
    @DisplayName("Должен удалять пользователей пользователей")
    void shouldDeleteUser(){
        doNothing().when(userDao).delete(testUser);

        userService.deleteUser(testUser);

        verify(userDao,times(1)).delete(testUser);
    }

    @Test
    @DisplayName("Должен получать всех пользователей пользователей")
    void shouldFindAllUsers(){
        List<User> users = Arrays.asList(
                new User("TestName","TestMail@test.com",25),
                new User("TestName2","TestMail@test.com",21)
        );
        when(userDao.findAll()).thenReturn(users);
        List<User> result = userService.findAllUsers();

        assertNotNull(result);
        assertEquals(2,result.size());
        verify(userDao,times(1)).findAll();
    }

    @Test
    @DisplayName("Должен возвращать null усли пользователь не найден")
    void shouldReturnNullWhenUserNotFound(){
        when(userDao.findById(999)).thenReturn(null);
        User result = userService.findUser(999);
        assertNull(result);
        verify(userDao,times(1)).findById(999);
    }
}
