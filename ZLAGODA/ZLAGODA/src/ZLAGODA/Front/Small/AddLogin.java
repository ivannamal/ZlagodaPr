package ZLAGODA_project.Front.Small;

import ZLAGODA_project.DataBase.DataBaseController;
import ZLAGODA_project.DataBase.DataClasses.CategoryData;
import ZLAGODA_project.DataBase.DataClasses.EmployeeData;
import ZLAGODA_project.DataBase.DataClasses.ProductData;
import ZLAGODA_project.DataBase.DataHolder;
import ZLAGODA_project.DataBase.Exeptions.CantCreateException;
import ZLAGODA_project.DataBase.Exeptions.CantEditException;
import ZLAGODA_project.DataBase.Exeptions.InvalidValueException;
import ZLAGODA_project.Front.ManagerMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class AddLogin extends JDialog {
    private JPanel MainPanel;
    private JLabel AddLoginLabel;
    private JLabel empl_loginLabel;
    private JTextField textField1;
    private JCheckBox autoIncrementCheckBox;
    private JTextField textField3;
    private JButton confirmButton;
    private JComboBox comboBox1;


    ManagerMenu Owner = null;
    String ID = null;
    DataHolder _categoryComboBoxData = null;

    public AddLogin(Frame owner, boolean modal, String oldlogin, String idlogin, Integer employeeId){
        super(owner, modal);
        Owner = (ManagerMenu) owner;
        this.setTitle("Login");
        setContentPane(MainPanel);
        setMinimumSize(new Dimension(540,520));
        setModal(true);
        setLocationRelativeTo(Owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        DefaultComboBoxModel tmpModel = new DefaultComboBoxModel();
        _categoryComboBoxData = Owner.DBC.getAllEmployee_SortByName();
        for (LinkedList<Object> datum : _categoryComboBoxData._data) {
            String patr = "NULL";
            if(datum.get(3) != null){
                patr = (String) datum.get(3);
            }
            tmpModel.addElement(datum.get(0).toString()+" "+datum.get(1)+" "+datum.get(2)+" "+patr);
        }
        comboBox1.setModel(tmpModel);


        if(idlogin != null){
            autoIncrementCheckBox.setVisible(false);
            ID = idlogin;
            textField1.setText(oldlogin);
            EmployeeData CD = Owner.DBC.getEmployeeById(employeeId);
            String patr = "NULL";
            if(CD._empl_patronymic != null){
                patr = CD._empl_patronymic;
            }
            comboBox1.setSelectedItem(CD._id_employee.toString()+" "+CD._empl_surname+" "+CD._empl_name+" "+patr);
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


    Object[] getData(){
        Object[] data = null;
        try {
            Integer index = comboBox1.getSelectedIndex();
            if(index == -1){
                throw new InvalidValueException();
            }

            data = new Object[]{
                    textField1.getText(),
                    null,
                    (Integer) _categoryComboBoxData._data.get(index).get(0)
            };
            if(textField3.getText().length() != 0){
                data[1] = (textField3.getText());
            }
            else{
                if(ID == null){
                    throw new InvalidValueException();
                }
            }
        } catch (InvalidValueException ex) {
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

        Object[] data = getData();
        if(data == null){
            return;
        }

        try {
            Owner.DBC.createNewLogin((Integer)data[2], (String)data[0], (String) data[1]);
        } catch (CantCreateException ex) {
            JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        dispose();
    }

    public void editEmployee(){
        Object[] data = getData();
        if(data == null){
            return;
        }
        try {
            Owner.DBC.editLogin(ID, (Integer)data[2], (String)data[0], (String) data[1]);
        }
        catch (CantEditException ex) {
            JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        dispose();
    }


}
