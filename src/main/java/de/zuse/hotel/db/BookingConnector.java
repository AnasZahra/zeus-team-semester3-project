package de.zuse.hotel.db;

import de.zuse.hotel.core.Address;
import de.zuse.hotel.core.Booking;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;


public class BookingConnector implements DataBankFunktion{
    private EntityManager manager ;
    private EntityManagerFactory managerFactory;


    public BookingConnector(){
        managerFactory  = Persistence.createEntityManagerFactory(JDBCConnecter.PERSISTENCE_NAME);
        manager = managerFactory.createEntityManager();
    }


    public void dbCreate(Booking booking) {
        manager.getTransaction().begin();
        manager.persist(booking);
        manager.getTransaction().commit();
    }

    @Override
    public void dbCreate() {

    }

    @Override
    public List<?> dbSerscheAll() {
        List<Address> addresses = manager.createNativeQuery("SELECT * FROM address", Address.class)
                .getResultList();
        return addresses;
    }

    @Override
    public List<?> dbSerscheforOne() {
        return null;
    }

    @Override
    public void dbRemoveAll() {

    }

    @Override
    public void dbRemoveOne() {

    }
}
