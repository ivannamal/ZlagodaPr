package ZLAGODA_project.Front.Small;

import ZLAGODA_project.DataBase.DataClasses.CategoryData;
import ZLAGODA_project.DataBase.DataClasses.ProductData;
import ZLAGODA_project.DataBase.DataClasses.Store_ProductData;
import ZLAGODA_project.DataBase.DataHolder;
import ZLAGODA_project.DataBase.Exeptions.*;
import ZLAGODA_project.Front.ManagerMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class AddStoreProduct extends JDialog{
    private JPanel MainPanel;
    private JLabel AddStoreProductLabel;
    private JLabel UPC;
    private JTextField textField1;
    private JCheckBox autoIncrementCheckBox;
    private JTextField textField3;
    private JTextField textField4;
    private JButton confirmButton;
    private JComboBox comboBox1;
    private JTextField textField2;


    ManagerMenu Owner = null;
    Integer ID = null;
    DataHolder _categoryComboBoxData = null;
    public AddStoreProduct(Frame owner, boolean modal, Store_ProductData data, Integer id){
        super(owner, modal);
        Owner = (ManagerMenu) owner;
        this.setTitle("StoreProduct");
        setContentPane(MainPanel);
        setMinimumSize(new Dimension(540,520));
        setModal(true);
        setLocationRelativeTo(Owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        DefaultComboBoxModel tmpModel = new DefaultComboBoxModel();
        _categoryComboBoxData = Owner.DBC.getAllProducts_SortByName();
        for (LinkedList<Object> datum : _categoryComboBoxData._data) {
            tmpModel.addElement(datum.get(0).toString()+" "+datum.get(2));
        }
        comboBox1.setModel(tmpModel);


        if(id != null){
            autoIncrementCheckBox.setVisible(false);
            ID = id;
            textField1.setText(data._UPC.toString());
            textField2.setText(data._expire_Date.toString());
            ProductData CD = Owner.DBC.getProductById(data._id_product);
            comboBox1.setSelectedItem(CD._id_product.toString()+" "+CD._product_name);
            textField3.setText(data._selling_price.toString());
            textField4.setText(data._products_number.toString());
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


    Store_ProductData getData(){
        Store_ProductData data = null;
        try {
            Integer index = comboBox1.getSelectedIndex();
            if(index == -1){
                throw new InvalidValueException();
            }
            data = new Store_ProductData(
                    textField1.getText(),
                    textField2.getText(),
                    _categoryComboBoxData._data.get(index).get(0).toString(),
                    textField3.getText(),
                    textField4.getText()
            );
        } catch (InvalidValueException ex) {
            JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        } catch (NegativeValueException ex) {
            JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        } catch (InvalidDateFormatException ex) {
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

        Store_ProductData data = getData();
        if(data == null){
            return;
        }

        try {
            Owner.DBC.createNewStore_Product_AI(data, ai);
        } catch (CantCreateException ex) {
            JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        dispose();
    }

    public void editEmployee(){
        Store_ProductData data = getData();
        if(data == null){
            return;
        }
        try {
            Owner.DBC.editStore_Product(ID, data);
        }
        catch (CantEditException ex) {
            JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        dispose();
    }


}
