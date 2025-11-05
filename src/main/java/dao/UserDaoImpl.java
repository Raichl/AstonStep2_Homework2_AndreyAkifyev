package dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import user.User;
import utils.HibernateSessionFactory;

import java.util.List;

public class UserDaoImpl implements UserDao{
    @Override
    public void save(User user){
        Transaction transaction = null;
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        }catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
            throw new RuntimeException("ошибка сохранение");
        }

    }

    @Override
    public User findById(int id) {
        try(Session session = HibernateSessionFactory.getSessionFactory().openSession()){
            return session.get(User.class,id);
        }catch (Exception e){
            throw new RuntimeException("Ошибка получения пользователя");
        }
    }

    @Override
    public void update(User user) {
        Transaction transaction = null;
        try(Session session = HibernateSessionFactory.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
        }catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка получения пользователя");
        }
    }

    @Override
    public void delete(User user) {
        Transaction transaction = null;
        try(Session session = HibernateSessionFactory.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.remove(user);
            transaction.commit();
        }catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка получения пользователя");
        }
    }

    @Override
    public List<User> findAll() {
        try(Session session = HibernateSessionFactory.getSessionFactory().openSession()){
            return session.createQuery(SELECT_ALL,User.class).getResultList();
        }catch (Exception e){
            throw new RuntimeException("Ошибка получения списка пользователей");
        }
    }
}
