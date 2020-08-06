package world;
import java.sql.*;  

class MysqlCon{
        Connection con;
        MysqlCon()
        {
            try
            {  
                Class.forName("com.mysql.jdbc.Driver");  
                con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","root");  
 //here mydb is database name, root is username and password    
            }
            catch(ClassNotFoundException | SQLException e){}   
        }
        /*
        public boolean login(String un, String pw)
        {
            String p = new String();
            try
            {
                Statement stmt=con.createStatement();  
                ResultSet rs = stmt.executeQuery("select * from mydb.users where username = " + "'" + un + "'"); 
                rs.next();
                p = rs.getString(2);
            }
            catch(SQLException se){}
            
            return pw.equals(p) && !p.equals("");
           
        }
        
        
        public void register(Customer customer)
        {
            try
            {
                Statement stmt=con.createStatement();
                stmt.execute("insert into mydb.users values('" + customer.username + "','" + customer.password + "','" + customer.name + "','" + customer.mobile + "','" + customer.email + "', 1, 0, 0)");
            }
            catch(SQLException se){se.printStackTrace();}
        }
        
        public boolean checkUsername(String un)
        {
            boolean t = true;
            try
            {
                Statement stmt=con.createStatement();  
                ResultSet rs = stmt.executeQuery("select count(*) from mydb.users where username = " + "'" + un + "'"); 
                rs.next();
                t = rs.getInt(1) == 0;
            }
            catch(SQLException se){se.printStackTrace();}
            return t;
        }
          
        public void fetchCustomer(Customer customer, String un)
        {
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM mydb.users where username = '" + un + "'");
                rs.next();
                customer.username = rs.getString("username");
                customer.name = rs.getString("name");
            } catch (SQLException se) {se.printStackTrace();}
        }
        
        public void fetchDriver(Driver driver, String un)
        {
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM mydb.drivers, mydb.users where mydb.users.username = mydb.drivers.username and mydb.users.username = '" + un + "'");
                rs.next();
                driver.username = rs.getString("username");
                driver.x = rs.getInt("x");
                driver.y = rs.getInt("y");
                driver.vehicleId = rs.getString("vehicle_id");
                driver.vehicleModel = rs.getString("model");
                driver.name = rs.getString("name");
                driver.mobile = rs.getString("mobile");
                driver.rating = rs.getDouble("rating");
                driver.nr = rs.getInt("nr");
            } catch (SQLException se) {se.printStackTrace();}
        }
        
        public void addRating(Driver driver, int x)
        {
            double t = driver.nr * driver.rating;
            driver.nr += 1;
            t += x;
            driver.rating = t/driver.nr;
            try
            {
                Statement stmt=con.createStatement();
                stmt.execute("update mydb.drivers set rating = " + driver.rating + ", nr = nr + 1 where username = " + "'" + driver.username + "'");
            }
            catch(SQLException se){se.printStackTrace();}
        }
        
        public void setDrivingState(Driver driver, int f)
        {
            try
            {
                Statement stmt=con.createStatement();
                stmt.execute("update mydb.drivers set driving = " + f + " where username = " + "'" + driver.username + "'");
            }
            catch(SQLException se){se.printStackTrace();}
        }
        
        
        public String searchCabs(int x, int y)
        {
            String un = new String();
            try
            {
                Statement stmt=con.createStatement();  
                ResultSet rs = stmt.executeQuery("select * , (ABS(x-"+x+") + ABS(y-"+y+")) as distance from mydb.drivers where driving = 0 order by distance asc, rating desc"); 
                rs.next();
                un = rs.getString("username");
            }
            catch(SQLException se){}
            
            return un;
        }
        
        public boolean driversAvailable()
        {
            boolean t = true;
            try
            {
                Statement stmt=con.createStatement();  
                ResultSet rs = stmt.executeQuery("select count(*) from mydb.drivers where driving = 0"); 
                rs.next();
                t = rs.getInt(1) != 0;
            }
            catch(SQLException se){se.printStackTrace();}
            return t;
        }
        
        public int getLocX(String loc)
        {
            loc = String.valueOf(loc.charAt(0));
            int x = 0;
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select * from mydb.locations where location = " + "'" + loc + "'");
                rs.next();
                x = Integer.parseInt(rs.getString(2));
            } catch (SQLException se) {}

            return x;
        }
        
        public int getLocY(String loc)
        {
            loc = String.valueOf(loc.charAt(0));
            int y = 0;
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select * from mydb.locations where location = " + "'" + loc + "'");
                rs.next();
                y = Integer.parseInt(rs.getString(3));
            } catch (SQLException se) {}

            return y;
        }
        
        public int getDriverX(String un)
        {
            int x = 0;
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select * from mydb.drivers where username = '" + un + "'");
                rs.next();
                x = rs.getInt("x");
            } catch (SQLException se) {se.printStackTrace();}

            return x;
        }
        
        public int getDriverY(String un)
        {
            int y = 0;
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select * from mydb.drivers where username = '" + un + "'");
                rs.next();
                y = rs.getInt("y");
            } catch (SQLException se) {}

            return y;
        }
        
        public void setDriverX(String un, int x)
        {
            try
            {
                Statement stmt=con.createStatement();
                stmt.execute("update mydb.drivers set x = " + x + " where username = " + "'" + un + "'");
            }
            catch(SQLException se){se.printStackTrace();}
        }
        
        public void setDriverY(String un, int y)
        {
            try
            {
                Statement stmt=con.createStatement();
                stmt.execute("update mydb.drivers set y = " + y + " where username = " + "'" + un + "'");
            }
            catch(SQLException se){se.printStackTrace();}
        }
        
        public double calcFare(String a, String b)
        {
            a = String.valueOf(a.charAt(0));
            b = String.valueOf(b.charAt(0));
            int x1 = getLocX(a);
            int y1 = getLocY(a);
            int x2 = getLocX(b);
            int y2 = getLocY(b);
            int distance = Math.abs(x2 - x1) + Math.abs(y2 - y1);
            double price = 0.5;
            return distance * price;
        }
        
        public double getBalance(String un)
        {
            double balance = 0;
            try
            {
                Statement stmt=con.createStatement();  
                ResultSet rs = stmt.executeQuery("select * from mydb.users where username = " + "'" + un + "'"); 
                rs.next();
                balance = rs.getDouble("balance");
            }
            catch(SQLException se){}
            
            return balance;
        }
        
        public void addMoney(String un, double money)
        {
            try
            {
                Statement stmt=con.createStatement();
                stmt.execute("update mydb.users set balance = balance + " + money + " where username = " + "'" + un + "'");
            }
            catch(SQLException se){se.printStackTrace();}
        }
        
        public void makePayment(Customer customer, Driver driver, double fare)
        {
            try
            {
                Statement stmt=con.createStatement();
                stmt.execute("update mydb.users set balance = balance - " + fare + " where username = " + "'" + customer.username + "'");
                stmt.execute("update mydb.users set balance = balance + " + fare + " where username = " + "'" + driver.username + "'");
            }
            catch(SQLException se){se.printStackTrace();}
        }
        
        public void reallocateDrivers()
        {
             try
            {
                Statement stmt=con.createStatement();
                stmt.execute("update mydb.drivers set x = xi, y = yi where driving = 0");
            }
            catch(SQLException se){se.printStackTrace();}
        }
        */
}