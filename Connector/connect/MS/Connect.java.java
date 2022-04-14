package MS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.DatabaseMetaData; 
import java.sql.ResultSet;  

   
    
    
public class Connect {  
     /*
     *First MAking a Database File in the desired Folder
     */ 

     // FileName can be anything that we want to input
    public static void createNewDatabase(String fileName) {  
   
        // Having a variable URL
        String url = "jdbc:sqlite:C:/sqlite/DB/" + fileName;  
   
        try {  
            Connection conn = DriverManager.getConnection(url);  
            if (conn != null) {  
                DatabaseMetaData meta = conn.getMetaData();  
                System.out.println("The driver name is " + meta.getDriverName());  
                System.out.println("A new database has been created.");  
            }  
   
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    }


    /* Creating a Connection Checker class to find if the database is present or not*/

    public static void connect() {  
        Connection conn = null;  
        try {  
            // db parameters  
            String url = "jdbc:sqlite:C:/sqlite/DB/movie.db";  
            // create a connection to the database  
            conn = DriverManager.getConnection(url);  
              
            System.out.println("Connection to SQLite has been established.");  
              
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        } finally {  
            try {  
                if (conn != null) {  
                    conn.close();  
                }  
            } catch (SQLException ex) {  
                System.out.println(ex.getMessage());  
            }  
        }  
    }
    
    public static void createNewTable() {  
        // SQLite connection string  
        String url = "jdbc:sqlite:C://sqlite/DB/movie.db";  
          
        // SQL statement for creating a new table  

        /*
        *With primary key as id, and constraints for the year
        */ 

        String sql = "CREATE TABLE moviedetail " +
        "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
        " mname VARCHAR(255) NOT NULL, " + 
        " actor VARCHAR(255), " +
        " actress VARCHAR(255), " +
        " director VARCHAR(255) NOT NULL, " + 
        " year INTEGER NOT NULL CHECK)";
          
        try{  
            Connection conn = DriverManager.getConnection(url);  
            Statement stmt = conn.createStatement();  
            stmt.execute(sql);  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    }    

    /* A private class to provide connection to SQL QUERIES only*/ 

    private Connection konnect() {  
        // SQLite connection string  
        String url = "jdbc:sqlite:C://sqlite/DB/movie.db";  
        Connection conn = null;  
        try {  
            conn = DriverManager.getConnection(url);  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
        return conn;  
    }  
    
    /* INSERING the values into the table that we created with 5 variables*/ 

    public void insert(String mname, String actor,String actress,String director,int year) {  
        String sql = "INSERT INTO moviedetail(mname, actor, actress, director,year) VALUES(?,?,?,?,?)";  
   
        try{  
            Connection conn = this.konnect();  
            PreparedStatement pstmt = conn.prepareStatement(sql);  
            pstmt.setString(1, mname);
            pstmt.setString(2, actor);
            pstmt.setString(3, actress);
            pstmt.setString(4, director);  
            pstmt.setInt(5, year);  
            pstmt.executeUpdate();  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    }  

    /*TO display every row in the movie review table*/
    
    public void selectAll(){  

        /*SQL query for selecting everything*/ 
        String sql = "SELECT * FROM moviedetail";  
          
        try {  
            Connection conn = this.konnect();  
            Statement stmt  = conn.createStatement();  
            ResultSet rs    = stmt.executeQuery(sql);  
              
            // loop through the result set  
            while (rs.next()) {  
                System.out.println(rs.getInt("id") +  "\t" +   
                                   rs.getString("mname") + "\t" + 
                                   rs.getString("actor") + "\t" +
                                   rs.getString("actress") + "\t" +
                                   rs.getString("director") + "\t" + 
                                   rs.getInt("year"));  
            }  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    }  
    
    /*To display very specific movies with a particular actor*/

    public void selectActor(){  

        // SQL query to select the name of the actor and find the movie he is related to
        String sql = "SELECT * FROM moviedetail WHERE actor ='Oscar Issac'";  
          
        try {  
            Connection conn = this.konnect();  
            Statement stmt  = conn.createStatement();  
            ResultSet rs    = stmt.executeQuery(sql);  
              
            // loop through the result set  
            while (rs.next()) {  
                System.out.println(rs.getInt("id") +  "\t" +   
                                   rs.getString("mname") + "\t" + 
                                   rs.getString("actor") + "\t" +
                                   rs.getString("actress") + "\t" +
                                   rs.getString("director") + "\t" + 
                                   rs.getInt("year"));  
            }  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    }  

    public static void main(String[] args) {  

        //Creating a database of the name movies1

        createNewDatabase("movie.db");
        connect();  
        createNewTable();
        
        Connect app = new Connect();
        

        // Inserting the values into the table using the insert class made above
        app.insert("Inception","Leonardo DiCaprio","Elliot Page","Christopher Nolen",2010);
        app.insert("Interstellar","MAtthew McConaughey","Anne Hathway","Christopher Nolen",2014);
        app.insert("The ShawShank Redemption","Andy Dufresne","Renee Blaine","Frank Darabont",1994);
        app.insert("Ex Machina","Oscar Issac","Alicia Vikander","ALex GArland",2014);
        app.insert("The Humger Games","Sam Claflin","Jennifer Lawrence","Garry Ross",2012);
        

        //SQL queries for showing the rows in the cmd or the output
        app.selectAll(); 
        app.selectActor();
    }  
}  
