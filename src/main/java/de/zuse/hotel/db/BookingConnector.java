package de.zuse.hotel.db;

import de.zuse.hotel.core.Booking;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;


public class BookingConnector implements DataBankOperation {
    private EntityManager manager ;
    private EntityManagerFactory managerFactory;


    public BookingConnector(){
        managerFactory  = Persistence.createEntityManagerFactory(JDBCConnecter.PERSISTENCE_NAME);
        manager = managerFactory.createEntityManager();
    }



    @Override
    public void dbCreate(Object object) {
        if (object instanceof Booking) {
            Booking booking = (Booking) object;
            manager.getTransaction().begin();
            manager.persist(booking);
            manager.getTransaction().commit();
        }
    }

    @Override
    public List<?> dbsearchAll() {
        List<Booking> allBooking = manager.createNativeQuery("SELECT * FROM Booking", Booking.class)
                .getResultList();
        return allBooking;
    }

    @Override
    public List<?> dbsearchById(int id) {
        List<Booking> oneBooking = manager.createNativeQuery("SELECT * FROM Booking WHERE Id = :id", Booking.class)
                .setParameter("id", id)
                .getResultList();
        return oneBooking;
    }

    @Override
    public void dbRemoveAll() {
        manager.getTransaction().begin();
        manager.createNativeQuery("INSERT INTO Booking_trash_collection SELECT * FROM Booking").executeUpdate();
        manager.createNativeQuery("DELETE FROM Booking").executeUpdate();
        manager.getTransaction().commit();
        manager.close();

    }

    @Override
    public void dbRemoveById(int id) {
        manager.getTransaction().begin();
        manager.createNativeQuery("INSERT INTO Booking_trash_collection SELECT * FROM Booking WHERE Id = :id")
                .setParameter("id", id)
                .executeUpdate();
        manager.createNativeQuery("DELETE FROM Booking WHERE Id = :id")
                .setParameter("id", id)
                .executeUpdate();
        manager.getTransaction().commit();
        manager.close();
    }


}
