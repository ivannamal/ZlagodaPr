package ZLAGODA_project.Front.Small;

import ZLAGODA_project.DataBase.DataClasses.EmployeeData;
import ZLAGODA_project.DataBase.DataHolder;
import ZLAGODA_project.DataBase.Exeptions.CantCreateException;
import ZLAGODA_project.DataBase.Exeptions.CantEditException;
import ZLAGODA_project.DataBase.Exeptions.InvalidValueException;
import ZLAGODA_project.DataBase.Exeptions.WentInNegativeExeption;
import ZLAGODA_project.DataBase.TWO;
import ZLAGODA_project.Front.CashierMenu;
import ZLAGODA_project.Front.ManagerMenu;
import ZLAGODA_project.Front.NotEditableTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FindPhoneAndAdress extends JDialog{
    private JTextField textField1;
    private JButton confirmButton;
    private JTextField textField2;
    private JTextField textField3;
    private JPanel MainPanel;
    private JTable table1;



    ManagerMenu Owner = null;
    NotEditableTableModel _NETM = null;
    public FindPhoneAndAdress(Frame owner, boolean modal){
        super(owner, modal);
        Owner = (ManagerMenu) owner;
        this.setTitle("Number");
        setContentPane(MainPanel);
        setMinimumSize(new Dimension(100,200));
        setModal(true);
        setLocationRelativeTo(Owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);




        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer num = null;
                try {
                    try {

                        TWO<Object[], Object[][]> data = Owner.DBC.getTelephoneAndAddressByPIB(textField1.getText(), textField2.getText(), textField3.getText()).ConvertToSwingTableData();
                        _NETM = new NotEditableTableModel(data._second, data._first);
                        table1.setModel(_NETM);
                    }
                    catch (Exception ex){
                        throw new InvalidValueException();
                    }
                }
                catch (InvalidValueException ex){
                    JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }


            }
        });
    }

}
