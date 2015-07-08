package DataBase;

import java.sql.*;

/**
 * Created by kelvinec on 07/07/15.
 */
public class UserDataBase {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/tr2";

    //  Database credentials
    static final String USER = "postgres";
    static final String PASS = "123456789";

    public void create(UserEntity user) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            String sql;
            sql = "INSERT INTO users (username,password) VALUES(?,?);";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1,user.getUsername());
            stmt.setString(2,user.getPassword());
            stmt.execute();

            stmt.close();
            conn.close();
        } catch (SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public UserEntity retrieve(UserEntity user) {
        Connection conn = null;
        PreparedStatement stmt = null;
        UserEntity selectedUser = new UserEntity();

        try {
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            String sql;
            sql = "SELECT * FROM users WHERE username = ?;";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            ResultSet rs = stmt.executeQuery();
            rs.next();

            if(rs == null)
                return null;

            selectedUser.setUsername(rs.getString(1));
            selectedUser.setHashedPassword(rs.getString(2));

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return selectedUser;
    }

    public void delete(UserEntity user) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            String sql;
            sql = "DELETE FROM users WHERE username = ?;";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.execute();


            stmt.close();
            conn.close();
        } catch (SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}

