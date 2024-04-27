package ZLAGODA_project.Front.Small;

import ZLAGODA_project.DataBase.DataClasses.Customer_CardData;
import ZLAGODA_project.DataBase.Exeptions.*;
import ZLAGODA_project.Front.ManagerMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCustomer extends JDialog{
    private JPanel MainPanel;
    private JLabel AddCustomerLabel;
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
    private JButton confirmButton;


    ManagerMenu Owner = null;
    Integer ID = null;
    public AddCustomer(Frame owner, boolean modal, Customer_CardData data, Integer id){
        super(owner, modal);
        Owner = (ManagerMenu) owner;
        this.setTitle("Customer");
        setContentPane(MainPanel);
        setMinimumSize(new Dimension(540,520));
        setModal(true);
        setLocationRelativeTo(Owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        if(id != null){
            autoIncrementCheckBox.setVisible(false);
            ID = id;
            textField1.setText(data._card_number.toString());
            textField2.setText(data._cust_surname.toString());
            textField3.setText(data._cust_name.toString());

            String patr = data._cust_patronymic;
            if(patr == null){
                patr = "null";
            }
            textField4.setText(patr);

            textField5.setText(data._phone_number.toString());
            String city = data._cust_patronymic;
            if(city == null){
                city = "null";
            }
            textField6.setText(city);


            String street = data._street;
            if(street == null){
                street = "null";
            }
            textField7.setText(street);

            String zip_code = data._zip_code;
            if(zip_code == null){
                zip_code = "null";
            }
            textField8.setText(zip_code);

            textField9.setText(data._percent.toString());
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


    Customer_CardData getData(){
        Customer_CardData data = null;
        try {
            data = new Customer_CardData(
                    textField1.getText(),
                    textField2.getText(),
                    textField3.getText(),
                    textField4.getText(),
                    textField5.getText(),
                    textField6.getText(),
                    textField7.getText(),
                    textField8.getText(),
                    textField9.getText()
            );
        } catch (InvalidValueException ex) {
            JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        } catch (TooBigNumberException ex) {
            JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        } catch (NegativeValueException ex) {
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

        Customer_CardData data = getData();
        if(data == null){
            return;
        }

        try {
            Owner.DBC.createNewCustomer_Card_AI(data, ai);
        } catch (CantCreateException ex) {
            JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        dispose();
    }

    public void editEmployee(){
        Customer_CardData data = getData();
        if(data == null){
            return;
        }
        try {
            Owner.DBC.editCustomer_Card(ID, data);
        }
        catch (CantEditException ex) {
            JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        dispose();
    }


}
