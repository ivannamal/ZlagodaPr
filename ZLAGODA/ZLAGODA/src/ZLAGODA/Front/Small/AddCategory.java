package ZLAGODA_project.Front.Small;

import ZLAGODA_project.DataBase.DataClasses.CategoryData;
import ZLAGODA_project.DataBase.DataClasses.EmployeeData;
import ZLAGODA_project.DataBase.Exeptions.*;
import ZLAGODA_project.Front.CashierMenu;
import ZLAGODA_project.Front.ManagerMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCategory extends JDialog{
    private JPanel MainPanel;
    private JLabel AddCategoryLabel;
    private JLabel category_numberLabel;
    private JTextField textField1;
    private JCheckBox autoIncrementCheckBox;
    private JTextField textField2;
    private JButton confirmButton;



    ManagerMenu Owner = null;
    Integer ID = null;
    public AddCategory(Frame owner, boolean modal, CategoryData data, Integer id){
        super(owner, modal);
        Owner = (ManagerMenu) owner;
        this.setTitle("Category");
        setContentPane(MainPanel);
        setMinimumSize(new Dimension(540,520));
        setModal(true);
        setLocationRelativeTo(Owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        if(id != null){
            autoIncrementCheckBox.setVisible(false);
            ID = id;
            textField1.setText(data._category_number.toString());
            textField2.setText(data._category_name.toString());
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


    CategoryData getData(){
        CategoryData data = null;
        try {
            data = new CategoryData(
                    textField1.getText(),
                    textField2.getText()
            );
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

        CategoryData data = getData();
        if(data == null){
            return;
        }

        try {
            Owner.DBC.createNewCategory_AI(data, ai);
        } catch (CantCreateException ex) {
            JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        dispose();
    }

    public void editEmployee(){
        CategoryData data = getData();
        if(data == null){
            return;
        }
        try {
            Owner.DBC.editCategory(ID, data);
        }
        catch (CantEditException ex) {
            JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        dispose();
    }


}
