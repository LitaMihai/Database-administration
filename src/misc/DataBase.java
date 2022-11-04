package misc;

import java.sql.*;

public class DataBase {
    private Connection connection;
    private Statement statement;
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
            while(resultSet.next()){
                System.out.println(resultSet.getString(2) + " " + resultSet.getString(3) + " " + resultSet.getString(4));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
