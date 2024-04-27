package ZLAGODA_project.DataBase;
import java.sql.*;
import ZLAGODA_project.DataBase.DataClasses.*;
import ZLAGODA_project.DataBase.Exeptions.*;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;


import javax.xml.crypto.Data;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.sql.*;
import java.util.LinkedList;

import static ZLAGODA_project.DataBase.Utils.CheckDate;

public class DataBaseController {

    protected String _nameOfDB = null;
    protected Connection _conn = null;

    public DataBaseController(String nameOfDB){
        this._nameOfDB = nameOfDB;
        connect(); //TODO change place
        basicInit();
        //_conn.setAutoCommit(false);
    }

    private void updateConn(Connection conn) throws SQLException {
        if(this._conn != null){
            this._conn.close();
        }
        this._conn = conn;
    }

    public Connection connect() {
        String url = "jdbc:sqlite://C://Users//іванна//Downloads//ZLAGODA//ZLAGODA//ZLAGODA.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            updateConn(conn);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception ee){
            System.out.println(ee.getMessage());
        }
        return conn;
    }

    public void disconnect() throws SQLException {
        if(this._conn != null){
            this._conn.close();
        }
    }

/*    public static String sha256(final String base) {
        try{
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(base.getBytes("UTF-8"));
            final StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                final String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }*/

    public TWO<String, Integer> tryLogin(String login, String password){
        TWO<String, Integer> Res = null;
        String sql = "SELECT empl_role, id_employee\n" +
                "FROM Employee\n" +
                "WHERE id_employee = (\n" +
                "SELECT id_employee\n" +
                "FROM Logins\n" +
                "WHERE empl_login = ? \n" +
                "AND empl_pass = ?)";
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            pstmt.setString(1, login);
            pstmt.setString(2, (password));

            ResultSet rs  = pstmt.executeQuery();

            while (rs.next()) {
                Res = new TWO<>();
                Res._first = rs.getString("empl_role");
                Res._second = rs.getInt("id_employee");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Res;
    }

    public void basicInit() {
        String sql0 = "PRAGMA foreign_keys = ON;";
        String sql1 = "CREATE TABLE IF NOT EXISTS Employee (\n" +
                "\tid_employee\t\tINTEGER PRIMARY KEY,\n" +
                "\templ_surname\tVARCHAR(50) NOT NULL,\n" +
                "\templ_name\t\tVARCHAR(50) NOT NULL,\n" +
                "\templ_patronymic\tVARCHAR(50) ,\n" +
                "\templ_role\t\tVARCHAR(10) NOT NULL,\n" +
                "\tsalary\t\t\tDECIMAL(13,4) NOT NULL,\n" +
                "\tdate_of_birth\tDATE NOT NULL,\n" +
                "\tdata_of_start\tDATE NOT NULL,\n" +
                "\tphone_number\tVARCHAR(13) NOT NULL,\n" +
                "\tcity\t\t\tVARCHAR(50) NOT NULL,\n" +
                "\tstreet\t\t\tVARCHAR(50) NOT NULL,\n" +
                "\tzip_code\t\tVARCHAR(9) NOT NULL\n" +
                ");";
        String sql2 = "CREATE TABLE IF NOT EXISTS Logins (\n" +
                "\templ_login\t\tVARCHAR(50) PRIMARY KEY,\n" +
                "\templ_pass\t\tVARCHAR(50) NOT NULL, -- will be sha256\n" +
                "\tid_employee\t\tINTEGER NOT NULL,\n" +
                "\tFOREIGN KEY (id_employee)\n" +
                "\t\tREFERENCES Employee (id_employee) \n" +
                "\t\t\tON UPDATE CASCADE\n" +
                "\t\t\tON DELETE CASCADE\n" +
                ");";
        String sql3 = "INSERT OR IGNORE INTO Employee (\n" +
                "\tid_employee,\n" +
                "\templ_surname,\n" +
                "\templ_name,\n" +
                "\templ_patronymic,\n" +
                "\templ_role,\n" +
                "\tsalary,\n" +
                "\tdate_of_birth,\n" +
                "\tdata_of_start,\n" +
                "\tphone_number,\n" +
                "\tcity,\n" +
                "\tstreet,\n" +
                "\tzip_code\n" +
                ")\n" +
                "VALUES (0,'overlord','overlord','overlord','manager',0,'1111-11-11','2222-11-11','?','?','?','?');";
        String sql4 = "INSERT OR IGNORE INTO Logins (\n" +
                "\templ_login,\n" +
                "\templ_pass,\n" +
                "\tid_employee\n" +
                ")\n" +
                "VALUES ('overlord','77aae185203edc6357676db95caa25d0f398d402c1723e6a7b42cfe8d2967f2e', 0);";
        String sql5 = "CREATE TABLE IF NOT EXISTS Customer_Card (\n" +
                "\tcard_number\t\tINTEGER PRIMARY KEY,\n" +
                "\tcust_surname\tVARCHAR(50) NOT NULL,\n" +
                "\tcust_name\t\tVARCHAR(50) NOT NULL,\n" +
                "\tcust_patronymic\tVARCHAR(50),\n" +
                "\tphone_number\tVARCHAR(13) NOT NULL,\n" +
                "\tcity\t\t\tVARCHAR(50),\n" +
                "\tstreet\t\t\tVARCHAR(50),\n" +
                "\tzip_code\t\tVARCHAR(9),\n" +
                "\tpercent\t\t\tDECIMAL NOT NULL\n" +
                ");";
        String sql6 = "CREATE TABLE IF NOT EXISTS Cheq (\n" +
                "\tcheck_number\tINTEGER PRIMARY KEY,\n" +
                "\tid_employee\t\tINTEGER NOT NULL,\n" +
                "\tcard_number\t\tINTEGER,\n" +
                "\tprint_date\t\tDATE NOT NULL,\n" +
                "\tFOREIGN KEY (id_employee)\n" +
                "\t\tREFERENCES Employee (id_employee) \n" +
                "\t\t\tON UPDATE CASCADE\n" +
                "\t\t\tON DELETE NO ACTION,\n" +
                "\tFOREIGN KEY (card_number)\n" +
                "\t\tREFERENCES Customer_Card (card_number) \n" +
                "\t\t\tON UPDATE CASCADE\n" +
                "\t\t\tON DELETE NO ACTION\n" +
                ");";
        String sql7 = "CREATE TABLE IF NOT EXISTS Category (\n" +
                "\tcategory_number\t\tINTEGER PRIMARY KEY,\n" +
                "\tcategory_name\t\tVARCHAR(50) NOT NULL\n" +
                ");";
        String sql8 = "CREATE TABLE IF NOT EXISTS Product (\n" +
                "\tid_product\t\t\tINTEGER PRIMARY KEY,\n" +
                "\tcategory_number\t\tINTEGER NOT NULL,\n" +
                "\tproduct_name\t\tVARCHAR(50) NOT NULL,\n" +
                "\tcharacteristics\t\tVARCHAR(100) NOT NULL,\n" +
                "\tFOREIGN KEY (category_number)\n" +
                "\t\tREFERENCES Category (category_number) \n" +
                "\t\t\tON UPDATE CASCADE\n" +
                "\t\t\tON DELETE NO ACTION\n" +
                ");";
        String sql9 = "CREATE TABLE IF NOT EXISTS Store_Product_Data (\n" +
                "\tUPC\t\t\t\tINTEGER PRIMARY KEY,\n" +
                "\texpire_Date\t\tDATE NOT NULL,\n" +
                "\tid_product\t\tINTEGER NOT NULL,\n" +
                "\tselling_price\tDECIMAL(13,4) NOT NULL,\n" +
                "\tproducts_number\tINTEGER NOT NULL,\n" +
                "\tFOREIGN KEY (id_product)\n" +
                "\t\tREFERENCES Product (id_product) \n" +
                "\t\t\tON UPDATE CASCADE\n" +
                "\t\t\tON DELETE NO ACTION\n" +
                ");";
        String sql10 = "CREATE VIEW IF NOT EXISTS Store_Product (\n" +
                "UPC,\n" +
                "expire_Date,\n" +
                "id_product,\n" +
                "products_number,\n" +
                "promotional_product,\n" +
                "is_expired,\n" +
                "selling_price\n" +
                ")\n" +
                "AS\n" +
                "SELECT UPC, \n" +
                "expire_Date,\n" +
                "id_product,\n" +
                "products_number,\n" +
                "(IIF((JULIANDAY(expire_Date) - JULIANDAY(DATE())) <= 3,(TRUE),(FALSE))),\n" +
                "(IIF((JULIANDAY(expire_Date) - JULIANDAY(DATE())) < 0,(TRUE),(FALSE))),\n" +
                "IIF((IIF((JULIANDAY(expire_Date) - JULIANDAY(DATE())) <= 3,(TRUE),(FALSE))), (Store_Product_Data.selling_price * 0.8), (Store_Product_Data.selling_price))\n" +
                "FROM Store_Product_Data;";
        String sql11 = "CREATE TABLE IF NOT EXISTS Sale (\n" +
                "\tUPC\t\t\t\tINTEGER NOT NULL,\n" +
                "\tcheck_number\tINTEGER NOT NULL,\n" +
                "\tproduct_number\tINTEGER NOT NULL,\n" +
                "\tselling_price\tDECIMAL(13,4) NOT NULL,\n" +
                "\tPRIMARY KEY (UPC, check_number),\n" +
                "\tFOREIGN KEY (UPC)\n" +
                "\t\tREFERENCES Store_Product_Data (UPC) \n" +
                "\t\t\tON UPDATE CASCADE\n" +
                "\t\t\tON DELETE NO ACTION,\n" +
                "\tFOREIGN KEY (check_number)\n" +
                "\t\tREFERENCES Cheq (check_number) \n" +
                "\t\t\tON UPDATE CASCADE\n" +
                "\t\t\tON DELETE CASCADE\n" +
                ");";
        String sql12 = "CREATE VIEW IF NOT EXISTS Cheq_Print (\n" +
                "check_number,\n" +
                "id_employee,\n" +
                "empl_surname,\n" +
                "empl_name,\n" +
                "empl_patronymic,\n" +
                "card_number,\n" +
                "print_date,\n" +
                "sum_total,\n" +
                "price_reduction,\n" +
                "to_pay,\n" +
                "vat\n" +
                ")\n" +
                "AS\n" +
                "SELECT \n" +
                "Cheq.check_number,\n" +
                "Employee.id_employee,\n" +
                "Employee.empl_surname,\n" +
                "Employee.empl_name,\n" +
                "Employee.empl_patronymic,\n" +
                "Customer_Card.card_number,\n" +
                "Cheq.print_date,\n" +
                "(SELECT SUM(product_number * selling_price) FROM Sale WHERE Cheq.check_number = Sale.check_number),\n" +
                "(IIF((Customer_Card.card_number IS NULL),(0),(percent))),\n" +
                "(((SELECT SUM(product_number * selling_price) FROM Sale WHERE Cheq.check_number = Sale.check_number)*1.2)*(1-(IIF((Customer_Card.card_number IS NULL),(0),(percent))))),\n" +
                "(((SELECT SUM(product_number * selling_price) FROM Sale WHERE Cheq.check_number = Sale.check_number)*0.2*(1-(IIF((Customer_Card.card_number IS NULL),(0),(percent))))))\n" +
                "FROM Cheq\n" +
                "INNER JOIN Employee ON Cheq.id_employee = Employee.id_employee\n" +
                "LEFT JOIN Customer_Card ON Cheq.card_number = Customer_Card.card_number\n" +
                "ORDER BY print_date;";

        try (Statement stmt = this._conn.createStatement()) {
            stmt.execute(sql0);
            stmt.execute(sql1);
            stmt.execute(sql2);
            stmt.execute(sql3);
            stmt.execute(sql4);
            stmt.execute(sql5);
            stmt.execute(sql6);
            stmt.execute(sql7);
            stmt.execute(sql8);
            stmt.execute(sql9);
            stmt.execute(sql10);
            stmt.execute(sql11);
            stmt.execute(sql12);
            _conn.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /////////////////////

    public void createNewLogin(Integer id_employee, String Login, String password) throws CantCreateException {
        String sql = "INSERT OR IGNORE INTO Logins (\n" +
                "\templ_login,\n" +
                "\templ_pass,\n" +
                "\tid_employee\n" +
                ")\n" +
                "VALUES (?,?,?);";
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            pstmt.setString(1, Login);
            pstmt.setString(2, (password));
            pstmt.setInt(3, id_employee);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CantCreateException();
        }
    }
    public void createNewEmployee_AI(EmployeeData data, boolean AI) throws CantCreateException {
        String sql = "INSERT INTO Employee (\n" +
                "\t--id_employee,\n" +
                "\templ_surname,\n" +
                "\templ_name,\n" +
                "\templ_patronymic,\n" +
                "\templ_role,\n" +
                "\tsalary,\n" +
                "\tdate_of_birth,\n" +
                "\tdata_of_start,\n" +
                "\tphone_number,\n" +
                "\tcity,\n" +
                "\tstreet,\n" +
                "\tzip_code\n";
        if(AI){
            sql += ") VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        }
        else{
            sql += ",id_employee) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        }
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            //pstmt.setString(1, data._id_employee);
            pstmt.setString(1, data._empl_surname);
            pstmt.setString(2, data._empl_name);
            if(data._empl_patronymic == null){
                pstmt.setNull(3, Types.VARCHAR);
            }
            else{
                pstmt.setString(3, data._empl_patronymic);
            }

            pstmt.setString(4, data._empl_role);
            pstmt.setDouble(5, data._salary);
            pstmt.setString(6, data._date_of_birth.toString());
            pstmt.setString(7, data._data_of_start.toString());
            pstmt.setString(8, data._phone_number);
            pstmt.setString(9, data._city);
            pstmt.setString(10, data._street);
            pstmt.setString(11, data._zip_code);
            if(!AI){
                pstmt.setInt(12, data._id_employee);
            }
            pstmt.executeUpdate();

            //while (rs.next()) {
            //return rs.getString("empl_role");
            //}
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CantCreateException();
        }
        //return null;
    }
    public void createNewEmployeeAutoDate_AI(EmployeeData data, boolean AI) throws CantCreateException {
        String sql = "INSERT INTO Employee (\n" +
                "\t--id_employee,\n" +
                "\templ_surname,\n" +
                "\templ_name,\n" +
                "\templ_patronymic,\n" +
                "\templ_role,\n" +
                "\tsalary,\n" +
                "\tdate_of_birth,\n" +
                "\tdata_of_start,\n" +
                "\tphone_number,\n" +
                "\tcity,\n" +
                "\tstreet,\n" +
                "\tzip_code\n";
        if(AI){
            sql += ") VALUES (?,?,?,?,?,?,DATE(),?,?,?,?)";
        }
        else{
            sql += ",id_employee) VALUES (?,?,?,?,?,?,DATE(),?,?,?,?,?)";
        }
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            //pstmt.setInt(1, data._id_employee);
            pstmt.setString(1, data._empl_surname);
            pstmt.setString(2, data._empl_name);
            if(data._empl_patronymic == null){
                pstmt.setNull(3, Types.VARCHAR);
            }
            else{
                pstmt.setString(3, data._empl_patronymic);
            }
            pstmt.setString(4, data._empl_role);
            pstmt.setDouble(5, data._salary);
            pstmt.setString(6, data._date_of_birth.toString());
            //pstmt.setString(8, data._data_of_start.toString());
            pstmt.setString(7, data._phone_number);
            pstmt.setString(8, data._city);
            pstmt.setString(9, data._street);
            pstmt.setString(10, data._zip_code);
            if(!AI){
                pstmt.setInt(11, data._id_employee);
            }
            pstmt.executeUpdate();

            //while (rs.next()) {
            //return rs.getString("empl_role");
            //}
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CantCreateException();
        }
        //return null;
    }

    public void createNewCustomer_Card_AI(Customer_CardData data, boolean AI) throws CantCreateException {
        String sql = "INSERT INTO Customer_Card (\n" +
                "\t--card_number,\n" +
                "\tcust_surname,\n" +
                "\tcust_name,\n" +
                "\tcust_patronymic,\n" +
                "\tphone_number,\n" +
                "\tcity,\n" +
                "\tstreet,\n" +
                "\tzip_code,\n" +
                "\tpercent\n";
        if(AI){
            sql += ") VALUES (?,?,?,?,?,?,?,?)";
        }
        else{
            sql += ",card_number) VALUES (?,?,?,?,?,?,?,?,?)";
        }
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            //pstmt.setInt(1, data._card_number);
            pstmt.setString(1, data._cust_surname);
            pstmt.setString(2, data._cust_name);
            if(data._cust_patronymic == null){
                pstmt.setNull(3, Types.VARCHAR);
            }
            else{
                pstmt.setString(3, data._cust_patronymic);
            }

            pstmt.setString(4, data._phone_number);
            if(data._city == null){
                pstmt.setNull(5, Types.VARCHAR);
            }
            else{
                pstmt.setString(5, data._city);
            }
            if(data._street == null){
                pstmt.setNull(6, Types.VARCHAR);
            }
            else{
                pstmt.setString(6, data._street);
            }
            if(data._zip_code == null){
                pstmt.setNull(7, Types.VARCHAR);
            }
            else{
                pstmt.setString(7, data._zip_code);
            }
            pstmt.setDouble(8, data._percent);
            if(!AI){
                pstmt.setInt(9, data._card_number);
            }
            pstmt.executeUpdate();

            //while (rs.next()) {
            //return rs.getString("empl_role");
            //}
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CantCreateException();
        }
        //return null;
    }

    public void createNewCategory_AI(CategoryData data, boolean AI) throws CantCreateException {
        String sql = "INSERT INTO Category (\n" +
                "\t--category_number,\n" +
                "\tcategory_name\n";
        if(AI){
            sql += ") VALUES (?)";
        }
        else{
            sql += ",category_number) VALUES (?,?)";
        }
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            //pstmt.setInt(1, data._category_number);
            pstmt.setString(1, data._category_name);
            if(!AI){
                pstmt.setInt(2, data._category_number);
            }
            pstmt.executeUpdate();

            //while (rs.next()) {
            //return rs.getString("empl_role");
            //}
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CantCreateException();
        }
        //return null;
    }
    public void createNewProduct_AI(ProductData data, boolean AI) throws CantCreateException {
        String sql = "INSERT INTO Product (\n" +
                "\t--id_product,\n" +
                "\tcategory_number,\n" +
                "\tproduct_name,\n" +
                "\tcharacteristics\n";
        if(AI){
            sql += ") VALUES (?,?,?)";
        }
        else{
            sql += ",id_product) VALUES (?,?,?,?)";
        }
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            //pstmt.setInt(1, data._id_product);
            pstmt.setInt(1, data._category_number);
            pstmt.setString(2, data._product_name);
            pstmt.setString(3, data._characteristics);
            if(!AI){
                pstmt.setInt(4, data._id_product);
            }
            pstmt.executeUpdate();

            //while (rs.next()) {
            //return rs.getString("empl_role");
            //}
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CantCreateException();
        }
        //return null;
    }
    public void createNewStore_Product_AI(Store_ProductData data, boolean AI) throws CantCreateException {
        String sql = "INSERT INTO Store_Product_Data (\n" +
                "\t--UPC,\n" +
                "\texpire_Date,\n" +
                "\tid_product,\n" +
                "\tselling_price,\n" +
                "\tproducts_number\n";
        if(AI){
            sql += ") VALUES (?,?,?,?)";
        }
        else{
            sql += ",UPC) VALUES (?,?,?,?,?)";
        }
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            //pstmt.setInt(1, data._UPC);
            pstmt.setString(1, data._expire_Date.toString());
            pstmt.setInt(2, data._id_product);
            pstmt.setDouble(3, data._selling_price);
            pstmt.setInt(4, data._products_number);
            if(!AI){
                pstmt.setInt(5, data._UPC);
            }
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CantCreateException();
        }
    }

    /////////////////////

    public DataHolder getAllLogins(){
        DataHolder ResArr = new DataHolder();
        ResArr._columnNames.add("login");
        ResArr._columnNames.add("password");
        ResArr._columnNames.add("employee id");
        String sql = "SELECT empl_login,\n" +
                "empl_pass,\n" +
                "id_employee\n" +
                "FROM Logins\t\n" +
                "ORDER By id_employee";
        try (PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            //pstmt.setString(1, "cashier");

            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                LinkedList<Object> dataList = new LinkedList<>();
                dataList.add(rs.getString(1));
                dataList.add(rs.getString(2));
                dataList.add(rs.getInt(3));
                ResArr._data.add(dataList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResArr;

    }



    public void editLogin(String LoginToFind, Integer id_employee, String Login, String password) throws CantEditException {
        String sql = "UPDATE Logins SET\n" +
                "\templ_login = ?,\n";
        String sqlpass = "\templ_pass = ?,\n";
        String sqlend = "\tid_employee = ?\n" +
                "WHERE empl_login = ?";
        if(password == null){
            sql += sqlend;
        }
        else{
            sql += sqlpass;
            sql += sqlend;
        }
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            if(password == null){
                pstmt.setString(1, Login);
                //pstmt.setString(2, sha256(password));
                pstmt.setInt(2, id_employee);
                pstmt.setString(3, LoginToFind);
            }
            else{
                pstmt.setString(1, Login);
                pstmt.setString(2, (password));
                pstmt.setInt(3, id_employee);
                pstmt.setString(4, LoginToFind);

            }
            pstmt.executeUpdate();

            //while (rs.next()) {
            //return rs.getString("empl_role");
            //}
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CantEditException();
        }
        //return null;
    }

    public EmployeeData getEmployeeById(Integer id_employee){
        EmployeeData Res = null;
        String sql = "SELECT id_employee,\n" +
                "\templ_surname,\n" +
                "\templ_name,\n" +
                "\templ_patronymic,\n" +
                "\templ_role,\n" +
                "\tsalary,\n" +
                "\tdate_of_birth,\n" +
                "\tdata_of_start,\n" +
                "\tphone_number,\n" +
                "\tcity,\n" +
                "\tstreet,\n" +
                "\tzip_code\n" +
                "FROM Employee\n" +
                "WHERE id_employee = ?\n" +
                "ORDER BY empl_surname, empl_name, empl_patronymic";
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            pstmt.setInt(1, id_employee);

            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                Res = new EmployeeData(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDouble(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12)
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Res;
    }
    public void editEmployee(Integer id_employee, EmployeeData data) throws CantEditException {
        String sql = "UPDATE Employee SET\n" +
                "\tid_employee = ?,\n" +
                "\templ_surname = ?,\n" +
                "\templ_name = ?,\n" +
                "\templ_patronymic = ?,\n" +
                "\templ_role = ?,\n" +
                "\tsalary = ?,\n" +
                "\tdate_of_birth = ?,\n" +
                "\tdata_of_start = ?,\n" +
                "\tphone_number = ?,\n" +
                "\tcity = ?,\n" +
                "\tstreet = ?,\n" +
                "\tzip_code = ?\n" +
                "WHERE id_employee = ?";
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            pstmt.setInt(1, data._id_employee);
            pstmt.setString(2, data._empl_surname);
            pstmt.setString(3, data._empl_name);
            if(data._empl_patronymic == null){
                pstmt.setNull(4, Types.VARCHAR);
            }
            else{
                pstmt.setString(4, data._empl_patronymic);
            }
            pstmt.setString(5, data._empl_role);
            pstmt.setDouble(6, data._salary);
            pstmt.setString(7, data._date_of_birth.toString());
            pstmt.setString(8, data._data_of_start.toString());
            pstmt.setString(9, data._phone_number);
            pstmt.setString(10, data._city);
            pstmt.setString(11, data._street);
            pstmt.setString(12, data._zip_code);

            pstmt.setInt(13, id_employee);

            pstmt.executeUpdate();

            //while (rs.next()) {
            //return rs.getString("empl_role");
            //}
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CantEditException();
        }
        //return null;
    }

    public Customer_CardData getCustomerById(Integer card_number){
        Customer_CardData Res = null;
        String sql = "SELECT card_number,\n" +
                "\tcust_surname,\n" +
                "\tcust_name,\n" +
                "\tcust_patronymic,\n" +
                "\tphone_number,\n" +
                "\tcity,\n" +
                "\tstreet,\n" +
                "\tzip_code,\n" +
                "\tpercent\n" +
                "FROM Customer_Card\n" +
                "WHERE card_number = ?\n" +
                "ORDER BY cust_surname, cust_name, cust_patronymic";
        try (PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            pstmt.setInt(1, card_number);

            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                Res = new Customer_CardData(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getDouble(9)
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Res;
    }
    public void editCustomer_Card(Integer card_number, Customer_CardData data) throws CantEditException {
        String sql = "UPDATE Customer_Card SET\n" +
                "\tcard_number = ?,\n" +
                "\tcust_surname = ?,\n" +
                "\tcust_name = ?,\n" +
                "\tcust_patronymic = ?,\n" +
                "\tphone_number = ?,\n" +
                "\tcity = ?,\n" +
                "\tstreet = ?,\n" +
                "\tzip_code = ?,\n" +
                "\tpercent = ?\n" +
                "WHERE card_number = ?";
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            pstmt.setInt(1, data._card_number);
            pstmt.setString(2, data._cust_surname);
            pstmt.setString(3, data._cust_name);
            if(data._cust_patronymic == null){
                pstmt.setNull(4, Types.VARCHAR);
            }
            else{
                pstmt.setString(4, data._cust_patronymic);
            }
            pstmt.setString(5, data._phone_number);
            if(data._city == null){
                pstmt.setNull(6, Types.VARCHAR);
            }
            else{
                pstmt.setString(6, data._city);
            }
            if(data._street == null){
                pstmt.setNull(7, Types.VARCHAR);
            }
            else{
                pstmt.setString(7, data._street);
            }
            if(data._zip_code == null){
                pstmt.setNull(8, Types.VARCHAR);
            }
            else{
                pstmt.setString(8, data._zip_code);
            }
            pstmt.setDouble(9, data._percent);
            pstmt.setInt(10, card_number);

            pstmt.executeUpdate();

            //while (rs.next()) {
            //return rs.getString("empl_role");
            //}
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CantEditException();
        }
        //return null;
    }

    public CategoryData getCategoryById(Integer category_numbers){
        CategoryData Res = null;
        String sql = "SELECT category_number,\n" +
                "\tcategory_name\n" +
                "FROM Category\n" +
                "WHERE category_number = ?\n" +
                "ORDER BY category_name";
        try (PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            pstmt.setInt(1, category_numbers);

            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                Res = new CategoryData(
                        rs.getInt(1),
                        rs.getString(2)
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Res;
    }
    public void editCategory(Integer category_number, CategoryData data) throws CantEditException {
        String sql = "UPDATE Category SET\n" +
                "\tcategory_number = ?,\n" +
                "\tcategory_name = ?\n" +
                "WHERE category_number = ?";
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            pstmt.setInt(1, data._category_number);
            pstmt.setString(2, data._category_name);
            pstmt.setInt(3, category_number);

            pstmt.executeUpdate();

            //while (rs.next()) {
            //return rs.getString("empl_role");
            //}
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CantEditException();
        }
        //return null;
    }

    public ProductData getProductById(Integer id_product){
        ProductData Res = null;
        String sql = "SELECT id_product,\n" +
                "\tcategory_number,\n" +
                "\tproduct_name,\n" +
                "\tcharacteristics\n" +
                "FROM Product\n" +
                "WHERE id_product = ?";
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            pstmt.setInt(1, id_product);

            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                Res = new ProductData(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4)
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Res;
    }
    public void editProduct(Integer id_product, ProductData data) throws CantEditException {
        String sql = "UPDATE Product SET\n" +
                "\tid_product = ?,\n" +
                "\tcategory_number = ?,\n" +
                "\tproduct_name = ?,\n" +
                "\tcharacteristics = ?\n" +
                "WHERE id_product = ?";
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            pstmt.setInt(1, data._id_product);
            pstmt.setInt(2, data._category_number);
            pstmt.setString(3, data._product_name);
            pstmt.setString(4, data._characteristics);
            pstmt.setInt(5, id_product);

            pstmt.executeUpdate();

            //while (rs.next()) {
            //return rs.getString("empl_role");
            //}
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CantEditException();
        }
        //return null;
    }

    public Store_ProductData getStoreProductById(Integer UPC){
        Store_ProductData Res = null;
        String sql = "SELECT UPC,\n" +
                "expire_Date,\n" +
                "id_product,\n" +
                "selling_price,\n" +
                "products_number\n" +
                "FROM Store_Product\n" +
                "WHERE UPC = ?\n" +
                "ORDER BY UPC";
        try (PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            pstmt.setInt(1, UPC);

            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                Res = new Store_ProductData(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getDouble(4),
                        rs.getInt(5)
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Res;
    }
    public void editStore_Product(Integer UPC, Store_ProductData data) throws CantEditException {
        String sql = "UPDATE Store_Product_Data SET\n" +
                "\tUPC = ?,\n" +
                "\texpire_Date = ?,\n" +
                "\tid_product = ?,\n" +
                "\tselling_price = ?,\n" +
                "\tproducts_number = ?\n" +
                "WHERE UPC = ?";
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            pstmt.setInt(1, data._UPC);
            pstmt.setString(2, data._expire_Date.toString());
            pstmt.setInt(3, data._id_product);
            pstmt.setDouble(4, data._selling_price);
            pstmt.setInt(5, data._products_number);
            pstmt.setInt(6, UPC);


            pstmt.executeUpdate();

            //while (rs.next()) {
            //return rs.getString("empl_role");
            //}
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CantEditException();
        }
        //return null;
    }

    //////////////////////

    public void deleteLogin(String LoginToFind) throws CantDeleteException {
        String sql = "DELETE FROM Logins\n" +
                "WHERE empl_login = ?";
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            pstmt.setString(4, LoginToFind);

            pstmt.executeUpdate();

            //while (rs.next()) {
            //return rs.getString("empl_role");
            //}
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CantDeleteException();
        }
        //return null;
    }
    public void deleteEmployee(Integer id_employee) throws CantDeleteException {
        String sql = "DELETE FROM Employee \n" +
                "WHERE id_employee = ?";
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            pstmt.setInt(1, id_employee);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CantDeleteException();
        }
    }

    public void deleteCustomer_Card(Integer card_number) throws CantDeleteException {
        String sql = "DELETE FROM Customer_Card\n" +
                "WHERE card_number = ?";
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            pstmt.setInt(1, card_number);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CantDeleteException();
        }
    }

    public void deleteCategory(Integer category_number) throws CantDeleteException {
        String sql = "DELETE FROM Category\n" +
                "WHERE category_number = ?";
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            pstmt.setInt(1, category_number);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CantDeleteException();
        }
    }
    public void deleteProduct(Integer id_product) throws CantDeleteException {
        String sql = "DELETE FROM Product\n" +
                "WHERE id_product = ?";
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            pstmt.setInt(1, id_product);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CantDeleteException();
        }
    }
    public void deleteStore_Product(Integer UPC) throws CantDeleteException {
        String sql = "DELETE FROM Store_Product_Data\n" +
                "WHERE UPC = ?";
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            pstmt.setInt(1, UPC);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CantDeleteException();
        }
    }
    public void deleteCheq(Integer check_number) throws CantDeleteException {
        String sql = "DELETE FROM Cheq\n" +
                "WHERE check_number = ?";
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            pstmt.setInt(1, check_number);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CantDeleteException();
        }
    }

    public DataHolder getAllEmployee_SortByName() {
        DataHolder ResArr = new DataHolder();
        ResArr._columnNames.add("id");
        ResArr._columnNames.add("surname");
        ResArr._columnNames.add("name");
        ResArr._columnNames.add("patronymic");
        ResArr._columnNames.add("role");
        ResArr._columnNames.add("salary");
        ResArr._columnNames.add("date of birth");
        ResArr._columnNames.add("data of start");
        ResArr._columnNames.add("phone number");
        ResArr._columnNames.add("city");
        ResArr._columnNames.add("street");
        ResArr._columnNames.add("zipcode");
        String sql = "SELECT id_employee,\n" +
                "\templ_surname,\n" +
                "\templ_name,\n" +
                "\templ_patronymic,\n" +
                "\templ_role,\n" +
                "\tsalary,\n" +
                "\tdate_of_birth,\n" +
                "\tdata_of_start,\n" +
                "\tphone_number,\n" +
                "\tcity,\n" +
                "\tstreet,\n" +
                "\tzip_code\n" +
                "FROM Employee\n" +
                "ORDER BY empl_surname, empl_name, empl_patronymic";
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            //pstmt.setInt(1, check_number);

            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                LinkedList<Object> dataList = new LinkedList<>();
                dataList.add(rs.getInt(1));
                dataList.add(rs.getString(2));
                dataList.add(rs.getString(3));
                dataList.add(rs.getString(4));
                dataList.add(rs.getString(5));
                dataList.add(rs.getDouble(6));
                dataList.add(rs.getString(7));
                dataList.add(rs.getString(8));
                dataList.add(rs.getString(9));
                dataList.add(rs.getString(10));
                dataList.add(rs.getString(11));
                dataList.add(rs.getString(12));
                ResArr._data.add(dataList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResArr;
    }
    public DataHolder getAllEmployeeByRole_SortByName(String role) {
        DataHolder ResArr = new DataHolder();
        ResArr._columnNames.add("id");
        ResArr._columnNames.add("surname");
        ResArr._columnNames.add("name");
        ResArr._columnNames.add("patronymic");
        ResArr._columnNames.add("role");
        ResArr._columnNames.add("salary");
        ResArr._columnNames.add("date of birth");
        ResArr._columnNames.add("data of start");
        ResArr._columnNames.add("phone number");
        ResArr._columnNames.add("city");
        ResArr._columnNames.add("street");
        ResArr._columnNames.add("zipcode");
        String sql = "SELECT id_employee,\n" +
                "\templ_surname,\n" +
                "\templ_name,\n" +
                "\templ_patronymic,\n" +
                "\templ_role,\n" +
                "\tsalary,\n" +
                "\tdate_of_birth,\n" +
                "\tdata_of_start,\n" +
                "\tphone_number,\n" +
                "\tcity,\n" +
                "\tstreet,\n" +
                "\tzip_code\n" +
                "FROM Employee WHERE empl_role = ?\n" +
                "ORDER BY empl_surname, empl_name, empl_patronymic";
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            pstmt.setString(1, role);

            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                LinkedList<Object> dataList = new LinkedList<>();
                dataList.add(rs.getInt(1));
                dataList.add(rs.getString(2));
                dataList.add(rs.getString(3));
                dataList.add(rs.getString(4));
                dataList.add(rs.getString(5));
                dataList.add(rs.getDouble(6));
                dataList.add(rs.getString(7));
                dataList.add(rs.getString(8));
                dataList.add(rs.getString(9));
                dataList.add(rs.getString(10));
                dataList.add(rs.getString(11));
                dataList.add(rs.getString(12));
                ResArr._data.add(dataList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResArr;
    }
    public DataHolder getAllCashiers_SortByName() {
        return getAllEmployeeByRole_SortByName("cashier");
    }
    public DataHolder getAllCustomer_SortByName() {
        DataHolder ResArr = new DataHolder();
        ResArr._columnNames.add("id");
        ResArr._columnNames.add("surname");
        ResArr._columnNames.add("name");
        ResArr._columnNames.add("patronymic");
        ResArr._columnNames.add("phone number");
        ResArr._columnNames.add("city");
        ResArr._columnNames.add("street");
        ResArr._columnNames.add("zipcode");
        ResArr._columnNames.add("percent");
        String sql = "SELECT card_number,\n" +
                "\tcust_surname,\n" +
                "\tcust_name,\n" +
                "\tcust_patronymic,\n" +
                "\tphone_number,\n" +
                "\tcity,\n" +
                "\tstreet,\n" +
                "\tzip_code,\n" +
                "\tpercent\n" +
                "FROM Customer_Card\n" +
                "ORDER BY cust_surname, cust_name, cust_patronymic";
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            //pstmt.setString(1, "cashier");

            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                LinkedList<Object> dataList = new LinkedList<>();
                dataList.add(rs.getInt(1));
                dataList.add(rs.getString(2));
                dataList.add(rs.getString(3));
                dataList.add(rs.getString(4));
                dataList.add(rs.getString(5));
                dataList.add(rs.getString(6));
                dataList.add(rs.getString(7));
                dataList.add(rs.getString(8));
                dataList.add(rs.getDouble(9));
                ResArr._data.add(dataList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResArr;
    }
    public DataHolder getAllCategory_SortByName() {
        DataHolder ResArr = new DataHolder();
        ResArr._columnNames.add("id");
        ResArr._columnNames.add("name");
        String sql = "SELECT category_number,\n" +
                "\tcategory_name\n" +
                "FROM Category\n" +
                "ORDER BY category_name";
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            //pstmt.setString(1, "cashier");

            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                LinkedList<Object> dataList = new LinkedList<>();
                dataList.add(rs.getInt(1));
                dataList.add(rs.getString(2));
                ResArr._data.add(dataList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResArr;
    }
    public DataHolder getAllProducts_SortByName() {
        DataHolder ResArr = new DataHolder();
        ResArr._columnNames.add("id");
        ResArr._columnNames.add("cat id");
        ResArr._columnNames.add("cat name");
        ResArr._columnNames.add("name");
        ResArr._columnNames.add("characteristics");
        String sql = "SELECT id_product,\n" +
                "\tCategory.category_number,\n" +
                "\tCategory.category_name,\n" +
                "\tproduct_name,\n" +
                "\tcharacteristics\n" +
                "FROM Product INNER JOIN Category ON Product.category_number = Category.category_number\n" +
                "ORDER BY product_name";
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            //pstmt.setString(1, "cashier");

            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                LinkedList<Object> dataList = new LinkedList<>();
                dataList.add(rs.getInt(1));
                dataList.add(rs.getInt(2));
                dataList.add(rs.getString(3));
                dataList.add(rs.getString(4));
                dataList.add(rs.getString(5));
                ResArr._data.add(dataList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResArr;
    }
    public DataHolder getAllStoreProducts_SortByCountORName(boolean SortedByCount) {
        DataHolder ResArr = new DataHolder();
        ResArr._columnNames.add("UPC");
        ResArr._columnNames.add("expire date");
        ResArr._columnNames.add("id");
        ResArr._columnNames.add("name");
        ResArr._columnNames.add("characteristics");
        ResArr._columnNames.add("price");
        ResArr._columnNames.add("number");
        ResArr._columnNames.add("prom");
        ResArr._columnNames.add("expired");
        String sql = "SELECT UPC,\n" +
                "\texpire_Date,\n" +
                "\t--\n" +
                "\tStore_Product.id_product,\n" +
                "\tproduct_name,\n" +
                "\tcharacteristics,\n" +
                "\t\n" +
                "\tselling_price,\n" +
                "\tproducts_number,\n" +
                "\tpromotional_product,\n" +
                "\tis_expired\n" +
                "FROM Store_Product INNER JOIN Product ON Store_Product.id_product = Product.id_product\n";
        if(SortedByCount){
            sql += "ORDER BY products_number, product_name";
        }
        else{
            sql += "ORDER BY product_name, products_number";
        }
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            //pstmt.setString(1, "cashier");

            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                LinkedList<Object> dataList = new LinkedList<>();
                dataList.add(rs.getInt(1));
                dataList.add(rs.getString(2));
                dataList.add(rs.getInt(3));
                dataList.add(rs.getString(4));
                dataList.add(rs.getString(5));
                dataList.add(rs.getDouble(6));
                dataList.add(rs.getInt(7));
                dataList.add(rs.getBoolean(8));
                dataList.add(rs.getBoolean(9));
                ResArr._data.add(dataList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResArr;
    }
    public DataHolder getTelephoneAndAddressByPIB(String surname, String name, String patronymic) {
        DataHolder ResArr = new DataHolder();
        ResArr._columnNames.add("id");
        ResArr._columnNames.add("phone number");
        ResArr._columnNames.add("city");
        ResArr._columnNames.add("street");
        ResArr._columnNames.add("zipcode");
        String sql = "SELECT id_employee,\n" +
                "\tphone_number,\n" +
                "\tcity,\n" +
                "\tstreet,\n" +
                "\tzip_code\n" +
                "FROM Employee \n" +
                "WHERE (empl_surname LIKE ? OR ? IS NULL)\n" +
                "AND (empl_name LIKE ? OR ? IS NULL)\n" +
                "AND (empl_patronymic LIKE ? OR ? IS NULL)";
        try (PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            pstmt.setString(1, surname);
            pstmt.setString(2, surname);
            pstmt.setString(3, name);
            pstmt.setString(4, name);
            pstmt.setString(5, patronymic);
            pstmt.setString(6, patronymic);

            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                LinkedList<Object> dataList = new LinkedList<>();
                dataList.add(rs.getInt(1));
                dataList.add(rs.getString(2));
                dataList.add(rs.getString(3));
                dataList.add(rs.getString(4));
                dataList.add(rs.getString(5));
                ResArr._data.add(dataList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResArr;
    }
    public DataHolder getAllCustomerByPercent(Double percent) {
        DataHolder ResArr = new DataHolder();
        ResArr._columnNames.add("id");
        ResArr._columnNames.add("surname");
        ResArr._columnNames.add("name");
        ResArr._columnNames.add("patronymic");
        ResArr._columnNames.add("phone number");
        ResArr._columnNames.add("city");
        ResArr._columnNames.add("street");
        ResArr._columnNames.add("zipcode");
        ResArr._columnNames.add("percent");
        String sql = "SELECT card_number,\n" +
                "\tcust_surname,\n" +
                "\tcust_name,\n" +
                "\tcust_patronymic,\n" +
                "\tphone_number,\n" +
                "\tcity,\n" +
                "\tstreet,\n" +
                "\tzip_code,\n" +
                "\tpercent\n" +
                "FROM Customer_Card WHERE percent = ?\n" +
                "ORDER BY cust_surname, cust_name, cust_patronymic";
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            pstmt.setDouble(1, percent);

            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                LinkedList<Object> dataList = new LinkedList<>();
                dataList.add(rs.getInt(1));
                dataList.add(rs.getString(2));
                dataList.add(rs.getString(3));
                dataList.add(rs.getString(4));
                dataList.add(rs.getString(5));
                dataList.add(rs.getString(6));
                dataList.add(rs.getString(7));
                dataList.add(rs.getString(8));
                dataList.add(rs.getDouble(9));
                ResArr._data.add(dataList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResArr;
    }
    public DataHolder getAllProductsByCategory_nameORnumber(String category_name, Integer category_number) {
        DataHolder ResArr = new DataHolder();
        ResArr._columnNames.add("id");
        ResArr._columnNames.add("cat id");
        ResArr._columnNames.add("cat name");
        ResArr._columnNames.add("name");
        ResArr._columnNames.add("characteristics");
        String sql = "SELECT id_product,\n" +
                "\tCategory.category_number,\n" +
                "\tcategory_name,\n" +
                "\tproduct_name,\n" +
                "\tcharacteristics\n" +
                "FROM Product INNER JOIN Category ON Product.category_number = Category.category_number\n" +
                "WHERE \n" +
                "(Category.category_number = ? OR ? IS NULL)\n" +
                "ORDER BY product_name";
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){

            pstmt.setInt(1, category_number);
            pstmt.setInt(2, category_number);
            /*if(category_name == null){
                pstmt.setNull(1, Types.VARCHAR);
                pstmt.setNull(2, Types.VARCHAR);
            }
            else{
                pstmt.setString(1, category_name);
                pstmt.setString(2, category_name);
            }
            if(category_name == null){
                pstmt.setInt(3, Types.INTEGER);
                pstmt.setInt(4, Types.INTEGER);
            }
            else{
                pstmt.setInt(3, category_number);
                pstmt.setInt(4, category_number);
            }*/


            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                LinkedList<Object> dataList = new LinkedList<>();
                dataList.add(rs.getInt(1));
                dataList.add(rs.getInt(2));
                dataList.add(rs.getString(3));
                dataList.add(rs.getString(4));
                dataList.add(rs.getString(5));
                ResArr._data.add(dataList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResArr;
    }
    public DataHolder getStoreProductByUPC(Integer UPC) {
        DataHolder ResArr = new DataHolder();
        ResArr._columnNames.add("UPC");
        ResArr._columnNames.add("name");
        ResArr._columnNames.add("characteristics");
        ResArr._columnNames.add("price");
        ResArr._columnNames.add("number");
        String sql = "SELECT UPC,\n" +
                "\tproduct_name,\n" +
                "\tcharacteristics,\n" +
                "\tselling_price,\n" +
                "\tproducts_number\n" +
                "FROM Store_Product INNER JOIN Product ON Store_Product.id_product = Product.id_product\n" +
                "WHERE UPC = ?\n" +
                "ORDER BY product_name";
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            pstmt.setInt(1, UPC);

            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                LinkedList<Object> dataList = new LinkedList<>();
                dataList.add(rs.getInt(1));
                dataList.add(rs.getString(2));
                dataList.add(rs.getString(3));
                dataList.add(rs.getDouble(4));
                dataList.add(rs.getInt(5));
                ResArr._data.add(dataList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResArr;
    }
    public DataHolder getAllStoreProductsPromoOrNot_SortedByCountOrByName(boolean promo, boolean SortedByCount) {
        DataHolder ResArr = new DataHolder();
        ResArr._columnNames.add("UPC");
        ResArr._columnNames.add("expire date");
        ResArr._columnNames.add("id");
        ResArr._columnNames.add("name");
        ResArr._columnNames.add("characteristics");
        ResArr._columnNames.add("price");
        ResArr._columnNames.add("number");
        ResArr._columnNames.add("prom");
        ResArr._columnNames.add("expired");
        String sql = "SELECT UPC,\n" +
                "\texpire_Date,\n" +
                "\t--\n" +
                "\tStore_Product.id_product,\n" +
                "\tproduct_name,\n" +
                "\tcharacteristics,\n" +
                "\t\n" +
                "\tselling_price,\n" +
                "\tproducts_number,\n" +
                "\tpromotional_product,\n" +
                "\tis_expired\n" +
                "FROM Store_Product INNER JOIN Product ON Store_Product.id_product = Product.id_product\n" +
                "WHERE promotional_product = ?\n";
        if(SortedByCount){
            sql += "ORDER BY products_number, product_name";
        }
        else{
            sql += "ORDER BY product_name, products_number";
        }
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            pstmt.setBoolean(1, promo);

            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                LinkedList<Object> dataList = new LinkedList<>();
                dataList.add(rs.getInt(1));
                dataList.add(rs.getString(2));
                dataList.add(rs.getInt(3));
                dataList.add(rs.getString(4));
                dataList.add(rs.getString(5));
                dataList.add(rs.getDouble(6));
                dataList.add(rs.getInt(7));
                dataList.add(rs.getBoolean(8));
                dataList.add(rs.getBoolean(9));
                ResArr._data.add(dataList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResArr;
    }


    public DataHolder getAllCheqsMadeByEmployeeORallInSpecificPeriodOfTime(Integer id_employee, String date1, String date2) throws InvalidDateFormatException {
        CheckDate(date1);
        CheckDate(date2);
        DataHolder ResArr = new DataHolder();
        ResArr._columnNames.add("check id");
        ResArr._columnNames.add("employee id");
        ResArr._columnNames.add("cust id");
        ResArr._columnNames.add("print date");
        ResArr._columnNames.add("sum total");
        ResArr._columnNames.add("price reduction");
        ResArr._columnNames.add("to pay");
        ResArr._columnNames.add("vat");
        String sql = "SELECT check_number,\n" +
                "\tid_employee,\n" +
                "\tcard_number,\n" +
                "\tprint_date,\n" +
                "\tsum_total,\n" +
                "\tprice_reduction,\n" +
                "\tto_pay,\n" +
                "\tvat\n" +
                "FROM Cheq_Print\n" +
                "WHERE (id_employee = ? OR ? IS NULL) \n" +
                "AND print_date BETWEEN ? AND ?\n" +
                "ORDER BY print_date";

        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            if(id_employee == null){
                pstmt.setNull(1, Types.INTEGER);
                pstmt.setNull(2, Types.INTEGER);
            }
            else{
                pstmt.setInt(1, id_employee);
                pstmt.setInt(2, id_employee);
            }
            pstmt.setString(3, date1);
            pstmt.setString(4, date2);

            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                LinkedList<Object> dataList = new LinkedList<>();
                dataList.add(rs.getInt(1));
                dataList.add(rs.getInt(2));
                dataList.add(rs.getInt(3));
                dataList.add(rs.getString(4));
                dataList.add(rs.getDouble(5));
                dataList.add(rs.getDouble(6));
                dataList.add(rs.getDouble(7));
                dataList.add(rs.getDouble(8));
                ResArr._data.add(dataList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResArr;
    }
    public DataHolder getAllCheqsLineMadeByEmployeeInSpecificPeriodOfTime(Integer id_employee, String date1, String date2) throws InvalidDateFormatException {
        CheckDate(date1);
        CheckDate(date2);
        DataHolder ResArr = new DataHolder();
        ResArr._columnNames.add("check id");
        ResArr._columnNames.add("product id");
        ResArr._columnNames.add("name");
        ResArr._columnNames.add("number");
        ResArr._columnNames.add("price");
        String sql = "SELECT Cheq.check_number,\n" +
                "\tProduct.id_product,\n" +
                "\tproduct_name,\n" +
                "\tproduct_number,\n" +
                "\tSale.selling_price\n" +
                "FROM Cheq \n" +
                "INNER JOIN Sale ON Cheq.check_number = Sale.check_number\n" +
                "INNER JOIN Store_Product ON Store_Product.UPC = Sale.UPC\n" +
                "INNER JOIN Product ON Store_Product.id_product = Product.id_product\n" +
                "WHERE (id_employee = ? OR ? IS NULL) \n" +
                "AND print_date BETWEEN ? AND ?\n" +
                "ORDER BY print_date";
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            if(id_employee == null){
                pstmt.setNull(1, Types.INTEGER);
                pstmt.setNull(2, Types.INTEGER);
            }
            else{
                pstmt.setInt(1, id_employee);
                pstmt.setInt(2, id_employee);
            }
            pstmt.setString(3, date1);
            pstmt.setString(4, date2);



            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                LinkedList<Object> dataList = new LinkedList<>();
                dataList.add(rs.getInt(1));
                dataList.add(rs.getInt(2));
                dataList.add(rs.getString(3));
                dataList.add(rs.getInt(4));
                dataList.add(rs.getDouble(5));
                ResArr._data.add(dataList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResArr;
    }
    public DataHolder getSumOfAllSoldProductsByEmployeeORallInSpecificPeriodOfTime(Integer id_employee, String date1, String date2) throws InvalidDateFormatException {
        CheckDate(date1);
        CheckDate(date2);
        DataHolder ResArr = new DataHolder();
        ResArr._columnNames.add("employee id");
        ResArr._columnNames.add("total cost");
        String sql = "SELECT id_employee, \n" +
                "SUM(sum_total) AS cost\n" +
                "FROM Cheq_Print\n" +
                "GROUP BY id_employee\n" +
                "HAVING (id_employee = ? OR ? IS NULL)  \n" +
                "AND print_date BETWEEN ? AND ?\n" +
                "ORDER BY cost";
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            pstmt.setInt(1, id_employee);
            pstmt.setInt(2, id_employee);
            pstmt.setString(3, date1);
            pstmt.setString(4, date2);

            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                LinkedList<Object> dataList = new LinkedList<>();
                dataList.add(rs.getInt(1));
                dataList.add(rs.getDouble(2));
                ResArr._data.add(dataList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResArr;
    }
    public DataHolder getSumOfAllSoldProductsInSpecificPeriodOfTime(String date1, String date2) throws InvalidDateFormatException {
        CheckDate(date1);
        CheckDate(date2);
        DataHolder ResArr = new DataHolder();
        ResArr._columnNames.add("total cost");
        String sql = "SELECT SUM(sum_total) AS cost\n" +
                "FROM Cheq_Print\n" +
                "WHERE print_date BETWEEN ? AND ?\n" +
                "ORDER BY cost";
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            pstmt.setString(1, date1);
            pstmt.setString(2, date2);

            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                LinkedList<Object> dataList = new LinkedList<>();
                dataList.add(rs.getInt(1));
                ResArr._data.add(dataList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResArr;
    }
    public DataHolder getCountOfSoldProductsInSpecificPeriodOfTime(Integer id_product, String date1, String date2) throws InvalidDateFormatException {
        CheckDate(date1);
        CheckDate(date2);
        DataHolder ResArr = new DataHolder();
        ResArr._columnNames.add("product id");
        ResArr._columnNames.add("total count");
        String sql = "SELECT id_product, SUM(product_number) AS total_count\n" +
                "FROM Cheq\n" +
                "INNER JOIN Sale ON Cheq.check_number = Sale.check_number\n" +
                "INNER JOIN Store_Product ON Store_Product.UPC = Sale.UPC\n" +
                "WHERE id_product = ?\n" +
                "AND print_date BETWEEN ? AND ?\n" +
                "ORDER BY total_count";
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            pstmt.setInt(1, id_product);
            pstmt.setString(2, date1);
            pstmt.setString(3, date2);

            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                LinkedList<Object> dataList = new LinkedList<>();
                dataList.add(rs.getInt(1));
                dataList.add(rs.getDouble(2));
                ResArr._data.add(dataList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResArr;
    }

    public DataHolder getStoreProductsByName(String product_name) {
        DataHolder ResArr = new DataHolder();
        ResArr._columnNames.add("UPC");
        ResArr._columnNames.add("expire date");
        ResArr._columnNames.add("id");
        ResArr._columnNames.add("name");
        ResArr._columnNames.add("characteristics");
        ResArr._columnNames.add("price");
        ResArr._columnNames.add("number");
        ResArr._columnNames.add("prom");
        ResArr._columnNames.add("expired");
        String sql = "SELECT UPC,\n" +
                "\texpire_Date,\n" +
                "\t--\n" +
                "\tStore_Product.id_product,\n" +
                "\tproduct_name,\n" +
                "\tcharacteristics,\n" +
                "\t\n" +
                "\tselling_price,\n" +
                "\tproducts_number,\n" +
                "\tpromotional_product,\n" +
                "\tis_expired\n" +
                "FROM Store_Product INNER JOIN Product ON Store_Product.id_product = Product.id_product\n" +
                "WHERE product_name LIKE ?\n" +
                "ORDER BY product_name";
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            pstmt.setString(1, product_name);

            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                LinkedList<Object> dataList = new LinkedList<>();
                dataList.add(rs.getInt(1));
                dataList.add(rs.getString(2));
                dataList.add(rs.getInt(3));
                dataList.add(rs.getString(4));
                dataList.add(rs.getString(5));
                dataList.add(rs.getDouble(6));
                dataList.add(rs.getInt(7));
                dataList.add(rs.getBoolean(8));
                dataList.add(rs.getBoolean(9));
                ResArr._data.add(dataList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResArr;
    }
    public DataHolder getAllCustomerByName(String surname, String name, String patronymic) {
        DataHolder ResArr = new DataHolder();
        ResArr._columnNames.add("id");
        ResArr._columnNames.add("surname");
        ResArr._columnNames.add("name");
        ResArr._columnNames.add("patronymic");
        ResArr._columnNames.add("phone number");
        ResArr._columnNames.add("city");
        ResArr._columnNames.add("street");
        ResArr._columnNames.add("zipcode");
        ResArr._columnNames.add("percent");
        String sql = "SELECT card_number,\n" +
                "\tcust_surname,\n" +
                "\tcust_name,\n" +
                "\tcust_patronymic,\n" +
                "\tphone_number,\n" +
                "\tcity,\n" +
                "\tstreet,\n" +
                "\tzip_code,\n" +
                "\tpercent\n" +
                "FROM Customer_Card\n" +
                "WHERE (cust_surname LIKE ? OR ? IS NULL)\n" +
                "AND (cust_name LIKE ? OR ? IS NULL)\n" +
                "AND (cust_patronymic LIKE ? OR ? IS NULL)\n" +
                "ORDER BY cust_surname, cust_name, cust_patronymic";
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            if(surname == null){
                pstmt.setNull(1, Types.VARCHAR);
                pstmt.setNull(2, Types.VARCHAR);
            }
            else{
                pstmt.setString(1, surname);
                pstmt.setString(2, surname);
            }
            if(name == null){
                pstmt.setNull(3, Types.VARCHAR);
                pstmt.setNull(4, Types.VARCHAR);
            }
            else{
                pstmt.setString(3, name);
                pstmt.setString(4, name);
            }
            if(patronymic == null){
                pstmt.setNull(5, Types.VARCHAR);
                pstmt.setNull(6, Types.VARCHAR);
            }
            else{
                pstmt.setString(5, patronymic);
                pstmt.setString(6, patronymic);
            }


            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                LinkedList<Object> dataList = new LinkedList<>();
                dataList.add(rs.getInt(1));
                dataList.add(rs.getString(2));
                dataList.add(rs.getString(3));
                dataList.add(rs.getString(4));
                dataList.add(rs.getString(5));
                dataList.add(rs.getString(6));
                dataList.add(rs.getString(7));
                dataList.add(rs.getString(8));
                dataList.add(rs.getDouble(9));
                ResArr._data.add(dataList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResArr;
    }

    ///////////////////////
    //77777

    public Integer CreateBlankCheq(Integer id_employee) throws CantCreateException{
        Integer check_number = null;
        String sql1 = "INSERT INTO Cheq (\n" +
                "\tid_employee,\n" +
                "\tcard_number,\n" +
                "\tprint_date\n" +
                ")\n" +
                "VALUES (?,NULL,DATE());";
        String sql2 = "SELECT check_number\n" +
                "FROM Cheq\n" +
                "WHERE ROWID = last_insert_rowid();";
        try (   PreparedStatement pstmt1  = _conn.prepareStatement(sql1);
                PreparedStatement pstmt2  = _conn.prepareStatement(sql2)){
            pstmt1.setInt(1, id_employee);
            pstmt1.executeUpdate();

            ResultSet rs  = pstmt2.executeQuery();
            while (rs.next()) {
                check_number = rs.getInt(1);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CantCreateException();
        }
        return check_number;
    }
    public void AddNewLineToCheq(Integer UPC, Integer product_number, Integer check_number) throws WentInNegativeExeption, CantEditException, CantCreateException {
        if(product_number < 0){
            throw new WentInNegativeExeption();
        }
        Integer Storage_product_number = -1;
        Double selling_price = null;
        String sql = "SELECT UPC,\n" +
                "products_number,\n" +
                "selling_price\n" +
                "FROM Store_Product\n" +
                "WHERE UPC = ?";
        try (PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            pstmt.setInt(1, UPC);

            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                Storage_product_number = rs.getInt(2);
                selling_price = rs.getDouble(3);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(Storage_product_number < product_number){
            throw new WentInNegativeExeption();
        }

        sql = "UPDATE Store_Product_Data SET\n" +
                "\tproducts_number = products_number - ?\n" +
                "WHERE UPC = ?";
        try (   PreparedStatement pstmt1  = _conn.prepareStatement(sql)){
            pstmt1.setInt(1, product_number);
            pstmt1.setInt(2, UPC);
            pstmt1.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CantEditException();
        }

        sql = "INSERT INTO Sale (\n" +
                "\tUPC,\n" +
                "\tcheck_number,\n" +
                "\tproduct_number,\n" +
                "\tselling_price\n" +
                ")\n" +
                "VALUES (?,?,?,?);";
        try (PreparedStatement pstmt1  = _conn.prepareStatement(sql)){
            pstmt1.setInt(1, UPC);
            pstmt1.setInt(2, check_number);
            pstmt1.setInt(3, product_number);
            pstmt1.setDouble(4, selling_price);
            pstmt1.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CantCreateException();
        }

    }
    public void LinkCustomerCardToCheq(Integer card_number, Integer check_number) throws CantEditException{
        String sql = "UPDATE Cheq SET\n" +
                "\tcard_number = ?\n" +
                "WHERE check_number = ?";
        try (PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            pstmt.setInt(1, card_number);
            pstmt.setInt(2, check_number);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CantEditException();
        }
    }
    public void EndMakingCheq(Integer check_number, boolean printORdelete) throws CantEditException, CantDeleteException {
        String sql = "";
        if(printORdelete){
            sql = "UPDATE Cheq SET\n" +
                    "\tprint_date = DATE()\n" +
                    "WHERE check_number = ?";
        }
        else{
            sql = "DELETE FROM Cheq\n" +
                    "WHERE check_number = ?";
        }
        try (PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            pstmt.setInt(1, check_number);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            if(printORdelete){
                throw new CantEditException();
            }
            else{
                throw new CantDeleteException();
            }

        }
    }
    public void DeleteSale(Integer check_number, Integer UPC) throws CantDeleteException {
        String sql = "DELETE FROM Sale\n" +
                "WHERE UPC = ? AND check_number = ?";

        try (PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            pstmt.setInt(1, UPC);
            pstmt.setInt(2, check_number);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CantDeleteException();
        }
    }

    ///////////////////////

    public DataHolder getAllCheqsMadeByEmployeeOnThisDay(Integer id_employee) {
        DataHolder ResArr = new DataHolder();
        ResArr._columnNames.add("check id");
        ResArr._columnNames.add("employee id");
        ResArr._columnNames.add("customer id");
        ResArr._columnNames.add("print date");
        ResArr._columnNames.add("sum total");
        ResArr._columnNames.add("price reduction");
        ResArr._columnNames.add("to pay");
        ResArr._columnNames.add("vat");
        String sql = "SELECT check_number,\n" +
                "\tid_employee,\n" +
                "\tcard_number,\n" +
                "\tprint_date,\n" +
                "\tsum_total,\n" +
                "\tprice_reduction,\n" +
                "\tto_pay,\n" +
                "\tvat\n" +
                "FROM Cheq_Print\n" +
                "WHERE id_employee = ? \n" +
                "AND print_date = DATE()\n" +
                "ORDER BY print_date";

        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            pstmt.setInt(1, id_employee);

            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                LinkedList<Object> dataList = new LinkedList<>();
                dataList.add(rs.getInt(1));
                dataList.add(rs.getInt(2));
                dataList.add(rs.getInt(3));
                dataList.add(rs.getString(4));
                dataList.add(rs.getDouble(5));
                dataList.add(rs.getDouble(6));
                dataList.add(rs.getDouble(7));
                dataList.add(rs.getDouble(8));
                ResArr._data.add(dataList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResArr;
    }
    public DataHolder getCheqByNumber(Integer check_number) {
        DataHolder ResArr = new DataHolder();
        ResArr._columnNames.add("check id");
        ResArr._columnNames.add("employee id");
        ResArr._columnNames.add("customer id");
        ResArr._columnNames.add("print date");
        ResArr._columnNames.add("sum total");
        ResArr._columnNames.add("price reduction");
        ResArr._columnNames.add("to pay");
        ResArr._columnNames.add("vat");
        String sql = "SELECT check_number,\n" +
                "\tid_employee,\n" +
                "\tcard_number,\n" +
                "\tprint_date,\n" +
                "\tsum_total,\n" +
                "\tprice_reduction,\n" +
                "\tto_pay,\n" +
                "\tvat\n" +
                "FROM Cheq_Print\n" +
                "WHERE check_number = ?\n" +
                "ORDER BY print_date";

        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            if(check_number == null){
                pstmt.setNull(1, Types.INTEGER);
            }
            else{
                pstmt.setInt(1, check_number);
            }


            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                LinkedList<Object> dataList = new LinkedList<>();
                dataList.add(rs.getInt(1));
                dataList.add(rs.getInt(2));
                dataList.add(rs.getInt(3));
                dataList.add(rs.getString(4));
                dataList.add(rs.getDouble(5));
                dataList.add(rs.getDouble(6));
                dataList.add(rs.getDouble(7));
                dataList.add(rs.getDouble(8));
                ResArr._data.add(dataList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResArr;
    }
    public DataHolder getAllLinesFromCheq(Integer check_number) {
        DataHolder ResArr = new DataHolder();
        ResArr._columnNames.add("UPC");
        ResArr._columnNames.add("check id");
        ResArr._columnNames.add("product id");
        ResArr._columnNames.add("name");
        ResArr._columnNames.add("number");
        ResArr._columnNames.add("price");
        String sql = "SELECT Sale.UPC, \n" +
                "\tCheq.check_number,\n" +
                "\tProduct.id_product,\n" +
                "\tproduct_name,\n" +
                "\tproduct_number,\n" +
                "\tSale.selling_price\n" +
                "FROM Cheq \n" +
                "INNER JOIN Sale ON Cheq.check_number = Sale.check_number\n" +
                "INNER JOIN Store_Product ON Store_Product.UPC = Sale.UPC\n" +
                "INNER JOIN Product ON Store_Product.id_product = Product.id_product\n" +
                "WHERE Cheq.check_number = ? \n" +
                "ORDER BY product_name";
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            if(check_number == null){
                pstmt.setNull(1, Types.INTEGER);
            }
            else{
                pstmt.setInt(1, check_number);
            }

            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                LinkedList<Object> dataList = new LinkedList<>();
                dataList.add(rs.getInt(1));
                dataList.add(rs.getInt(2));
                dataList.add(rs.getInt(3));
                dataList.add(rs.getString(4));
                dataList.add(rs.getInt(5));
                dataList.add(rs.getDouble(6));
                ResArr._data.add(dataList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResArr;
    }
    public DataHolder getEmployeeByID(Integer id_employee) {
        DataHolder ResArr = new DataHolder();
        ResArr._columnNames.add("id");
        ResArr._columnNames.add("surname");
        ResArr._columnNames.add("name");
        ResArr._columnNames.add("patronymic");
        ResArr._columnNames.add("role");
        ResArr._columnNames.add("salary");
        ResArr._columnNames.add("date of birth");
        ResArr._columnNames.add("data of start");
        ResArr._columnNames.add("phone number");
        ResArr._columnNames.add("city");
        ResArr._columnNames.add("street");
        ResArr._columnNames.add("zipcode");
        String sql = "SELECT id_employee,\n" +
                "\templ_surname,\n" +
                "\templ_name,\n" +
                "\templ_patronymic,\n" +
                "\templ_role,\n" +
                "\tsalary,\n" +
                "\tdate_of_birth,\n" +
                "\tdata_of_start,\n" +
                "\tphone_number,\n" +
                "\tcity,\n" +
                "\tstreet,\n" +
                "\tzip_code\n" +
                "FROM Employee\n" +
                "WHERE id_employee = ?";
        try (
                PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            pstmt.setInt(1, id_employee);

            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                LinkedList<Object> dataList = new LinkedList<>();
                dataList.add(rs.getInt(1));
                dataList.add(rs.getString(2));
                dataList.add(rs.getString(3));
                dataList.add(rs.getString(4));
                dataList.add(rs.getString(5));
                dataList.add(rs.getDouble(6));
                dataList.add(rs.getString(7));
                dataList.add(rs.getString(8));
                dataList.add(rs.getString(9));
                dataList.add(rs.getString(10));
                dataList.add(rs.getString(11));
                dataList.add(rs.getString(12));
                ResArr._data.add(dataList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResArr;
    }

    public DataHolder getAllCheqs_SortByPrintDate(){
        DataHolder ResArr = new DataHolder();
        ResArr._columnNames.add("check id");
        ResArr._columnNames.add("employee id");
        ResArr._columnNames.add("customer id");
        ResArr._columnNames.add("print date");
        ResArr._columnNames.add("sum total");
        ResArr._columnNames.add("price reduction");
        ResArr._columnNames.add("to pay");
        ResArr._columnNames.add("vat");
        String sql = "SELECT check_number,\n" +
                "\tid_employee,\n" +
                "\tcard_number,\n" +
                "\tprint_date,\n" +
                "\tsum_total,\n" +
                "\tprice_reduction,\n" +
                "\tto_pay,\n" +
                "\tvat\n" +
                "FROM Cheq_Print\n" +
                "ORDER BY print_date";
        try (PreparedStatement pstmt  = _conn.prepareStatement(sql)){

            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                LinkedList<Object> dataList = new LinkedList<>();
                dataList.add(rs.getInt(1));
                dataList.add(rs.getInt(2));
                dataList.add(rs.getInt(3));
                dataList.add(rs.getString(4));
                dataList.add(rs.getDouble(5));
                dataList.add(rs.getDouble(6));
                dataList.add(rs.getDouble(7));
                dataList.add(rs.getDouble(8));
                ResArr._data.add(dataList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResArr;
    }

    public DataHolder countForAllProductUniqueCustomers(){
        DataHolder ResArr = new DataHolder();
        ResArr._columnNames.add("id_product");
        ResArr._columnNames.add("product_name");
        ResArr._columnNames.add("number_of_unique_buyers");
        String sql = "SELECT Product.id_product, Product.product_name, COUNT(card_number) AS number_of_unique_buyers\n" +
                "FROM Product \n" +
                "LEFT JOIN Store_Product_Data ON Product.id_product = Store_Product_Data.id_product\n" +
                "LEFT JOIN Sale ON Store_Product_Data.UPC = Sale.UPC\n" +
                "LEFT JOIN Cheq ON Sale.check_number = Cheq.check_number\n" +
                "GROUP BY Product.id_product;\n";
        try (PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            //pstmt.setInt(1, id_employee);

            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                LinkedList<Object> dataList = new LinkedList<>();
                dataList.add(rs.getInt(1));
                dataList.add(rs.getString(2));
                dataList.add(rs.getInt(3));
                ResArr._data.add(dataList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResArr;
    }

    public DataHolder getAllCustomersThatDidntBuyProductsThatBuyGivenCustomer(Integer card_number){
        DataHolder ResArr = new DataHolder();
        ResArr._columnNames.add("card_number");
        ResArr._columnNames.add("cust_surname");
        ResArr._columnNames.add("cust_name");
        ResArr._columnNames.add("cust_patronymic");
        String sql = "SELECT card_number, cust_surname, cust_name, cust_patronymic\n" +
                "FROM Customer_Card\n" +
                "WHERE card_number NOT IN (\n" +
                "\tSELECT card_number\n" +
                "\tFROM Cheq\n" +
                "\tINNER JOIN Sale ON Sale.check_number = Cheq.check_number\n" +
                "\tINNER JOIN Store_Product_Data ON Store_Product_Data.UPC = Sale.UPC\n" +
                "\tWHERE id_product NOT IN(\n" +
                "\t\tSELECT id_product\n" +
                "\t\tFROM Cheq\n" +
                "\t\tINNER JOIN Sale ON Sale.check_number = Cheq.check_number\n" +
                "\t\tINNER JOIN Store_Product_Data ON Store_Product_Data.UPC = Sale.UPC\n" +
                "\t\tWHERE Cheq.card_number = ?\n" +
                "\t)\n" +
                ")";
        try (PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            if(card_number == null){
                pstmt.setNull(1, Types.INTEGER);
            }
            else {
                pstmt.setInt(1, card_number);
            }


            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                LinkedList<Object> dataList = new LinkedList<>();
                dataList.add(rs.getInt(1));
                dataList.add(rs.getString(2));
                dataList.add(rs.getString(3));
                dataList.add(rs.getString(4));
                ResArr._data.add(dataList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResArr;
    }

    public DataHolder getAllCategoryThatDidntHaveProductsThatDidntBuyGivenCustomer(Integer card_number){
        DataHolder ResArr = new DataHolder();
        ResArr._columnNames.add("category_number");
        ResArr._columnNames.add("category_name");
        String sql = "SELECT Category.category_number, category_name\n" +
                "FROM Category\n" +
                "WHERE Category.category_number NOT IN (\n" +
                "\tSELECT Product.category_number\n" +
                "\tFROM Product\n" +
                "\tLEFT JOIN Store_Product_Data ON Product.id_product = Store_Product_Data.id_product\n" +
                "\tLEFT JOIN Sale ON Store_Product_Data.UPC = Sale.UPC\n" +
                "\tLEFT JOIN Cheq ON Sale.check_number = Cheq.check_number\n" +
                "\tWHERE card_number <> ?\n" +
                ")";
        try (PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            pstmt.setInt(1, card_number);

            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                LinkedList<Object> dataList = new LinkedList<>();
                dataList.add(rs.getInt(1));
                dataList.add(rs.getString(2));
                ResArr._data.add(dataList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResArr;
    }

    public DataHolder countForAllCustomersFromHowManyCategoryTheyMadePurchase(){
        DataHolder ResArr = new DataHolder();
        ResArr._columnNames.add("card_number");
        ResArr._columnNames.add("cust_surname");
        ResArr._columnNames.add("cust_name");
        ResArr._columnNames.add("cust_patronymic");
        ResArr._columnNames.add("uniqe_category");
        String sql = "SELECT Customer_Card.card_number, cust_surname, cust_name, cust_patronymic, COUNT(category_number) AS uniqe_category\n" +
                "FROM Customer_Card\n" +
                "LEFT JOIN Cheq ON Customer_Card.card_number = Cheq.card_number\n" +
                "LEFT JOIN Sale ON Sale.check_number = Cheq.check_number\n" +
                "LEFT JOIN Store_Product_Data ON Store_Product_Data.UPC = Sale.UPC\n" +
                "LEFT JOIN Product ON Product.id_product = Store_Product_Data.id_product\n" +
                "GROUP BY Customer_Card.card_number\n";
        try (PreparedStatement pstmt  = _conn.prepareStatement(sql)){
            //pstmt.setInt(1, card_number);

            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                LinkedList<Object> dataList = new LinkedList<>();
                dataList.add(rs.getInt(1));
                dataList.add(rs.getString(2));
                dataList.add(rs.getString(3));
                dataList.add(rs.getString(4));
                dataList.add(rs.getInt(5));
                ResArr._data.add(dataList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResArr;
    }


    public Table generateTableFrom(DataHolder currentDataHolder){
        Table table = new Table(currentDataHolder._columnNames.size());
        for (String columnName : currentDataHolder._columnNames) {
            table.addCell(new Cell().add(new Paragraph(columnName).setFontSize(8)));
        }
        for (LinkedList<Object> datum : currentDataHolder._data) {
            for (Object o : datum) {
                if(o != null){
                    table.addCell(new Cell().add(new Paragraph(o.toString()).setFontSize(8)));
                }
                else{
                    table.addCell(new Cell().add(new Paragraph("NULL").setFontSize(8)));
                }
            }
        }
        table.setWidth(UnitValue.createPercentValue(100));
        return table;
    }

    public void createPDF() throws IOException {
        //employee
        //customers
        //category
        //products
        //productsInStore
        //cheqs
        DataHolder employeeDataset = getAllEmployee_SortByName();
        DataHolder customerDataset = getAllCustomer_SortByName();
        DataHolder categoryDataset = getAllCategory_SortByName();
        DataHolder productDataset = getAllProducts_SortByName();
        DataHolder productsInStoreDataset = getAllStoreProducts_SortByCountORName(false);
        DataHolder cheqDataset = getAllCheqs_SortByPrintDate();

        String file = "ZLAGODA.pdf";

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(file));
        Document doc = new Document(pdfDoc);

        doc.add(new Paragraph("Employee"));
        doc.add(generateTableFrom(employeeDataset));
        doc.add(new Paragraph("Customer"));
        doc.add(generateTableFrom(customerDataset));
        doc.add(new Paragraph("Category"));
        doc.add(generateTableFrom(categoryDataset));
        doc.add(new Paragraph("Product"));
        doc.add(generateTableFrom(productDataset));
        doc.add(new Paragraph("Store Product"));
        doc.add(generateTableFrom(productsInStoreDataset));
        doc.add(new Paragraph("Check"));
        doc.add(generateTableFrom(cheqDataset));

        doc.close();

        java.io.File fileA = new java.io.File("ZLAGODA.pdf");
        java.awt.Desktop.getDesktop().open(fileA);
    }

    public static void main(String[] args) {

        DataBaseController DBC = new DataBaseController("ZLAGODA");
        DBC.basicInit();
        try {

            //DBC.createNewEmployee_AI(new EmployeeData(121, "surn121", "name121", "patr121", "cashier", 1000.0, "2000-11-11", "2222-11-11", "+3800", "121city", "121street", "121zip"));
            //DBC.createNewEmployeeAutoDate_AI(new EmployeeData(121, "surn121", "name121", "patr121", "cashier", 1000.0, "2000-11-11", "2222-11-11", "+3800", "121city", "121street", "121zip"));
            //DBC.createNewCustomer_Card_AI(new Customer_CardData(121, "surn121", "name121", "patr121", "+3800", null, null, null, 0.5));
            //DBC.createNewCategory_AI(new CategoryData(1,"Cat1"));
            //DBC.createNewProduct_AI(new ProductData(11,1,"prod11","char11"));
            //DBC.createNewStore_Product_AI(new Store_ProductData(121,"2000-11-11",1,3.5,100,false,false));
            //DBC.editCustomer_Card(1 ,new Customer_CardData(121, "surn222", "name121", "patr121", "+3800", null, null, null, 0.5));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        try {
            DBC.createPDF();
            //TWO AAA = DBC.getAllEmployee_SortByName().ConvertToSwingTableData();
            //System.out.println("A");
        }
        catch (Exception e){
            e.printStackTrace();
        }

        //Font[] AA = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();

        System.out.println(java.sql.Date.valueOf("2222-11-11"));
        System.out.println(("Qwerty123"));
        //   System.out.println(sha256("abc").equals("ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad"));

    }

}
