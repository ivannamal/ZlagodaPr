package ZLAGODA_project.Front.Small;

import ZLAGODA_project.DataBase.DataHolder;
import ZLAGODA_project.DataBase.Exeptions.InvalidDateFormatException;
import ZLAGODA_project.Front.ManagerMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AskPeriodOfTimeThird extends JDialog{
    private JPanel MainPanel;
    private JTextField textField1;
    private JButton confirmButton;
    private JTextField textField2;
    private JLabel Summ;

    public Integer _prod_id = null;
    ManagerMenu Owner = null;
    public AskPeriodOfTimeThird(Frame owner, boolean modal, Integer prod_id){
        super(owner, modal);
        _prod_id = prod_id;
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
                    DataHolder sum = Owner.DBC.getCountOfSoldProductsInSpecificPeriodOfTime(prod_id, textField1.getText(), textField2.getText());
                    if(sum._data.size() >= 1){
                        Summ.setText("Count "+sum._data.get(0).get(0));
                    }
                    else{
                        Summ.setText("Count 0");
                    }
                } catch (InvalidDateFormatException ex) {
                    JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }


            }
        });
    }
}
