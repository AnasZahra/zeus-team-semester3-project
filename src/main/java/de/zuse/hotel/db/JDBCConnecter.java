package de.zuse.hotel.db;

import org.apache.commons.io.FileUtils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;


public class JDBCConnecter
{
    //jdbc:hsqldb:hsql://localhost/testdb
    private static final String DB_NAME = "jdbc:hsqldb:file:src/main/resources/de/zuse/hotel/db/example";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "root123";
    public static final String PERSISTENCE_NAME = "ZuseHotel";
    private static Connection conn;
    private static EntityManagerFactory factory;


    public static void printDrivers()
    {
        final Enumeration<Driver> driver = DriverManager.getDrivers();

        while (driver.hasMoreElements())
        {
            System.out.println(driver.nextElement().getClass().getName());
        }
    }

    public static Connection getConnection() throws Exception
    {
        conn = DriverManager.getConnection(DB_NAME, USER_NAME, PASSWORD);
        return conn;
    }

    public static void shutdown() throws Exception
    {
        conn.close();
        factory.close();
    }

    public static EntityManagerFactory getEntityManagerFactory()
    {
        try
        {
            if (factory == null)
            {
                factory = Persistence.createEntityManagerFactory(PERSISTENCE_NAME);
            }
            return factory;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
