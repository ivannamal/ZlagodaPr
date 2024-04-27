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

public class AskNumber extends JDialog{
    private JPanel MainPanel;
    private JTextField textField1;
    private JButton confirmButton;


    public Integer _upc = null;
    public Integer _cheqID = null;
    CashierMenu Owner = null;
    public AskNumber(Frame owner, boolean modal, Integer UPC, Integer CheqId){
        super(owner, modal);
        _upc = UPC;
        _cheqID = CheqId;
        Owner = (CashierMenu) owner;
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
                        num = Integer.valueOf(textField1.getText());
                        Owner.DBC.AddNewLineToCheq(_upc, num, _cheqID);
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
