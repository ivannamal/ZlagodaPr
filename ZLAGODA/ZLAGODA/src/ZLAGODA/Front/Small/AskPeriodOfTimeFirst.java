package ZLAGODA_project.Front.Small;

import ZLAGODA_project.DataBase.DataHolder;
import ZLAGODA_project.DataBase.Exeptions.*;
import ZLAGODA_project.Front.CashierMenu;
import ZLAGODA_project.Front.ManagerMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AskPeriodOfTimeFirst extends JDialog{
    private JPanel MainPanel;
    private JTextField textField1;
    private JButton confirmButton;
    private JTextField textField2;
    private JLabel Summ;

    public Integer _id_empl = null;
    ManagerMenu Owner = null;
    public AskPeriodOfTimeFirst(Frame owner, boolean modal, Integer id_empl){
        super(owner, modal);
        _id_empl = id_empl;
        Owner = (ManagerMenu) owner;
        this.setTitle("NumberAndUPC");
        setContentPane(MainPanel);
        setMinimumSize(new Dimension(100,200));
        setModal(true);
        setLocationRelativeTo(Owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DataHolder sum = Owner.DBC.getSumOfAllSoldProductsByEmployeeORallInSpecificPeriodOfTime(_id_empl, textField1.getText(), textField2.getText());
                    if(sum._data.size() >= 1){
                        Summ.setText("Sum "+sum._data.get(0).get(0));
                    }
                    else{
                        Summ.setText("Sum 0");
                    }
                } catch (InvalidDateFormatException ex) {
                    JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }


            }
        });
    }

}
