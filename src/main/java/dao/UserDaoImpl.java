package dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import user.User;
import utils.HibernateSessionFactory;

import java.util.List;

public class UserDaoImpl implements UserDao{
    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
    @Override
    public void save(User user){
        logger.info("Сохранение пользователя");
        Transaction transaction = null;
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            logger.info("Пользователь успешно сохранен c id: {}",user.getId());
        }catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
            logger.info("Пользователь НЕ сохранен ошибка{}",e.getMessage());
            throw new RuntimeException("ошибка сохранение");
        }
    }

    @Override
    public User findById(int id) {
        logger.info("Запрошен пользователь по id: {}",id);
        try(Session session = HibernateSessionFactory.getSessionFactory().openSession()){
            logger.info("Пользователь с id: {} успешно найден",id);
            return session.get(User.class,id);
        }catch (Exception e){
            logger.info("Пользователь с id: {} Не найден",id);
            throw new RuntimeException("Ошибка получения пользователя");
        }
    }

    @Override
    public void update(User user) {
        logger.info("Запрошено обновление пользователя с id{}",user.getId());
        Transaction transaction = null;
        try(Session session = HibernateSessionFactory.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
            logger.info("Пользователь с id {} успешно обновлен ",user.getId());
        }catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
            logger.info("Ошибка обновления пользователя с id:{}",user.getId());
            throw new RuntimeException("Ошибка получения пользователя");
        }
    }

    @Override
    public void delete(User user) {
        logger.info("Запрошено удаление пользователя с id{}",user.getId());
        Transaction transaction = null;
        try(Session session = HibernateSessionFactory.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.remove(user);
            transaction.commit();
            logger.info("Пользователь с id {} успешно удален",user.getId());
        }catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
            logger.info("Ошибка удаления пользователя с id:{}",user.getId());
            throw new RuntimeException("Ошибка получения пользователя");
        }
    }

    @Override
    public List<User> findAll() {
        logger.info("Получение Списка пользователей");
        try(Session session = HibernateSessionFactory.getSessionFactory().openSession()){
            logger.info("Пользователи успешно получены");
            return session.createQuery(SELECT_ALL,User.class).getResultList();
        }catch (Exception e){
            logger.info("Ошибка получения списка пользователей");
            throw new RuntimeException("Ошибка получения списка пользователей");
        }
    }
}
