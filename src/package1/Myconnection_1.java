
package package1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


class MyConnection_1 {
    public static Connection getConnection() throws SQLException{
     
    Connection con = null;
    try {
         Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance","root","Polaroid8901@:sql");
            System.out.println("connection success");
}
    catch(ClassNotFoundException ex){
        System.out.println("no database");
}
    return con;
    }

    static Connection getCon() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Cod//e/GeneratedMethodBody
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Ken Española
 */
class Myconnection {
    
}
