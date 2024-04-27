package ZLAGODA_project.Front.Small;

import ZLAGODA_project.DataBase.DataHolder;
import ZLAGODA_project.DataBase.Exeptions.InvalidDateFormatException;
import ZLAGODA_project.Front.CashierMenu;
import ZLAGODA_project.Front.ManagerMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AskPeriodOfTimeSecond  extends JDialog{
    private JPanel MainPanel;
    private JTextField textField1;
    private JButton confirmButton;
    private JTextField textField2;
    private JLabel Summ;

    ManagerMenu Owner = null;
    public AskPeriodOfTimeSecond(Frame owner, boolean modal){
        super(owner, modal);
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
                    DataHolder sum = Owner.DBC.getSumOfAllSoldProductsInSpecificPeriodOfTime(textField1.getText(), textField2.getText());
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
