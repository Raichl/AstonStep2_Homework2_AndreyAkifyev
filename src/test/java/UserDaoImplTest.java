import dao.UserDao;
import dao.UserDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import user.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoImplTest extends BaseTestcontainersTest {

    private final String TEST_NAME = "TestName";
    private final String TEST_EMAIL = "TestMail@Ex.com";
    private final int TEST_AGE = 18;

    private UserDao userDao;
    private User testUser;

    @BeforeEach
    void setUp() {
        userDao = new UserDaoImpl();
        testUser = new User(TEST_NAME, TEST_EMAIL, TEST_AGE);
    }

    @Test
    void shouldSaveCorrect() {
        userDao.save(testUser);

        assertNotEquals(0, testUser.getId());
        User savedUser = userDao.findById(testUser.getId());
        assertNotNull(savedUser);
        assertEquals(TEST_AGE, savedUser.getAge());
        assertEquals(TEST_NAME, savedUser.getName());
        assertEquals(TEST_EMAIL, savedUser.getEmail());
    }

    @Test
    void shouldCorrectDelete() {
        userDao.save(testUser);

        userDao.delete(testUser);

        assertNull(userDao.findById(testUser.getId()));
    }

    @Test
    void shouldCorrectFindIfExist() {
        userDao.save(testUser);

        User foundUser = userDao.findById(testUser.getId());

        assertNotNull(foundUser);
        assertEquals(testUser.getId(), foundUser.getId());
        assertEquals(testUser, foundUser);
    }

    @Test
    void shouldCorrectFindIfNotExist() {
        assertNull(userDao.findById(9999));
    }

    @Test
    void shouldCorrectFindAll() {
        User user1 = new User("User1", "User1@mail.com", 24);
        User user2 = new User("User2", "User2@mail.com", 23);

        userDao.save(user1);
        userDao.save(user2);
        List<User> users = userDao.findAll();

        assertNotNull(users);
        assertTrue(users.size() >= 2);
        assertTrue(users.stream().anyMatch(user -> user1.getName().equals(user.getName())));
        assertTrue(users.stream().anyMatch(user -> user2.getName().equals(user.getName())));
    }
}
