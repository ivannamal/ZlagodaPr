package ZLAGODA_project.Front;

import ZLAGODA_project.DataBase.DataBaseController;
import ZLAGODA_project.DataBase.DataClasses.*;
import ZLAGODA_project.DataBase.DataHolder;
import ZLAGODA_project.DataBase.Exeptions.CantDeleteException;
import ZLAGODA_project.DataBase.Exeptions.InvalidDateFormatException;
import ZLAGODA_project.DataBase.Exeptions.InvalidValueException;
import ZLAGODA_project.DataBase.TWO;
import ZLAGODA_project.DataBase.Utils;
import ZLAGODA_project.Front.Small.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.LinkedList;

public class ManagerMenu extends JFrame {
    private JPanel MainPanel;
    private JPanel RightPanel;
    private JPanel MidDataPanel;
    private JPanel BottomPanel;
    private JPanel TopPanel;
    private JPanel LeftPanel;
    private JComboBox ShowStdTablesManager;
    private JPanel bottomright;
    private JPanel bottomleft;
    private JButton LogoutButton;
    private JTable table1;
    private JButton showButton;
    private JTextField PercentNumField;
    private JLabel PercentLabel;
    private JComboBox CategoryComboBox;
    private JLabel CategoryLabel;
    private JComboBox CashiersComboBox;
    private JLabel CashiersLabel;
    private JTextField FromDateField;
    private JTextField ToDateField;
    private JLabel FromDateLabel;
    private JLabel ToDateLabel;
    private JLabel TablesLabel;
    private JButton ButtonToShowGoods;
    private JButton AddEmployeeButton;
    private JButton AddCustomerButton;
    private JButton AddCategoryButton;
    private JButton AddProductButton;
    private JButton AddStoreProductButton;
    private JButton EditEmployeeButton;
    private JButton EditCustomerButton;
    private JButton EditCategoryButton;
    private JButton EditProductButton;
    private JButton EditStoreProductButton;
    private JButton DeleteEmployeeButton;
    private JButton DeleteCustomerButton;
    private JButton DeleteCategoryButton;
    private JButton DeleteProductButton;
    private JButton DeleteStoreProductButton;
    private JButton DeleteCheckButton;
    private JButton AddLoginButton;
    private JButton EditLoginButton;
    private JButton DeleteLoginButton;
    private JButton PrintZvitButton;
    private JButton FindTelephoneAndAdressButton;
    private JButton FindStoreProductButton;
    private JButton CalculateSumOfGoodsOfCashierButton;
    private JButton CalculateSumOfGoodsButton;
    private JButton CalculateCountOfGoodsButton;
    private JComboBox CustomerComboBox;
    private JLabel CustomerLabel;

    public NotEditableTableModel _NETM = null;
    public int _showingTable = 0;
    public int _chosenTable = 0;
    public DataBaseController DBC = null;

    public EmployeeData ME = null;

    public ManagerMenu THIS = null;
    public ManagerMenu(EmployeeData employee, DataBaseController dbc){
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

        TWO<Object[], Object[][]> basicTableData = DBC.getAllEmployee_SortByName().ConvertToSwingTableData();
        _NETM = new NotEditableTableModel(basicTableData._second, basicTableData._first);
        table1.setModel(_NETM);


        //
        ShowStdTablesManager.addItem("Logins"); //0
        ShowStdTablesManager.addItem("Employee"); //1
        ShowStdTablesManager.addItem("Cashiers"); //2
        ShowStdTablesManager.addItem("Customer"); //3
        ShowStdTablesManager.addItem("Category"); //4
        ShowStdTablesManager.addItem("Product"); //5
        ShowStdTablesManager.addItem("StoreProdByCount"); //6
        ShowStdTablesManager.addItem("StoreProdByName"); //7
        ShowStdTablesManager.addItem("UniqueCustForProd"); //8
        ShowStdTablesManager.addItem("UniqueCatForCust"); //9
        ShowStdTablesManager.addItem("PromProdByCount"); //10
        ShowStdTablesManager.addItem("PromProdByName"); //11
        ShowStdTablesManager.addItem("NonPromProdByCount"); //12
        ShowStdTablesManager.addItem("NonPromProdByName"); //13
        //with params
        ShowStdTablesManager.addItem("CustomersWithSpecificPercent"); //14
        //textfield for num
        ShowStdTablesManager.addItem("ProductsInCategory"); //15
        //combobox with all category
        ShowStdTablesManager.addItem("ChecksFromSpecificCashierInPeriodOfTime"); //16
        //combobox with all cashiers
        //Date1
        //Date2
        //button to show goods
        ShowStdTablesManager.addItem("ChecksFromAllCashierInPeriodOfTime"); //17
            //--combobox with all cashiers
        //Date1
        //Date2
        //button to show goods
        ShowStdTablesManager.addItem("FindAllCustomersWhoNeverBoughtProductsThatDidtBoughtGivenCustomer"); //18
        //--combobox with all customers

        hideLeftAdditional();
        InitTopButtons();

        AddEmployeeButton.setVisible(true);
        EditEmployeeButton.setVisible(true);
        DeleteEmployeeButton.setVisible(true);






        ShowStdTablesManager.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _chosenTable = ShowStdTablesManager.getSelectedIndex();
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

                        break;
                    case 9:

                        break;
                    case 10:

                        break;
                    case 11:

                        break;
                    case 12:

                        break;
                    case 13:

                        break;
                    case 14:

                        percentShowHide(true);
                        break;
                    case 15:
                        categoryShowHide(true);
                        break;
                    case 16:
                        cashierShowHide(true);
                        dateShowHide(true);
                        buttonShowGoodsShowHide(true);
                        break;
                    case 17:
                        dateShowHide(true);
                        buttonShowGoodsShowHide(true);
                        break;
                    case 18:
                        customerShowHide(true);
                        break;
                    default:
                }


            }
        });
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //SHOW

                try{
                    DataHolder DH = null;
                    InitTopButtons();
                    switch (_chosenTable){
                        case 0:
                            AddLoginButton.setVisible(true);
                            EditLoginButton.setVisible(true);
                            DeleteLoginButton.setVisible(true);
                            DH = DBC.getAllLogins();
                            break;
                        case 1:
                            AddEmployeeButton.setVisible(true);
                            EditEmployeeButton.setVisible(true);
                            DeleteEmployeeButton.setVisible(true);
                            DH = DBC.getAllEmployee_SortByName();
                            break;
                        case 2:
                            AddEmployeeButton.setVisible(true);
                            EditEmployeeButton.setVisible(true);
                            DeleteEmployeeButton.setVisible(true);
                            CalculateSumOfGoodsOfCashierButton.setVisible(true);
                            DH = DBC.getAllCashiers_SortByName();
                            break;
                        case 3:
                            AddCustomerButton.setVisible(true);
                            EditCustomerButton.setVisible(true);
                            DeleteCustomerButton.setVisible(true);
                            DH = DBC.getAllCustomer_SortByName();
                            break;
                        case 4:
                            AddCategoryButton.setVisible(true);
                            EditCategoryButton.setVisible(true);
                            DeleteCategoryButton.setVisible(true);
                            DH = DBC.getAllCategory_SortByName();
                            break;
                        case 5:
                            AddProductButton.setVisible(true);
                            EditProductButton.setVisible(true);
                            DeleteProductButton.setVisible(true);
                            CalculateCountOfGoodsButton.setVisible(true);
                            DH = DBC.getAllProducts_SortByName();
                            break;
                        case 6:
                            AddStoreProductButton.setVisible(true);
                            EditStoreProductButton.setVisible(true);
                            DeleteStoreProductButton.setVisible(true);
                            DH = DBC.getAllStoreProducts_SortByCountORName(true);
                            break;
                        case 7:
                            AddStoreProductButton.setVisible(true);
                            EditStoreProductButton.setVisible(true);
                            DeleteStoreProductButton.setVisible(true);
                            DH = DBC.getAllStoreProducts_SortByCountORName(false);
                            break;
                        case 8:
                            DH = DBC.countForAllProductUniqueCustomers();
                            break;
                        case 9:
                            DH = DBC.countForAllCustomersFromHowManyCategoryTheyMadePurchase();
                            break;
                        case 10:
                            AddStoreProductButton.setVisible(true);
                            EditStoreProductButton.setVisible(true);
                            DeleteStoreProductButton.setVisible(true);
                            DH = DBC.getAllStoreProductsPromoOrNot_SortedByCountOrByName(true, true);
                            break;
                        case 11:
                            AddStoreProductButton.setVisible(true);
                            EditStoreProductButton.setVisible(true);
                            DeleteStoreProductButton.setVisible(true);
                            DH = DBC.getAllStoreProductsPromoOrNot_SortedByCountOrByName(true, false);
                            break;
                        case 12:
                            AddStoreProductButton.setVisible(true);
                            EditStoreProductButton.setVisible(true);
                            DeleteStoreProductButton.setVisible(true);
                            DH = DBC.getAllStoreProductsPromoOrNot_SortedByCountOrByName(false, true);
                            break;
                        case 13:
                            AddStoreProductButton.setVisible(true);
                            EditStoreProductButton.setVisible(true);
                            DeleteStoreProductButton.setVisible(true);
                            DH = DBC.getAllStoreProductsPromoOrNot_SortedByCountOrByName(false, false);
                            break;
                        case 14:

                            Double percent = null;
                            try {
                                percent = Double.valueOf(PercentNumField.getText());
                            }
                            catch (Exception ee){
                                throw new InvalidValueException();
                            }
                            AddEmployeeButton.setVisible(true);
                            EditEmployeeButton.setVisible(true);
                            DeleteEmployeeButton.setVisible(true);
                            DH = DBC.getAllCustomerByPercent(percent);
                            break;
                        case 15:
                            DH = DBC.getAllProductsByCategory_nameORnumber(null, get_categoryComboBoxItem());
                            break;
                        case 16:
                            String date1 = FromDateField.getText();
                            String date2 = ToDateField.getText();
                            Utils.CheckDate(date1);
                            Utils.CheckDate(date2);
                            Integer cashierIndex = get_cashierComboBoxItem();
                            if(cashierIndex == null){
                                throw new InvalidValueException();
                            }
                            DH = DBC.getAllCheqsMadeByEmployeeORallInSpecificPeriodOfTime(cashierIndex, date1, date2);
                            break;
                        case 17:
                            String date11 = FromDateField.getText();
                            String date22 = ToDateField.getText();
                            Utils.CheckDate(date11);
                            Utils.CheckDate(date22);
                            DH = DBC.getAllCheqsMadeByEmployeeORallInSpecificPeriodOfTime(null, date11, date22);
                            break;
                        case 18:

                            DH = DBC.getAllCustomersThatDidntBuyProductsThatBuyGivenCustomer(get_customerComboBoxItem());
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
        ButtonToShowGoods.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //SHOW goods
                try{
                    DataHolder DH = null;
                    switch (_chosenTable){
                        case 16:
                            String date1 = FromDateField.getText();
                            String date2 = ToDateField.getText();
                            Utils.CheckDate(date1);
                            Utils.CheckDate(date2);
                            Integer cashierIndex = get_cashierComboBoxItem();
                            if(cashierIndex == null){
                                throw new InvalidValueException();
                            }
                            DH = DBC.getAllCheqsLineMadeByEmployeeInSpecificPeriodOfTime(cashierIndex, date1, date2);
                            break;
                        case 17:
                            String date11 = FromDateField.getText();
                            String date22 = ToDateField.getText();
                            Utils.CheckDate(date11);
                            Utils.CheckDate(date22);
                            DH = DBC.getAllCheqsLineMadeByEmployeeInSpecificPeriodOfTime(null, date11, date22);
                            break;
                        default:
                    }
                    TWO<Object[], Object[][]> data = null;
                    data = DH.ConvertToSwingTableData();
                    _NETM.setDataVector(data._second, data._first);
                    _showingTable = _chosenTable;
                }
                catch (InvalidValueException ex) {
                    JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (InvalidDateFormatException ex) {
                    JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        AddEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddEmployee AE = new AddEmployee(THIS, true, null, null);
                AE.setVisible(true);
            }
        });

        AddCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddCustomer AE = new AddCustomer(THIS, true, null, null);
                AE.setVisible(true);
            }
        });
        AddCategoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddCategory AE = new AddCategory(THIS, true, null, null);
                AE.setVisible(true);
            }
        });
        AddProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddProduct AE = new AddProduct(THIS, true, null, null);
                AE.setVisible(true);
            }
        });
        AddStoreProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddStoreProduct AE = new AddStoreProduct(THIS, true, null, null);
                AE.setVisible(true);
            }
        });


        ///////////////
        EditEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                if(row != -1){
                    Integer id = Integer.valueOf((String) table1.getModel().getValueAt(row, 0));
                    EmployeeData data = DBC.getEmployeeById(id);
                    AddEmployee AE = new AddEmployee(THIS, true, data, id);
                    AE.setVisible(true);
                }
            }
        });
        EditCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                if(row != -1){
                    Integer id = Integer.valueOf((String) table1.getModel().getValueAt(row, 0));
                    Customer_CardData data = DBC.getCustomerById(id);
                    AddCustomer AE = new AddCustomer(THIS, true, data, id);
                    AE.setVisible(true);
                }
            }
        });
        EditCategoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                if(row != -1){
                    Integer id = Integer.valueOf((String) table1.getModel().getValueAt(row, 0));
                    CategoryData data = DBC.getCategoryById(id);
                    AddCategory AE = new AddCategory(THIS, true, data, id);
                    AE.setVisible(true);
                }
            }
        });
        EditProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                if(row != -1){
                    Integer id = Integer.valueOf((String) table1.getModel().getValueAt(row, 0));
                    ProductData data = DBC.getProductById(id);
                    AddProduct AE = new AddProduct(THIS, true, data, id);
                    AE.setVisible(true);
                }
            }
        });
        EditStoreProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                if(row != -1){
                    Integer id = Integer.valueOf((String) table1.getModel().getValueAt(row, 0));
                    Store_ProductData data = DBC.getStoreProductById(id);
                    AddStoreProduct AE = new AddStoreProduct(THIS, true, data, id);
                    AE.setVisible(true);
                }
            }
        });
        DeleteEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                if(row != -1){
                    Integer id = Integer.valueOf((String) table1.getModel().getValueAt(row, 0));
                    try {
                        DBC.deleteEmployee(id);
                    } catch (CantDeleteException ex) {
                        JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        DeleteCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                if(row != -1){
                    Integer id = Integer.valueOf((String) table1.getModel().getValueAt(row, 0));
                    try {
                        DBC.deleteCustomer_Card(id);
                    } catch (CantDeleteException ex) {
                        JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        DeleteCategoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                if(row != -1){
                    Integer id = Integer.valueOf((String) table1.getModel().getValueAt(row, 0));
                    try {
                        DBC.deleteCategory(id);
                    } catch (CantDeleteException ex) {
                        JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        DeleteProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                if(row != -1){
                    Integer id = Integer.valueOf((String) table1.getModel().getValueAt(row, 0));
                    try {
                        DBC.deleteProduct(id);
                    } catch (CantDeleteException ex) {
                        JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        DeleteStoreProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                if(row != -1){
                    Integer id = Integer.valueOf((String) table1.getModel().getValueAt(row, 0));
                    try {
                        DBC.deleteStore_Product(id);
                    } catch (CantDeleteException ex) {
                        JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        DeleteCheckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                if(row != -1){
                    Integer id = Integer.valueOf((String) table1.getModel().getValueAt(row, 0));
                    try {
                        DBC.deleteCheq(id);
                    } catch (CantDeleteException ex) {
                        JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        AddLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddLogin AE = new AddLogin(THIS, true, null, null, null);
                AE.setVisible(true);
            }
        });


        EditLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                if(row != -1){
                    String login = (String) table1.getModel().getValueAt(row, 0);
                    Integer employeeId = Integer.valueOf((String) table1.getModel().getValueAt(row, 2));
                    AddLogin AE = new AddLogin(THIS, true, login, login, employeeId);
                    AE.setVisible(true);
                }
            }
        });


        DeleteLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                if(row != -1){
                    String id = (String) table1.getModel().getValueAt(row, 0);
                    try {
                        DBC.deleteLogin(id);
                    } catch (CantDeleteException ex) {
                        JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        PrintZvitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ZvitWindow PZB = new ZvitWindow(THIS, true);
                PZB.setVisible(true);
            }
        });

        FindTelephoneAndAdressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FindPhoneAndAdress PZB = new FindPhoneAndAdress(THIS, true);
                PZB.setVisible(true);
            }
        });
        FindStoreProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                managerFindStoreProductByUPC PZB = new managerFindStoreProductByUPC(THIS, true);
                PZB.setVisible(true);
            }
        });
        CalculateSumOfGoodsOfCashierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                if(row != -1){
                    Integer employeeId = Integer.valueOf((String) table1.getModel().getValueAt(row, 0));
                    AskPeriodOfTimeFirst AE = new AskPeriodOfTimeFirst(THIS, true, employeeId);
                    AE.setVisible(true);
                }
            }
        });
        CalculateSumOfGoodsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AskPeriodOfTimeSecond AE = new AskPeriodOfTimeSecond(THIS, true);
                AE.setVisible(true);
            }
        });
        CalculateCountOfGoodsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                if(row != -1){
                    Integer prodId = Integer.valueOf((String) table1.getModel().getValueAt(row, 0));
                    AskPeriodOfTimeThird AE = new AskPeriodOfTimeThird(THIS, true, prodId);
                    AE.setVisible(true);
                }
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
                LoginForm LF = new LoginForm();
                LF.setVisible(true);
                dispose();
            }
        });
    }

    public void hideLeftAdditional(){
        percentShowHide(false);
        categoryShowHide(false);
        cashierShowHide(false);
        dateShowHide(false);
        buttonShowGoodsShowHide(false);
        customerShowHide(false);
    }

    public void InitTopButtons(){
        AddEmployeeButton.setVisible(false);
        AddCustomerButton.setVisible(false);
        AddCategoryButton.setVisible(false);
        AddProductButton.setVisible(false);
        AddStoreProductButton.setVisible(false);
        EditEmployeeButton.setVisible(false);
        EditCustomerButton.setVisible(false);
        EditCategoryButton.setVisible(false);
        EditProductButton.setVisible(false);
        EditStoreProductButton.setVisible(false);
        DeleteEmployeeButton.setVisible(false);
        DeleteCustomerButton.setVisible(false);
        DeleteCategoryButton.setVisible(false);
        DeleteProductButton.setVisible(false);
        DeleteStoreProductButton.setVisible(false);
        DeleteCheckButton.setVisible(false);
        AddLoginButton.setVisible(false);
        EditLoginButton.setVisible(false);
        DeleteLoginButton.setVisible(false);
        //
        PrintZvitButton.setVisible(true);
        FindTelephoneAndAdressButton.setVisible(true);
        FindStoreProductButton.setVisible(true);
        //
        CalculateSumOfGoodsOfCashierButton.setVisible(false);
        CalculateSumOfGoodsButton.setVisible(true);
        CalculateCountOfGoodsButton.setVisible(false);


    }



    public void percentShowHide(boolean show){
        PercentLabel.setVisible(show);
        PercentNumField.setVisible(show);
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

    public DataHolder _cashierComboBoxData = null;
    public Integer get_cashierComboBoxItem(){
        Integer index = CashiersComboBox.getSelectedIndex();
        if(index == -1){
            return null;
        }
        return  (Integer) _cashierComboBoxData._data.get(index).get(0);
    }
    public void cashierShowHide(boolean show){
        if(show){
            DefaultComboBoxModel tmpModel = new DefaultComboBoxModel();
            _cashierComboBoxData = DBC.getAllCategory_SortByName();
            for (LinkedList<Object> datum : _cashierComboBoxData._data) {
                tmpModel.addElement(datum.get(0).toString()+" "+datum.get(1)+" "+datum.get(2)+" "+datum.get(3));
            }
            CashiersComboBox.setModel(tmpModel);
        }

        CashiersLabel.setVisible(show);
        CashiersComboBox.setVisible(show);
    }
    public void dateShowHide(boolean show){
        FromDateLabel.setVisible(show);
        ToDateLabel.setVisible(show);
        FromDateField.setVisible(show);
        ToDateField.setVisible(show);
    }
    public void buttonShowGoodsShowHide(boolean show){
        ButtonToShowGoods.setVisible(show);
    }


    public DataHolder _customerComboBoxData = null;
    public Integer get_customerComboBoxItem(){
        Integer index = CustomerComboBox.getSelectedIndex();
        if(index == -1){
            return null;
        }
        return  (Integer) _customerComboBoxData._data.get(index).get(0);
    }
    public void customerShowHide(boolean show){
        if(show){
            DefaultComboBoxModel tmpModel = new DefaultComboBoxModel();
            _customerComboBoxData = DBC.getAllCustomer_SortByName();
            for (LinkedList<Object> datum : _customerComboBoxData._data) {
                tmpModel.addElement(datum.get(0).toString()+" "+datum.get(1)+" "+datum.get(2)+" "+datum.get(3));
            }
            CustomerComboBox.setModel(tmpModel);
        }
        CustomerLabel.setVisible(show);
        CustomerComboBox.setVisible(show);
    }


}
