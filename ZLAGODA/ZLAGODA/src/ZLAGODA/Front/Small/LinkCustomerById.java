package ZLAGODA_project.Front.Small;

import ZLAGODA_project.DataBase.DataClasses.Customer_CardData;
import ZLAGODA_project.DataBase.DataHolder;
import ZLAGODA_project.DataBase.Exeptions.CantCreateException;
import ZLAGODA_project.DataBase.Exeptions.CantEditException;
import ZLAGODA_project.DataBase.Exeptions.InvalidValueException;
import ZLAGODA_project.DataBase.Exeptions.WentInNegativeExeption;
import ZLAGODA_project.Front.CashierMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LinkCustomerById extends JDialog{
    private JPanel MainPanel;
    private JTextField textField1;
    private JButton confirmButton;


    public Integer _upc = null;
    public Integer _cheqID = null;
    CashierMenu Owner = null;
    public LinkCustomerById(Frame owner, boolean modal, Integer CheqId){
        super(owner, modal);
        _cheqID = CheqId;
        Owner = (CashierMenu) owner;
        this.setTitle("LinkCustomerById");
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
                        num = Integer.valueOf(textField1.getText());
                        Customer_CardData tmpdata = Owner.DBC.getCustomerById(num);
                        if(tmpdata == null){
                            JOptionPane.showMessageDialog(new JFrame(), "There no customer with this card number", "ERROR", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        Owner.DBC.LinkCustomerCardToCheq(num, _cheqID);
                        Owner.updateCreatedCheq();
                        dispose();
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
