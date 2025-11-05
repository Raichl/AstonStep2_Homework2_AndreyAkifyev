package dao;

import user.User;

import java.util.List;

public interface UserDao  {
    String SELECT_ALL = "From User";
    void save(User user);
    User findById(int id);
    void update(User user);
    void delete(User user);
    List<User> findAll();
}
