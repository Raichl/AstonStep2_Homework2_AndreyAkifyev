package utils;

import user.User;

import java.lang.reflect.Field;

public enum StringsHolder {;
    public static final String MENU_START_MESSAGE = "Выберите действие";
    public static final String BUTTON_SHOW_ALL = "Отобразить всех пользователей в таблице";
    public static final String BUTTON_GET_USER = "Получить пользователя по id";
    public static final String BUTTON_DELETE_USER = "Удалить пользователя по id";
    public static final String BUTTON_ADD_USER = "Добавить пользователя";
    public static final String BUTTON_UPDATE_USER = "Обновить пользователя по id";
    public static final String BUTTON_EXIT = "Выйти";

    public static final String MENU_SELECT_FIELD = "Выберите поля для обновления";
    public static final String NAME = "Имя";
    public static final String EMAIL = "Email";
    public static final String AGE = "Возраст";
    public static final String UPDATE = "Обновить";
    public static final String ID = "id";
    public static final String CANCEL = "Отмена";
    public static final String INSERT = "Введите";

    public static final String UI_INPUT_USERNAME = "Введите имя пользователя";
    public static final String UI_INPUT_EMAIL = "Введите Email пользователя";
    public static final String UI_INPUT_AGE ="Введите возраст пользователя";
    public static final String UI_INPUT_CHECK_CORRECT = "Убедитесь в правильности ввода";
    public static final String UI_USER_SELECT = "Выбран пользователь:";
    public static final String UI_DIV_LINE = "------------------------------------------------------------------";
    public static final String UI_DELETE_SUCCESS = "Пользователь успешно удален";


    public static final String END_MESSAGE = "Завершение работы";

    public static final String USER_FIELD_NAME;
    public static final String USER_FIELD_AGE;
    public static final String USER_FIELD_EMAIL;

    public static final String ERROR_ILLEGAL_ARGUMENT = "Неверный ввод, попробуйте снова";
    public static final String ERROR_NOT_FOUND = "Пользователь с таки id  не найден";
    public static final String ERROR_UPDATE = "ошибка обновления данных";
    public static final String ERROR_GET = "ошибка получения данных";
    public static final String ERROR_DELETE = "ошибка удаления данных";
    public static final String ERROR_CREATE = "ошибка записи данных";
    public static final String ERROR_EMPTY_DB = "Пользователи в базе отсутствуют";


    static {
        Field[] userFields = User.class.getDeclaredFields();
        USER_FIELD_NAME = userFields[1].getName();
        USER_FIELD_EMAIL = userFields[2].getName();
        USER_FIELD_AGE = userFields[3].getName();
    }


}
