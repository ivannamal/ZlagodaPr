package ZLAGODA_project.Front;

import ZLAGODA_project.DataBase.DataBaseController;
import ZLAGODA_project.DataBase.DataClasses.Customer_CardData;
import ZLAGODA_project.DataBase.DataClasses.EmployeeData;
import ZLAGODA_project.DataBase.DataClasses.Store_ProductData;
import ZLAGODA_project.DataBase.DataHolder;
import ZLAGODA_project.DataBase.Exeptions.*;
import ZLAGODA_project.DataBase.TWO;
import ZLAGODA_project.DataBase.Utils;
import ZLAGODA_project.Front.Small.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.LinkedList;

public class CashierMenu extends JFrame{
    private JPanel MainPanel;
    private JTable table1;
    private JPanel MidDataPanel;
    private JPanel LeftPanel;
    private JPanel TopPanel;
    private JPanel Rightpanel;
    private JPanel BottomPanel;
    private JLabel TopLabel;
    private JPanel BottomLeft;
    private JPanel BottomRight;
    private JButton LogoutButton;
    private JComboBox ShowTableComboBox;
    private JTextField ProductNameField;
    private JLabel TableLabel;
    private JLabel ProductNameLabel;
    private JLabel CategoryLabel;
    private JComboBox CategoryComboBox;
    private JLabel SurnameLabel;
    private JTextField SurnameField;
    private JLabel NameLabel;
    private JTextField NameField;
    private JLabel PatronimicLabel;
    private JTextField PatronimicField;
    private JLabel FromDateLabel;
    private JTextField FromDateField;
    private JLabel ToDateLabel;
    private JTextField ToDateField;
    private JLabel CheckNumLabel;
    private JTextField CheckNumField;
    private JButton AddFromListButton;
    private JButton AddByUPC;
    private JButton AddCustomerButton;
    private JButton EditCustomerButton;
    private JButton FindStoreProductButton;
    private JButton AboutMeButton;
    private JPanel RightTopPanel;
    private JPanel RightMidlpanel;
    private JTable table2;
    private JTable table3;
    private JButton DeleteFromListButton;
    private JButton CreateBlankCheckButton;
    private JButton PrintCheckButton;
    private JButton CancelCheckButton;
    private JButton ShowButton;
    private JButton LinkCustomer;


    public NotEditableTableModel _NETM = null;
    public NotEditableTableModel _NETM_Top = null;
    public NotEditableTableModel _NETM_Centr = null;

    public int _showingTable = 0;
    public int _chosenTable = 0;
    public DataBaseController DBC = null;

    public EmployeeData ME = null;

    CashierMenu THIS = null;

    public Integer _cheqId = null;
    public CashierMenu(EmployeeData employee, DataBaseController dbc) {
        THIS = this;
        ME = employee;
        DBC = dbc;
        setTitle("MainMenu");
        setContentPane(MainPanel);
        setMinimumSize(new Dimension(1400,768));
        //setModal(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        DataBaseController DBC = new DataBaseController("ZLAGODA");
        DBC.basicInit();

        TWO<Object[], Object[][]> basicTableData = DBC.getAllProducts_SortByName().ConvertToSwingTableData();
        _NETM = new NotEditableTableModel(basicTableData._second, basicTableData._first);
        table1.setModel(_NETM);

        TWO<Object[], Object[][]> basicTableData_Top = DBC.getCheqByNumber(0).ConvertToSwingTableData();
        _NETM_Top = new NotEditableTableModel(basicTableData_Top._second, basicTableData_Top._first);
        table2.setModel(_NETM_Top);

        TWO<Object[], Object[][]> basicTableData_Centr = DBC.getAllLinesFromCheq(0).ConvertToSwingTableData();
        _NETM_Centr = new NotEditableTableModel(basicTableData_Centr._second, basicTableData_Centr._first);
        table3.setModel(_NETM_Centr);

        ShowTableComboBox.addItem("Products"); //0
        ShowTableComboBox.addItem("StoreProducts"); //1
        ShowTableComboBox.addItem("Customers"); //2
        ShowTableComboBox.addItem("TodayMyChecks"); //3
        ShowTableComboBox.addItem("PromProdByCount"); //4
        ShowTableComboBox.addItem("PromProdByName"); //5
        ShowTableComboBox.addItem("NonPromProdByCount"); //6
        ShowTableComboBox.addItem("NonPromProdByName"); //7
        //
        ShowTableComboBox.addItem("ProductsByName"); //8
        //name
        ShowTableComboBox.addItem("ProductsByCategory"); //9
        //cat
        ShowTableComboBox.addItem("CustomersBySurname"); //10
        //surname name patronymic
        ShowTableComboBox.addItem("MyCheckInPeriodOfTime"); //11
        //date1 date2
        ShowTableComboBox.addItem("CheckInfo"); //12
        //checknum

        hideLeftAdditional();
        InitTopButtons();



        ShowTableComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _chosenTable = ShowTableComboBox.getSelectedIndex();
                hideLeftAdditional();
                //hideAllButtons();
                switch (_chosenTable){
                    case 0:

                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 5:

                        break;
                    case 6:

                        break;
                    case 7:

                        break;
                    case 8:
                        productNameShowHide(true);
                        break;
                    case 9:
                        categoryShowHide(true);
                        break;
                    case 10:
                        custNameShowHide(true);
                        break;
                    case 11:
                        dateShowHide(true);
                        break;
                    case 12:
                        checkNumShowHide(true);
                        break;
                    default:
                }


            }
        });
        ShowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //SHOW

                try{
                    DataHolder DH = null;
                    InitTopButtons();
                    InitBottomButtons();
                    switch (_chosenTable){
                        case 0:

                            DH = DBC.getAllProducts_SortByName();
                            break;
                        case 1:
                            AddFromListButton.setVisible(true);
                            DH = DBC.getAllStoreProducts_SortByCountORName(false);
                            break;
                        case 2:
                            AddCustomerButton.setVisible(true);
                            EditCustomerButton.setVisible(true);
                            DH = DBC.getAllCustomer_SortByName();
                            break;
                        case 3:

                            DH = DBC.getAllCheqsMadeByEmployeeOnThisDay(ME._id_employee);
                            break;
                        case 4:

                            DH = DBC.getAllStoreProductsPromoOrNot_SortedByCountOrByName(true, true);
                            break;
                        case 5:

                            DH = DBC.getAllStoreProductsPromoOrNot_SortedByCountOrByName(true, false);
                            break;
                        case 6:

                            DH = DBC.getAllStoreProductsPromoOrNot_SortedByCountOrByName(false, true);
                            break;
                        case 7:

                            DH = DBC.getAllStoreProductsPromoOrNot_SortedByCountOrByName(false, false);
                            break;
                        case 8:

                            DH = DBC.getStoreProductsByName(ProductNameField.getText());
                            break;
                        case 9:

                            DH = DBC.getAllProductsByCategory_nameORnumber(null, get_categoryComboBoxItem());
                            break;
                        case 10:
                            String custSurname = SurnameField.getText();
                            if(custSurname.length() == 0){
                                custSurname = null;
                            }
                            String custName = NameField.getText();
                            if(custName.length() == 0){
                                custName = null;
                            }
                            String custPatronymic = PatronimicField.getText();
                            if(custPatronymic.length() == 0){
                                custPatronymic = null;
                            }
                            DH = DBC.getAllCustomerByName(custSurname, custName, custPatronymic);
                            break;
                        case 11:

                            String date1 = FromDateField.getText();
                            String date2 = ToDateField.getText();
                            Utils.CheckDate(date1);
                            Utils.CheckDate(date2);
                            DH = DBC.getAllCheqsMadeByEmployeeORallInSpecificPeriodOfTime(ME._id_employee, date1, date2);
                            break;
                        case 12:
                            Integer checknum = null;
                            try {
                                checknum = Integer.parseInt(CheckNumField.getText());
                            }
                            catch (Exception EE){
                                throw new InvalidValueException();
                            }
                            //TODO
                            DH = DBC.getAllLinesFromCheq(checknum);
                            break;
                        default:
                    }
                    TWO<Object[], Object[][]> data = null;
                    data = DH.ConvertToSwingTableData();
                    _NETM.setDataVector(data._second, data._first);
                    _showingTable = _chosenTable;
                }
                catch (InvalidValueException ex){
                    JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                catch (InvalidDateFormatException ex) {
                    JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }


            }
        });

        AddCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                custForCashier AE = new custForCashier(THIS, true, null, null);
                AE.setVisible(true);
            }
        });
        EditCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                if(row != -1){
                    Integer id = Integer.valueOf((String) table1.getModel().getValueAt(row, 0));
                    Customer_CardData data = DBC.getCustomerById(id);
                    custForCashier AE = new custForCashier(THIS, true, data, id);
                    AE.setVisible(true);
                }
            }
        });


        FindStoreProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FindStoreProductByUPC LF = new FindStoreProductByUPC(THIS,true);
                LF.setVisible(true);
            }
        });
        AboutMeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AboutMe LF = new AboutMe(THIS,true);
                LF.setVisible(true);
            }
        });
        LogoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DBC.disconnect();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                try {
                    DBC.disconnect();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                LoginForm LF = new LoginForm();
                LF.setVisible(true);
                try {
                    if(_cheqId != null){
                        DBC.EndMakingCheq(_cheqId, false);
                        _cheqId = null;
                        updateCreatedCheq();
                    }
                } catch (CantEditException ex) {
                    JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (CantDeleteException ex) {
                    JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                dispose();
            }
        });


        AddFromListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(_cheqId == null){
                    JOptionPane.showMessageDialog(new JFrame(), "Create blank check first", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int row = table1.getSelectedRow();
                if(row != -1){
                    Integer upc = Integer.valueOf((String) table1.getModel().getValueAt(row, 0));
                    AskNumber AN = new AskNumber(THIS, true, upc, _cheqId);
                    AN.setVisible(true);
                }

            }
        });
        AddByUPC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(_cheqId == null){
                    JOptionPane.showMessageDialog(new JFrame(), "Create blank check first", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                AskNumberAndUPC AN = new AskNumberAndUPC(THIS,true, _cheqId);
                AN.setVisible(true);
            }
        });
        DeleteFromListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table3.getSelectedRow();
                if(row != -1){
                    Integer upc = Integer.valueOf((String) table3.getModel().getValueAt(row, 0));
                    try {
                        DBC.DeleteSale(_cheqId, upc);
                        updateCreatedCheq();
                    } catch (CantDeleteException ex) {
                        JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });
        CreateBlankCheckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(_cheqId == null){
                    try {
                        _cheqId = DBC.CreateBlankCheq(ME._id_employee);
                        updateCreatedCheq();
                    } catch (CantCreateException ex) {
                        JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(new JFrame(), "End current check first", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        PrintCheckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(_cheqId == null){
                    JOptionPane.showMessageDialog(new JFrame(), "Create blank check first", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    if(_cheqId != null){
                        DBC.EndMakingCheq(_cheqId, true);
                        _cheqId = null;
                        updateCreatedCheq();
                    }
                } catch (CantEditException ex) {
                    JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (CantDeleteException ex) {
                    JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        CancelCheckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(_cheqId == null){
                    JOptionPane.showMessageDialog(new JFrame(), "Create blank check first", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    if(_cheqId != null){
                        DBC.EndMakingCheq(_cheqId, false);
                        _cheqId = null;
                        updateCreatedCheq();
                    }
                } catch (CantEditException ex) {
                    JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (CantDeleteException ex) {
                    JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        LinkCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(_cheqId == null){
                    JOptionPane.showMessageDialog(new JFrame(), "Create blank check first", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                LinkCustomerById AN = new LinkCustomerById(THIS,true, _cheqId);
                AN.setVisible(true);
            }
        });
        AboutMe aboutMe = new AboutMe(this, true);
        aboutMe.setVisible(true);
    }

    public void productNameShowHide(boolean show){
        ProductNameLabel.setVisible(show);
        ProductNameField.setVisible(show);
    }

    public DataHolder _categoryComboBoxData = null;
    public Integer get_categoryComboBoxItem(){
        Integer index = CategoryComboBox.getSelectedIndex();
        if(index == -1){
            return null;
        }
        return  (Integer) _categoryComboBoxData._data.get(index).get(0);
    }
    public void categoryShowHide(boolean show){
        if(show){
            DefaultComboBoxModel tmpModel = new DefaultComboBoxModel();
            _categoryComboBoxData = DBC.getAllCategory_SortByName();
            for (LinkedList<Object> datum : _categoryComboBoxData._data) {
                tmpModel.addElement(datum.get(0).toString()+" "+datum.get(1));
            }
            CategoryComboBox.setModel(tmpModel);
        }
        CategoryLabel.setVisible(show);
        CategoryComboBox.setVisible(show);
    }

    public void custNameShowHide(boolean show){
        SurnameLabel.setVisible(show);
        SurnameField.setVisible(show);
        NameLabel.setVisible(show);
        NameField.setVisible(show);
        PatronimicLabel.setVisible(show);
        PatronimicField.setVisible(show);
    }

    public void dateShowHide(boolean show){
        FromDateLabel.setVisible(show);
        ToDateLabel.setVisible(show);
        FromDateField.setVisible(show);
        ToDateField.setVisible(show);
    }

    public void checkNumShowHide(boolean show){
        CheckNumLabel.setVisible(show);
        CheckNumField.setVisible(show);
    }

    public void hideLeftAdditional(){
        productNameShowHide(false);
        categoryShowHide(false);
        custNameShowHide(false);
        dateShowHide(false);
        checkNumShowHide(false);
    }

    public void InitTopButtons(){
        AddCustomerButton.setVisible(false);
        EditCustomerButton.setVisible(false);
        //
        FindStoreProductButton.setVisible(true);
        AboutMeButton.setVisible(true);
    }

    public void InitBottomButtons(){
        AddFromListButton.setVisible(false);
    }

    public void updateCreatedCheq(){
        TWO<Object[], Object[][]> cheq = DBC.getCheqByNumber(_cheqId).ConvertToSwingTableData();
        TWO<Object[], Object[][]> cheqLines = DBC.getAllLinesFromCheq(_cheqId).ConvertToSwingTableData();
        _NETM_Top.setDataVector(cheq._second, cheq._first);
        _NETM_Centr.setDataVector(cheqLines._second, cheqLines._first);
    }

}
