package DataBase;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by kelvinec on 08/07/15.
 */
public class FileDatabase {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/tr2";

    //  Database credentials
    static final String USER = "postgres";
    static final String PASS = "123456789";

    public void create(FileEntity file) {
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
            sql = "INSERT INTO files (file_name, path, ip_address, username)  VALUES(?,?, ?, ?);";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, file.getFile_name());
            stmt.setString(1, file.getPath());
            stmt.setString(1, file.getIp_address());
            stmt.setString(1, file.getUsername());
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

    public FileEntity retrieve(String fileName) {
        Connection conn = null;
        PreparedStatement stmt = null;
        FileEntity file = new FileEntity();

        try {
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            String sql;
            sql = "SELECT * FROM files WHERE file_name = ?;";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, fileName);
            ResultSet rs = stmt.executeQuery();
            rs.next();

            if(rs == null)
                return null;

            file.setFile_name(rs.getString(1));
            file.setPath(rs.getString(2));
            file.setIp_address(rs.getString(3));
            file.setUsername(rs.getString(4));

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
        return file;
    }

    public ArrayList<FileEntity> retrieveAllFilesOfUser(String username) {
        Connection conn = null;
        PreparedStatement stmt = null;
        FileEntity file = new FileEntity();

        try {
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            String sql;
            sql = "SELECT * FROM files WHERE username = ?;";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                file.setFile_name(rs.getString(1));
                file.setPath(rs.getString(2));
                file.setIp_address(rs.getString(3));
                file.setUsername(rs.getString(4));
            }

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
        return file;
    }

    public void delete(String fileName) {
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
            sql = "DELETE FROM files WHERE file_name = ?;";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, fileName);
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

    public void deleteAllFiles(String fileName) {
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
            sql = "DELETE FROM files;";
            stmt = conn.prepareStatement(sql);
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
