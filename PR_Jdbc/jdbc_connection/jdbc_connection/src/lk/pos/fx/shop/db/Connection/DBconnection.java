package lk.pos.fx.shop.db.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
    private static DBconnection dBconnection;
    private Connection connection;
    public static DBconnection getInstance() throws SQLException, ClassNotFoundException {
        if(dBconnection==null) {
          dBconnection=new DBconnection();
        }
        return dBconnection;
    }

    private DBconnection() throws ClassNotFoundException, SQLException {
       // Class.forName("com.mysql.jdbc.Driver");
        connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/pos_system?useSSL=false","root","rathnayaka1");
    }

    public Connection getConnection(){
        return connection;
    }
}
