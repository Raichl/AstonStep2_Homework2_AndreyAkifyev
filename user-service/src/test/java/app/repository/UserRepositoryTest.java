package app.repository;

import app.model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;



import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


public class UserRepositoryTest extends TestcontainerBaseTest{
    private final String TEST_NAME = "Test";
    private final String TEST_EMAIL = "test@test.com";
    private final int TEST_AGE = 25;


    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    void saveAndFindUser() {
        User user = new User(TEST_NAME, TEST_EMAIL, TEST_AGE);

        User saved = userRepository.save(user);
        Optional<User> found = userRepository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals(TEST_NAME, found.get().getName());
        assertEquals(TEST_AGE, found.get().getAge());
        assertEquals(TEST_EMAIL, found.get().getEmail());
        assertNotNull(found.get().getCreatedAt());
    }

    @Test
    void findAllUser() {
        userRepository.save(new User(TEST_NAME, TEST_EMAIL, TEST_AGE));
        userRepository.save(new User(TEST_NAME + 1, TEST_EMAIL + 1, TEST_AGE + 1));

        List<User> user = userRepository.findAll();

        assertTrue(user.size() >= 2);
    }

    @Test
    void updateUser_Success() {
        User savedUser = userRepository.save(new User(TEST_NAME, TEST_EMAIL, TEST_AGE));

        savedUser.setName("newName");
        savedUser.setAge(11);
        savedUser.setEmail("newEmail@test.com");
        User updateUser = userRepository.save(savedUser);

        assertEquals("newName", updateUser.getName());
        assertEquals(11, updateUser.getAge());
        assertEquals("newEmail@test.com", updateUser.getEmail());
        assertEquals(savedUser.getId(), updateUser.getId());
        assertEquals(savedUser.getCreatedAt(), updateUser.getCreatedAt());
    }

    @Test
    void deleteUserById_Success() {
        User savedUser = userRepository.save(new User(TEST_NAME, TEST_EMAIL, TEST_AGE));
        Long userId = savedUser.getId();

        assertTrue(userRepository.findById(userId).isPresent());

        userRepository.deleteById(userId);

        Optional<User> foundUser = userRepository.findById(userId);
        assertFalse(foundUser.isPresent());
    }

    @Test
    void deleteUserByEntity_Success() {
        User savedUser = userRepository.save(new User(TEST_NAME, TEST_EMAIL, TEST_AGE));
        Long userId = savedUser.getId();

        assertTrue(userRepository.findById(userId).isPresent());

        userRepository.delete(savedUser);

        Optional<User> foundUser = userRepository.findById(userId);
        assertFalse(foundUser.isPresent());
    }
}
