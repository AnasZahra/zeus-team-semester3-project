package de.zuse.hotel.db;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;


public class JDBCConnecter
{
    ////localhost/testdb
    //file:src/main/db/dbFiles
    private static final String DB_NAME = "jdbc:hsqldb:hsql://localhost/testdb";
    //src/main/resources/de/zuse/hotel/db/example
    private static final String USER_NAME = "SA";
    private static final String PASSWORD = "";
    public static final String PERSISTENCE_NAME = "ZuseHotel";

    private static Connection conn;


    public static void printDrivers()
    {
        final Enumeration<Driver> driver = DriverManager.getDrivers();

        while (driver.hasMoreElements())
        {
            System.out.println(driver.nextElement().getClass().getName());
        }
    }

    public static void getConnection() throws Exception
    {

        String dbTablesCreationCommando = readFile("src/main/resources/de/zuse/hotel/db/dbTablesCreation.sql");

        //return DriverManager.getConnection(DB_NAME, USER_NAME, PASSWORD);
        try{
            Class.forName("org.hsqldb.jdbcDriver");
        }catch (ClassNotFoundException e)
        {
            System.err.println("ERROR: failed to load HSQLDB JDBC driver.");
            return;
        }

        try{
            conn = DriverManager.getConnection(DB_NAME, USER_NAME, PASSWORD);


            //trash tables creation commando
            //conn.createStatement().executeUpdate(dbTablesCreationCommando);


        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            conn.close();
        }


    }

    public static String readFile(String fileName) throws Exception{

        File file = new File(fileName);
        String s = FileUtils.readFileToString(file, "utf-8");
        return s;

    }



}
