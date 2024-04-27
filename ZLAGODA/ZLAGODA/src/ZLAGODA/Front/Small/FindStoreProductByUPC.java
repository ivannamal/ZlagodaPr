package ZLAGODA_project.Front.Small;

import ZLAGODA_project.DataBase.DataClasses.Store_ProductData;
import ZLAGODA_project.Front.CashierMenu;
import ZLAGODA_project.Front.ManagerMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FindStoreProductByUPC extends JDialog {
    private JLabel StoreProduct;
    private JLabel expire_Date;
    private JLabel id_product;
    private JLabel products_number;
    private JLabel selling_price;
    private JButton searchButton;
    private JTextField textField1;
    private JLabel UPC;
    private JPanel MainPanel;

    CashierMenu Owner = null;
    Integer ID = null;
    public FindStoreProductByUPC(Frame owner, boolean modal){
        super(owner, modal);
        Owner = (CashierMenu) owner;
        this.setTitle("Employee");
        setContentPane(MainPanel);
        setMinimumSize(new Dimension(540,520));
        setModal(true);
        setLocationRelativeTo(Owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);



        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Store_ProductData data = null;
                Integer UPC = null;
                try {
                    UPC = Integer.valueOf(textField1.getText());
                }
                catch (Exception EE){
                    JOptionPane.showMessageDialog(new JFrame(), "InvalidValueException", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                data = Owner.DBC.getStoreProductById(UPC);

                if(data == null){
                    JOptionPane.showMessageDialog(new JFrame(), "There no StoreProduct with that UPC", "ERROR", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                expire_Date.setText("expire_Date "+data._expire_Date);
                id_product.setText("id_product "+data._id_product);
                products_number.setText("products_number "+data._products_number);
                selling_price.setText("selling_price "+data._selling_price);

            }
        });
    }

}
