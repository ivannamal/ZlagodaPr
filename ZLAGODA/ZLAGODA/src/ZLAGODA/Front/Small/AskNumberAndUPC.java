package ZLAGODA_project.Front.Small;

import ZLAGODA_project.DataBase.Exeptions.CantCreateException;
import ZLAGODA_project.DataBase.Exeptions.CantEditException;
import ZLAGODA_project.DataBase.Exeptions.InvalidValueException;
import ZLAGODA_project.DataBase.Exeptions.WentInNegativeExeption;
import ZLAGODA_project.Front.CashierMenu;
import ZLAGODA_project.Front.ManagerMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AskNumberAndUPC extends JDialog {
    private JPanel MainPanel;
    private JTextField textField1;
    private JButton confirmButton;
    private JTextField textField2;

    public Integer _cheqID = null;
    CashierMenu Owner = null;
    public AskNumberAndUPC(Frame owner, boolean modal, Integer CheqId){
        super(owner, modal);
        _cheqID = CheqId;
        Owner = (CashierMenu) owner;
        this.setTitle("NumberAndUPC");
        setContentPane(MainPanel);
        setMinimumSize(new Dimension(100,200));
        setModal(true);
        setLocationRelativeTo(Owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer num = null;
                Integer upc = null;
                try {
                    try {
                        num = Integer.valueOf(textField1.getText());
                        upc = Integer.valueOf(textField2.getText());
                        Owner.DBC.AddNewLineToCheq(upc, num, _cheqID);
                        Owner.updateCreatedCheq();
                        dispose();
                    }
                    catch (WentInNegativeExeption ex) {
                        JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                    } catch (CantEditException ex) {
                        JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                    } catch (CantCreateException ex) {
                        JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
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
