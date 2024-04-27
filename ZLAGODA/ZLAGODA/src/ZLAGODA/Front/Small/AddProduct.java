package ZLAGODA_project.Front.Small;

import ZLAGODA_project.DataBase.DataClasses.CategoryData;
import ZLAGODA_project.DataBase.DataClasses.EmployeeData;
import ZLAGODA_project.DataBase.DataClasses.ProductData;
import ZLAGODA_project.DataBase.DataHolder;
import ZLAGODA_project.DataBase.Exeptions.*;
import ZLAGODA_project.Front.ManagerMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class AddProduct extends JDialog{
    private JPanel MainPanel;
    private JLabel AddProductLabel;
    private JLabel id_productLabel;
    private JTextField textField1;
    private JCheckBox autoIncrementCheckBox;
    private JTextField textField3;
    private JTextField textField4;
    private JButton confirmButton;
    private JComboBox comboBox1;


    ManagerMenu Owner = null;
    Integer ID = null;
    DataHolder _categoryComboBoxData = null;
    public AddProduct(Frame owner, boolean modal, ProductData data, Integer id){
        super(owner, modal);
        Owner = (ManagerMenu) owner;
        this.setTitle("Product");
        setContentPane(MainPanel);
        setMinimumSize(new Dimension(540,520));
        setModal(true);
        setLocationRelativeTo(Owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        DefaultComboBoxModel tmpModel = new DefaultComboBoxModel();
        _categoryComboBoxData = Owner.DBC.getAllCategory_SortByName();
        for (LinkedList<Object> datum : _categoryComboBoxData._data) {
            tmpModel.addElement(datum.get(0).toString()+" "+datum.get(1));
        }
        comboBox1.setModel(tmpModel);


        if(id != null){
            autoIncrementCheckBox.setVisible(false);
            ID = id;
            textField1.setText(data._id_product.toString());
            CategoryData CD = Owner.DBC.getCategoryById(data._category_number);
            comboBox1.setSelectedItem(CD._category_number.toString()+" "+CD._category_name);
            textField3.setText(data._product_name.toString());
            textField4.setText(data._characteristics.toString());
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


    ProductData getData(){
        ProductData data = null;
        try {
            Integer index = comboBox1.getSelectedIndex();
            if(index == -1){
                throw new InvalidValueException();
            }

            Integer productId = Integer.parseInt(textField1.getText());
            data = new ProductData(
                    productId,
                    (Integer) _categoryComboBoxData._data.get(index).get(0),
                    textField3.getText(),
                    textField4.getText()
            );
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "Invalid product ID format. Please enter a valid integer.", "ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
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

        ProductData data = getData();
        if(data == null){
            return;
        }

        try {
            Owner.DBC.createNewProduct_AI(data, ai);
        } catch (CantCreateException ex) {
            JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        dispose();
    }

    public void editEmployee(){
        ProductData data = getData();
        if(data == null){
            return;
        }
        try {
            Owner.DBC.editProduct(ID, data);
        }
        catch (CantEditException ex) {
            JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        dispose();
    }

}
