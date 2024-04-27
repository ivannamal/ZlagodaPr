package ZLAGODA_project;
import ZLAGODA_project.Front.CashierMenu;import ZLAGODA_project.Front.LoginForm;
import ZLAGODA_project.Front.ManagerMenu;import ZLAGODA_project.Front.Small.AddEmployee;
import javax.swing.*;
public class Main {
    public static void main(String[] args) {
        try {            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException e) {            e.printStackTrace();
        } catch (InstantiationException e) {            e.printStackTrace();
        } catch (IllegalAccessException e) {            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {            e.printStackTrace();
        }
        LoginForm LF = new LoginForm();        LF.setVisible(true);
        //AddEmployee AE = new AddEmployee(null, false);        //AE.setVisible(true);
        //ManagerMenu MM = new ManagerMenu();        //MM.setVisible(true);
        //CashierMenu CM = new CashierMenu();        //CM.setVisible(true);
    }}