package de.zuse.hotel.db;

import de.zuse.hotel.core.Address;
import de.zuse.hotel.core.Booking;
import de.zuse.hotel.core.Person;

import javax.persistence.*;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Enumeration;
import java.sql.DriverManager;
import java.util.List;


public class JDBCConnecter
{
    private static final String DB_NAME = "jdbc:hsqldb:file:src/main/resources/de/zuse/hotel/db/example";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "root123";

    public static final String PERSISTENCE_NAME = "ZuseHotel";

    public static void printDrivers()
    {
        final Enumeration<Driver> driver = DriverManager.getDrivers();

        while (driver.hasMoreElements())
        {
            System.out.println(driver.nextElement().getClass().getName());
        }
    }

    public static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(DB_NAME, USER_NAME, PASSWORD);
    }

    public static void testWriteAndRead() throws Exception
    {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_NAME);
        EntityManager manager = factory.createEntityManager();


        // Read | Write | Update | Search
        {
            Address address = new Address("DE", "VK", "Street", 66333, 110);

            manager.getTransaction().begin();
            manager.persist(address);
            manager.getTransaction().commit();

            // Query for all persons
            List<Address> addresses = manager.createNativeQuery("SELECT * FROM address", Address.class)
                    .getResultList();

            System.out.println("\n\nResult("+addresses.size()+"): \n\n");
            addresses.forEach(System.out::println);
        }

        manager.close();
        factory.close();
    }

}
