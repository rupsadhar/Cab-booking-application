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
public class Driver extends User{
    String vehicleId;
    String vehicleModel;
    double rating;
    int nr;
    int x;
    int y;
    public Driver(String username) {
        this.username = username;
    }
    public void fetchDriver()
    {
        MysqlCon m = new MysqlCon();
        try {
                Statement stmt = m.con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM mydb.drivers, mydb.users where mydb.users.username = mydb.drivers.username and mydb.users.username = '" + username + "'");
                rs.next();
                this.username = rs.getString("username");
                this.x = rs.getInt("x");
                this.y = rs.getInt("y");
                this.vehicleId = rs.getString("vehicle_id");
                this.vehicleModel = rs.getString("model");
                this.name = rs.getString("name");
                this.mobile = rs.getString("mobile");
                this.rating = rs.getDouble("rating");
                this.nr = rs.getInt("nr");
                m.con.close();
            } catch (SQLException se) {se.printStackTrace();}
    }
    public void addRating(int x)
    {
        MysqlCon m = new MysqlCon();
        double t = nr * rating;
        nr += 1;
        t += x;
        rating = t/nr;
        try
        {
            Statement stmt = m.con.createStatement();
            stmt.execute("update mydb.drivers set rating = " + rating + ", nr = nr + 1 where username = " + "'" + username + "'");
            m.con.close();
        }
        catch(SQLException se){se.printStackTrace();}
    }
    
    public void setDrivingState(int f)
    {
        MysqlCon m = new MysqlCon();
        try
        {
            Statement stmt = m.con.createStatement();
            stmt.execute("update mydb.drivers set driving = " + f + " where username = " + "'" + username + "'");
            m.con.close();
        }
        catch(SQLException se){se.printStackTrace();}
    }
    
    public int getDriverX()
    {
        MysqlCon m = new MysqlCon();
        int x = 0;
        try {
            Statement stmt = m.con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from mydb.drivers where username = '" + username + "'");
            rs.next();
            x = rs.getInt("x");
            m.con.close();
        } catch (SQLException se) {se.printStackTrace();}

        return x;
    }
    
    public int getDriverY()
    {
        MysqlCon m = new MysqlCon();
        int y = 0;
        try {
            Statement stmt = m.con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from mydb.drivers where username = '" + username + "'");
            rs.next();
            y = rs.getInt("y");
            m.con.close();
        } catch (SQLException se) {}

        return y;
    }
    
    public void setDriverX(int x)
    {
        MysqlCon m = new MysqlCon();
        try
        {
            Statement stmt = m.con.createStatement();
            stmt.execute("update mydb.drivers set x = " + x + " where username = " + "'" + username + "'");
            m.con.close();
        }
        catch(SQLException se){se.printStackTrace();}
    }
    
    public void setDriverY(int y)
    {
        MysqlCon m = new MysqlCon();
        try
        {
            Statement stmt = m.con.createStatement();
            stmt.execute("update mydb.drivers set y = " + y + " where username = " + "'" + username + "'");
            m.con.close();
        }
        catch(SQLException se){se.printStackTrace();}
    }
}
