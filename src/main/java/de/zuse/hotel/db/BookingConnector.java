package de.zuse.hotel.db;

import de.zuse.hotel.core.Booking;
import de.zuse.hotel.core.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.function.Consumer;


public class BookingConnector implements DatabaseOperations
{
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
    public <T> T dbsearchById  (int id) {
        manager.getTransaction().begin();
        Booking booking = manager.find(Booking.class, id);
        manager.getTransaction().commit();
        manager.close();
        return (T) booking ;
    }

    @Override
    public void dbRemoveAll() {
        dbsearchAll().forEach(new Consumer<Object>() {
            @Override
            public void accept(Object o) {
                dbRemoveById(((Booking) o ).getBookingID()); // cast to Booking and get id
            }
        });
    }

    @Override
    public void dbRemoveById(int id) {
        Booking booking = dbsearchById(id);
        booking.canceledBooking();
        dbUpdate(booking);
    }

    @Override
    public void dbUpdate(Object object) {
        if(object instanceof Person) {
            Person person = (Person) object;
            System.out.println((Person) dbsearchById(person.getId()));
            manager.getTransaction().begin();
            manager.merge(person);
            manager.getTransaction().commit();
            manager.close();
            System.out.println((Person) dbsearchById(person.getId()));
        }
    }

    @Override
    public List<?> dbSerscheforanythinhg(String searchTerm) { // basel
        /*String query = "SELECT * FROM address WHERE ";
        query += "Id = ?1 OR ";
        query += "roomNumber = ?3 OR ";
        query += "floorNumber = ?3 OR ";
        query += "startDate = ?1 OR ";
        query += "endDate = ?1 OR ";
        query += "guest = ?2 OR ";
        query += "payment = ?2 OR ";

        Query nativeQuery = manager.createNativeQuery(query, Person.class);
        nativeQuery.setParameter(1, Integer.parseInt(searchTerm));
        nativeQuery.setParameter(2, "%" + searchTerm + "%");
        nativeQuery.setParameter(3, Integer.parseInt(searchTerm));
        nativeQuery.setParameter(4, Integer.parseInt(searchTerm));
        List<Person> result = nativeQuery.getResultList();
        return result;*/
        return null;
    }



}
