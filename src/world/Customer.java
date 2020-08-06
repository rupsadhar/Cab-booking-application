/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author praty
 */
public class Customer extends User{

    public Customer(String username) {
        this.username = username;
    }
    
    public void register()
    {
        MysqlCon m = new MysqlCon();
        try
        {
            Statement stmt = m.con.createStatement();
            stmt.execute("insert into mydb.users values('" + username + "','" + password + "','" + name + "','" + mobile + "','" + email + "', 1, 0, 0)");
            m.con.close();
        }
        catch(SQLException se){se.printStackTrace();}
    }
    
    public void fetchCustomer()
    {
        MysqlCon m = new MysqlCon();
        try {
            Statement stmt = m.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM mydb.users where username = '" + username + "'");
            rs.next();
            this.username = rs.getString("username");
            this.name = rs.getString("name");
            m.con.close();
        } 
        catch (SQLException se) {se.printStackTrace();}
    }
    
    public double getBalance()
    {
        MysqlCon m = new MysqlCon();
        double balance = 0;
        try
        {
            Statement stmt = m.con.createStatement();  
            ResultSet rs = stmt.executeQuery("select * from mydb.users where username = " + "'" + username + "'"); 
            rs.next();
            balance = rs.getDouble("balance");
            m.con.close();
        }
        catch(SQLException se){}

        return balance;
    }
    
    public void addMoney(double money)
    {
        MysqlCon m = new MysqlCon();
        try
        {
            Statement stmt = m.con.createStatement();
            stmt.execute("update mydb.users set balance = balance + " + money + " where username = " + "'" + username + "'");
            m.con.close();
        }
        catch(SQLException se){se.printStackTrace();}
    }
    
    public void makePayment(Driver driver, double fare)
    {
        MysqlCon m = new MysqlCon();
        try
        {
            Statement stmt = m.con.createStatement();
            stmt.execute("update mydb.users set balance = balance - " + fare + " where username = " + "'" + username + "'");
            stmt.execute("update mydb.users set balance = balance + " + fare + " where username = " + "'" + driver.username + "'");
            m.con.close();
        }
        catch(SQLException se){se.printStackTrace();}
    }

    public void setOnline(int i) {
        MysqlCon m = new MysqlCon();
        try
        {
            Statement stmt = m.con.createStatement();
            stmt.execute("update mydb.users set online = " + i + " where username = '" + username + "'");
            m.con.close();
        }
        catch(SQLException se){se.printStackTrace();}
    }
    
    public boolean getOnline() {
        MysqlCon m = new MysqlCon();
        int status = 0;
        try
        {
            Statement stmt = m.con.createStatement();  
            ResultSet rs = stmt.executeQuery("select * from mydb.users where username = '" + username + "'"); 
            rs.next();
            status = rs.getInt("online");
            m.con.close();
        }
        catch(SQLException se){}
        
        return status == 1;
    }
    
    public boolean isCustomer() {
        MysqlCon m = new MysqlCon();
        int type = 0;
        try
        {
            Statement stmt = m.con.createStatement();  
            ResultSet rs = stmt.executeQuery("select * from mydb.users where username = '" + username + "'"); 
            rs.next();
            type = rs.getInt("type");
            m.con.close();
        }
        catch(SQLException se){}
        
        return type == 1;
    }
}
