package ZLAGODA_project.Front.Small;

import ZLAGODA_project.DataBase.DataBaseController;
import ZLAGODA_project.DataBase.DataClasses.EmployeeData;
import ZLAGODA_project.DataBase.Exeptions.*;
import ZLAGODA_project.Front.ManagerMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddEmployee extends JDialog {
    private JPanel MainPanel;
    private JLabel AddEmployeeLabel;
    private JLabel id_employeeLabel;
    private JTextField textField1;
    private JCheckBox autoIncrementCheckBox;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JTextField textField10;
    private JTextField textField11;
    private JButton confirmButton;
    private JTextField textField12;


    ManagerMenu Owner;
    Integer ID = null;
    public AddEmployee(Frame owner, boolean modal, EmployeeData data, Integer id){
        super(owner, modal);
        Owner = (ManagerMenu) owner;
        this.setTitle("Employee");
        setContentPane(MainPanel);
        setMinimumSize(new Dimension(540,520));
        setModal(true);
        setLocationRelativeTo(Owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        if(id != null){
            autoIncrementCheckBox.setVisible(false);
            ID = id;
            textField1.setText(data._id_employee.toString());
            textField2.setText(data._empl_surname.toString());
            textField3.setText(data._empl_name.toString());

            String patr = data._empl_patronymic;
            if(patr == null){
                patr = "null";
            }
            textField4.setText(patr);

            textField5.setText(data._empl_role.toString());
            textField6.setText(data._salary.toString());
            textField7.setText(data._date_of_birth.toString());
            textField8.setText(data._data_of_start.toString());
            textField9.setText(data._phone_number.toString());
            textField10.setText(data._city.toString());
            textField11.setText(data._street.toString());
            textField12.setText(data._zip_code.toString());
        }

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ID == null){
                    addEmployee();
                }
                else{
                    editEmployee();
                }
            }
        });
    }


    EmployeeData getData(){
        EmployeeData data = null;
        try {
            data = new EmployeeData(
                    textField1.getText(),
                    textField2.getText(),
                    textField3.getText(),
                    textField4.getText(),
                    textField5.getText(),
                    textField6.getText(),
                    textField7.getText(),
                    textField8.getText(),
                    textField9.getText(),
                    textField10.getText(),
                    textField11.getText(),
                    textField12.getText()
            );
        } catch (NegativeValueException ex) {
            JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        } catch (TooBigNumberException ex) {
            JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        } catch (InvalidDateFormatException ex) {
            JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        } catch (TooYoungException ex) {
            JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return data;
    }

    public void addEmployee(){
        boolean ai = false;
        if(autoIncrementCheckBox.isSelected()){
            ai = true;
        }

        EmployeeData data = getData();
        if(data == null){
            return;
        }

        try {
            Owner.DBC.createNewEmployee_AI(data, ai);
        } catch (CantCreateException ex) {
            JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        dispose();
    }

    public void editEmployee(){
        EmployeeData data = getData();
        if(data == null){
            return;
        }
        try {
            Owner.DBC.editEmployee(ID, data);
        }
        catch (CantEditException ex) {
            JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        dispose();
    }

}
