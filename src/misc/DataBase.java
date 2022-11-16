package misc;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class DataBase {
    private Connection connection;
    private Statement statement;
    private JTable table;
    private JFrame frame;
    private DefaultTableModel model;
    public DataBase(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbURL = "jdbc:sqlserver://DESKTOP-U62N61T\\SQLEXPRESS;" +
                    "Database=EPTM;" +
                    "IntegratedSecurity=true;" +
                    "encrypt=true;trustServerCertificate=true";
            String userName = "sa";
            String pass = "ed308";
            this.connection = DriverManager.getConnection(dbURL, userName, pass);
            this.statement = this.connection.createStatement();
            this.model = new DefaultTableModel();
            this.table = new JTable(model);
        }
        catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void CloseConnection(){
        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void SendQuery(String query){
        ResultSet resultSet = null;
        try {
            resultSet = this.statement.executeQuery(query);
            String tabela = "";

            // Clear the table
            model.getDataVector().removeAllElements();
            model.setColumnCount(0);
            model.fireTableDataChanged();

            // Daca query ul se face pe doctori
            model.addColumn("Nume");
            model.addColumn("Prenume");
            model.addColumn("Specializare");

            while(resultSet.next()){
                String s1 = resultSet.getString(2);
                String s2 = resultSet.getString(3);
                String s3 = resultSet.getString(4);

                // Here we re finding the index of the blank space after the table name
                int index = query.indexOf("FROM") + 5;
                while(query.charAt(index) != ' ' && index < query.length() - 1){
                    index++;
                }
                index++;

                tabela = query.substring(query.indexOf("FROM") + 5, index);
                System.out.println(tabela);

                model.addRow(new Object[]{s1, s2, s3});
            }
            this.frame = new JFrame();
            this.frame.setSize(550, 350);
            this.frame.add(new JScrollPane(table));
            this.frame.dispose();
            this.frame.setVisible(true);
            this.frame.setTitle(tabela);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
