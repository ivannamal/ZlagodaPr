package ZLAGODA_project;

import ZLAGODA_project.DataBase.DataBaseController;
import ZLAGODA_project.DataBase.DataHolder;

import java.util.List;
import java.util.ArrayList;

public class DisplayLogins {

    public static void main(String[] args) {
        // Створюємо екземпляр класу для роботи з базою даних
        DataBaseController database = new DataBaseController("ZLAGODA");

        // Отримуємо всі логіни та паролі з бази даних
        DataHolder loginsData = database.getAllLogins();
        

        // Відображаємо отримані дані разом з паролями
        displayLogins(loginsData);
    }

    // Метод для відображення логінів та паролів
    public static void displayLogins(DataHolder loginsData) {
        System.out.println("Усі логіни та паролі:");
        List<List<Object>> logins = new ArrayList<>(loginsData._data);
        for (List<Object> login : logins) {
            String username = (String) login.get(0);
            String password = (String) login.get(1);
            System.out.println("Логін: " + username + ", Пароль: " + password);
        }
    }
}
