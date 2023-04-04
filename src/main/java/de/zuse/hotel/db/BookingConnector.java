package de.zuse.hotel.db;

import de.zuse.hotel.core.Booking;
import de.zuse.hotel.core.Payment;
import de.zuse.hotel.core.Person;
import de.zuse.hotel.util.ZuseCore;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


public class BookingConnector implements DatabaseOperations
{
    @Override
    public void dbCreate(Object object)
    {
        if (!(object instanceof Booking))
            ZuseCore.coreAssert(false, "object must be Booking");

        EntityManager manager = JDBCConnecter.getEntityManagerFactory().createEntityManager();
        Booking booking = (Booking) object;
        manager.getTransaction().begin();
        manager.persist(booking);
        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public List<Booking> dbsearchAll()
    {
        EntityManager manager = JDBCConnecter.getEntityManagerFactory().createEntityManager();
        manager.getTransaction().begin();
        List<Booking> allBooking = manager.createNativeQuery("SELECT * FROM Bookings", Booking.class)
                .getResultList();
        manager.getTransaction().commit();
        manager.close();

        return allBooking;
    }

    @Override
    public <T> T dbsearchById(int id)
    {
        EntityManager manager = JDBCConnecter.getEntityManagerFactory().createEntityManager();
        manager.getTransaction().begin();
        Booking booking = manager.find(Booking.class, id);
        manager.getTransaction().commit();
        manager.close();
        return (T) booking;
    }

    @Override
    public void dbRemoveAll()
    {
        dbsearchAll().forEach(new Consumer<Object>()
        {
            @Override
            public void accept(Object o)
            {
                dbRemoveById(((Booking) o).getBookingID()); // cast to Booking and get id
            }
        });
    }

    @Override
    public void dbRemoveById(int id)
    {
        Booking booking = dbsearchById(id);
        booking.cancelBooking();
        dbUpdate(booking);
    }

    @Override
    public void dbUpdate(Object object)
    {
        if (!(object instanceof Booking))
            ZuseCore.coreAssert(false, "object must be Booking");

        EntityManager manager = JDBCConnecter.getEntityManagerFactory().createEntityManager();
        Booking booking = (Booking) object;
        manager.getTransaction().begin();
        manager.merge(booking);
        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public List<Booking> dbSerschforanythinhg(Object searchFilter)
    {
        if (!(searchFilter instanceof BookingSearchFilter))
            ZuseCore.coreAssert(false, "the SearchFilter class you try to add is not BookingSearchFilter");

        BookingSearchFilter bookingSearchFilter = (BookingSearchFilter) searchFilter;
        EntityManager manager = JDBCConnecter.getEntityManagerFactory().createEntityManager();
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Booking> criteria = builder.createQuery(Booking.class);
        Root<Booking> bookingRoot = criteria.from(Booking.class);
        criteria.select(bookingRoot);

        List<Predicate> predicates = new ArrayList<>();

        if (bookingSearchFilter.roomNumber != null)
            predicates.add(builder.equal(bookingRoot.get("roomNumber"), bookingSearchFilter.roomNumber));

        if (bookingSearchFilter.floorNumber != null)
            predicates.add(builder.equal(bookingRoot.get("floorNumber"), bookingSearchFilter.floorNumber));

        if (bookingSearchFilter.startDate != null)
            predicates.add(builder.greaterThanOrEqualTo(bookingRoot.get("startDate"), bookingSearchFilter.startDate));

        if (bookingSearchFilter.endDate != null)
            predicates.add(builder.lessThanOrEqualTo(bookingRoot.get("endDate"), bookingSearchFilter.endDate));

        if (bookingSearchFilter.guest != null)
        {
            Join<Booking, Person> guestJoin = bookingRoot.join("guest");
            predicates.add(builder.equal(guestJoin.get("id"), bookingSearchFilter.guest.getId()));
        }

        if (bookingSearchFilter.payment != null)
        {
            Join<Booking, Payment> paymentJoin = bookingRoot.join("payment");
            predicates.add(builder.equal(paymentJoin.get("id"), bookingSearchFilter.payment.paymentID));
        }

        if (bookingSearchFilter.canceled != null)
            predicates.add(builder.equal(bookingRoot.get("canceled"), bookingSearchFilter.canceled));

        if (bookingSearchFilter.guestsNum != null)
            predicates.add(builder.equal(bookingRoot.get("guestsNum"), bookingSearchFilter.guestsNum));

        if (bookingSearchFilter.extraServices != null && !bookingSearchFilter.extraServices.isEmpty())
        {
            Join<Booking, List<String>> extraServicesJoin = bookingRoot.join("extraServices");
            predicates.add(extraServicesJoin.get("name").in(bookingSearchFilter.extraServices));
        }

        criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<Booking> query = manager.createQuery(criteria);

        return query.getResultList();
    }

    public List<Booking> dbSearchBookingBetween(LocalDate start, LocalDate end)
    {
        EntityManager manager = JDBCConnecter.getEntityManagerFactory().createEntityManager();
        List<Booking> bookings = manager.createNativeQuery(
                        "SELECT * FROM Bookings b " +
                                "WHERE b.Start_Date <= :searchEndDate " +
                                "AND b.End_Date >= :searchStartDate",
                        Booking.class)
                .setParameter("searchStartDate", start)
                .setParameter("searchEndDate", end)
                .getResultList();

        return bookings;
    }

}
