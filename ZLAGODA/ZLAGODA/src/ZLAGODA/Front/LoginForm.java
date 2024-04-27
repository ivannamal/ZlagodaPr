package ZLAGODA_project.Front;

import ZLAGODA_project.DataBase.DataBaseController;
import ZLAGODA_project.DataBase.DataClasses.EmployeeData;
import ZLAGODA_project.DataBase.TWO;
import org.sqlite.core.DB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame {
    private JPanel MainPanel;
    private JTextField LoginField;
    private JPasswordField PasswordField;
    private JButton ConfirmButton;
    private JLabel PleaseLogInLabel;
    private JLabel LoginLabel;
    private JLabel PasswordLabel;




    public DataBaseController DBC = null;
    public EmployeeData ME = null;

    public LoginForm() {
        DBC = new DataBaseController("ZLAGODA");
        setTitle("LogInForm");
        setContentPane(MainPanel);
        setMinimumSize(new Dimension(320,180));
        //setModal(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        ConfirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TWO<String, Integer> login = DBC.tryLogin(LoginField.getText(), String.valueOf(PasswordField.getPassword()));
                if(login == null){
                    JOptionPane.showMessageDialog(new JFrame(), "Login or Password is incorrect, try again", "Can`t login", JOptionPane.WARNING_MESSAGE);
                }
                else{
                    EmployeeData employee = DBC.getEmployeeById(login._second);
                    if(login._first.equals("manager")){
                        ManagerMenu MM = new ManagerMenu(employee, DBC);
                        MM.setVisible(true);
                    }
                    else{
                        CashierMenu CM = new CashierMenu(employee, DBC);
                        CM.setVisible(true);
                    }
                    dispose();
                }
            }
        });
    }


}
