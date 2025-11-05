package manager_UI;

import lombok.Getter;
import utils.StringsHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuHolder {
    public static final String START = "startMenu";
    public static final String UPDATE = "updateMenu";
    @Getter
    private static final HashMap<String,Menu> menuMap = new HashMap<>();
    static {
        List<Menu.Button> btnStart = new ArrayList<>();
        btnStart.add(new Menu.Button(StringsHolder.BUTTON_SHOW_ALL, UIManager::showAllUsers));
        btnStart.add(new Menu.Button(StringsHolder.BUTTON_GET_USER, UIManager::showUserById));
        btnStart.add(new Menu.Button(StringsHolder.BUTTON_DELETE_USER,UIManager::deleteUserById));
        btnStart.add(new Menu.Button(StringsHolder.BUTTON_ADD_USER,UIManager::addUser));
        btnStart.add(new Menu.Button(StringsHolder.BUTTON_UPDATE_USER,UIManager::updateUserById));
        btnStart.add(new Menu.Button(StringsHolder.BUTTON_EXIT,() ->System.out.println(StringsHolder.END_MESSAGE)));
        Menu menuStart = new Menu(StringsHolder.MENU_START_MESSAGE,btnStart);
        menuMap.put(START,menuStart);


        List<Menu.Button> btnUpdate= new ArrayList<>();
        btnUpdate.add(new Menu.Button(StringsHolder.NAME,() ->UIManager.updateField(StringsHolder.USER_FIELD_NAME)));
        btnUpdate.add(new Menu.Button(StringsHolder.EMAIL,() ->UIManager.updateField(StringsHolder.USER_FIELD_EMAIL)));
        btnUpdate.add(new Menu.Button(StringsHolder.AGE,() ->UIManager.updateField(StringsHolder.USER_FIELD_AGE)));
        btnUpdate.add(new Menu.Button(StringsHolder.UPDATE,UIManager::startUpdate));
        btnUpdate.add(new Menu.Button(StringsHolder.CANCEL,() -> UIManager.showMenu(START)));
        Menu menuUpdate = new Menu(StringsHolder.MENU_SELECT_FIELD,btnUpdate);
        menuMap.put(UPDATE,menuUpdate);
    }

}
