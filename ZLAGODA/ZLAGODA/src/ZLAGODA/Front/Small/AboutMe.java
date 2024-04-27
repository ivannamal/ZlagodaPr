package ZLAGODA_project.Front.Small;

import ZLAGODA_project.DataBase.DataClasses.EmployeeData;
import ZLAGODA_project.Front.CashierMenu;
import ZLAGODA_project.Front.ManagerMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AboutMe extends JDialog{
    private JPanel MainPanel;
    private JLabel AboutMe;
    private JLabel id_employeeLabel;
    private JButton confirmButton;
    private JLabel empl_surname;
    private JLabel empl_name;
    private JLabel empl_patronymic;
    private JLabel empl_role;
    private JLabel salary;
    private JLabel date_of_birth;
    private JLabel data_of_start;
    private JLabel phone_number;
    private JLabel city;
    private JLabel street;
    private JLabel zip_code;

    CashierMenu Owner = null;
    Integer ID = null;
    public AboutMe(Frame owner, boolean modal){
        super(owner, modal);
        Owner = (CashierMenu) owner;
        this.setTitle("Employee");
        setContentPane(MainPanel);
        setMinimumSize(new Dimension(540,520));
        setModal(true);
        setLocationRelativeTo(Owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        id_employeeLabel.setText("id_employee "+Owner.ME._id_employee);
        empl_surname.setText("empl_surname "+Owner.ME._empl_surname);
        empl_name.setText("empl_name "+Owner.ME._empl_name);
        if(Owner.ME._empl_patronymic == null){
            empl_patronymic.setText("empl_patronymic "+"NULL");
        }
        else{
            empl_patronymic.setText("empl_patronymic "+Owner.ME._empl_patronymic);
        }
        empl_role.setText("empl_role "+Owner.ME._empl_role);
        salary.setText("salary "+Owner.ME._salary);
        date_of_birth.setText("date_of_birth "+Owner.ME._date_of_birth);
        data_of_start.setText("data_of_start "+Owner.ME._data_of_start);
        phone_number.setText("phone_number "+Owner.ME._phone_number);
        city.setText("city "+Owner.ME._city);
        street.setText("street "+Owner.ME._street);
        zip_code.setText("zip_code "+Owner.ME._zip_code);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

}
