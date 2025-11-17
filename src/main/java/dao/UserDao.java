package dao;

import user.User;

import java.util.List;
/**
 * Интерфейс для доступа к данным пользователей.
 * Определяет основные операции CRUD (Create, Read, Update, Delete) для сущности User.
 *
 */
 public interface UserDao {
    /**
     * SQL-запрос для выборки всех пользователей.
     */
    String SELECT_ALL = "From User";

    /**
     * Сохраняет нового пользователя в базе данных.
     *
     * @param user объект пользователя для сохранения
     * @throws IllegalArgumentException если переданный пользователь равен null
     */
    void save(User user);

    /**
     * Находит пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя
     * @return объект пользователя или null, если пользователь не найден
     * @throws IllegalArgumentException если id меньше или равен 0
     */
    User findById(int id);

    /**
     * Обновляет информацию о пользователе в базе данных.
     *
     * @param user объект пользователя с обновленными данными
     * @throws IllegalArgumentException если переданный пользователь равен null
     */
    void update(User user);

    /**
     * Удаляет пользователя из базы данных.
     *
     * @param user объект пользователя для удаления
     * @throws IllegalArgumentException если переданный пользователь равен null
     */
    void delete(User user);

    /**
     * Возвращает список всех пользователей из базы данных.
     *
     * @return список объектов User, может быть пустым, но не null
     */
    List<User> findAll();
}
