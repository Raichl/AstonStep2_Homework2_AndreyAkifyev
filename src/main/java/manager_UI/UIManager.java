package manager_UI;

import user.User;
import user.UserService;
import utils.StringsHolder;

import java.util.List;
import java.util.Scanner;

public class UIManager {

    private static final UserService userService = new UserService();
    private static final Scanner scanner = new Scanner(System.in);
    private static User selectedUser;
    
    public static void showMenu(String menuName){
        MenuHolder.getMenuMap().get(menuName).showMenuAndChoose();
    }
    
    static public void showAllUsers(){
        try {
            List<User> allUsers = userService.findAllUsers();
            if (allUsers.isEmpty()) {
                System.out.println(StringsHolder.ERROR_EMPTY_DB);
            } else {
                System.out.println(StringsHolder.UI_DIV_LINE);
                allUsers.forEach(User::showInConsole);
                System.out.println(StringsHolder.UI_DIV_LINE);
            }
        } catch (Exception e) {
            System.out.println(StringsHolder.ERROR_GET);
        } finally {
            showMenu(MenuHolder.START);
        }
    }

    public static  void showUserById(){
        try {
            User user = userService.findUser(getInt(StringsHolder.ID));
            if (user != null){
                user.showInConsole();
            } else {
                System.out.println(StringsHolder.ERROR_NOT_FOUND);
            }
        } catch (Exception e) {
            System.out.println(StringsHolder.ERROR_GET);
        } finally {
            showMenu(MenuHolder.START);
        }
    }

    public static  void updateUserById(){
        try {
            User user = userService.findUser(getInt(StringsHolder.ID));
            if (user != null){
                System.out.println(StringsHolder.UI_USER_SELECT);
                user.showInConsole();
                selectedUser = user;
                showMenu(MenuHolder.UPDATE);
            }else{
                System.out.println(StringsHolder.ERROR_NOT_FOUND);
                showMenu(MenuHolder.START);
            }
        } catch (Exception e) {
            System.out.println(StringsHolder.ERROR_UPDATE);
            showMenu(MenuHolder.START);
        }
    }

    public static void updateField(String fieldName){
        if (fieldName.equals(StringsHolder.USER_FIELD_NAME)) {
            selectedUser.setName(getStringFromUser(StringsHolder.UI_INPUT_USERNAME));
        } else if (fieldName.equals(StringsHolder.USER_FIELD_AGE)) {
            selectedUser.setAge(getInt(StringsHolder.UI_INPUT_AGE));
        } else if (fieldName.equals(StringsHolder.USER_FIELD_EMAIL)) {
            selectedUser.setEmail(getStringFromUser(StringsHolder.UI_INPUT_EMAIL));
        }
        selectedUser.showInConsole();
        showMenu(MenuHolder.UPDATE);
    }

    public static void startUpdate(){
        try{
            userService.updateUser(selectedUser);
        }catch (RuntimeException e){
            System.out.println(StringsHolder.ERROR_UPDATE);
        }finally {
            selectedUser.showInConsole();
            selectedUser = null;
            showMenu(MenuHolder.START);
        }
    }

    public static void deleteUserById(){
        try {
            User user = userService.findUser(getInt(StringsHolder.ID));
            if (user != null){
                userService.deleteUser(user);
                System.out.println(StringsHolder.UI_DELETE_SUCCESS);
            }else{
                System.out.println(StringsHolder.ERROR_NOT_FOUND);
            }
        } catch (Exception e) {
            System.out.println(StringsHolder.ERROR_DELETE);
        } finally {
            showMenu(MenuHolder.START);
        }
    }

    public static void addUser(){
        try {
            User user = getUser();
            userService.saveUser(user);
        } catch (Exception e) {
            System.out.println(StringsHolder.ERROR_CREATE);
        } finally {
            showMenu(MenuHolder.START);
        }
    }

    private static int getInt(String fieldName){
        System.out.printf("%s %s\n",StringsHolder.INSERT,fieldName);
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        }catch (IllegalArgumentException e){
            System.out.println(StringsHolder.UI_INPUT_CHECK_CORRECT);
            return getInt(fieldName);
        }
        return id;
    }

    private static User getUser(){
        String name = getStringFromUser(StringsHolder.UI_INPUT_USERNAME);
        String email = getStringFromUser(StringsHolder.UI_INPUT_EMAIL);
        int age = getInt(StringsHolder.AGE);
        return new User(name,email,age);
    }

    private static String getStringFromUser(String text){
        System.out.println(text);
        String str = scanner.nextLine();
        if(str.isEmpty() || str.length() > 255){
            System.out.println(StringsHolder.ERROR_ILLEGAL_ARGUMENT);
            return getStringFromUser(text);
        }else {
            return str;
        }
    }
}
