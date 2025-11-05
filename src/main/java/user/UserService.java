package user;

import dao.UserDao;
import dao.UserDaoImpl;

import java.util.List;

public class UserService {
    private final UserDao usersDao = new UserDaoImpl();
    public  User findUser(int id){
        return  usersDao.findById(id);
    }
    public void saveUser(User user){
        usersDao.save(user);
    }

    public void updateUser(User user){
        usersDao.update(user);
    }

    public void deleteUser(User user){
        usersDao.delete(user);
    }

    public List<User> findAllUsers(){
        return usersDao.findAll();
    }
}
