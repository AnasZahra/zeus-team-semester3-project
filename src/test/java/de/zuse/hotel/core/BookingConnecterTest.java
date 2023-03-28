package de.zuse.hotel.core;
import static org.junit.jupiter.api.Assertions.*;
import de.zuse.hotel.db.BookingConnector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.zuse.hotel.core.Booking;

public class BookingConnectorTest {
    private BookingConnector connector;

    @BeforeEach
    void setUp() {
        connector = new BookingConnector();
    }

    @AfterEach
    void tearDown() {
        connector.dbRemoveAll();
    }

    @Test
    void testDbCreate() {
        Booking booking = new Booking();
        connector.dbCreate(booking);
        assertNotNull(booking.getId());
    }

    @Test
    void testDbSearchAll() {
        Booking booking1 = new Booking();
        connector.dbCreate(booking1);
        Booking booking2 = new Booking();
        connector.dbCreate(booking2);
        assertEquals(2, connector.dbsearchAll().size());
    }

    @Test
    void testDbSearchById() {
        Booking booking = new Booking();

        connector.dbCreate(booking);

        Booking retrievedBooking = connector.dbsearchById(booking.getId());
        assertEquals(booking.getId(), retrievedBooking.getId());
    }

    @Test
    void testDbRemoveAll() {
        Booking booking1 = new Booking();
        connector.dbCreate(booking1);
        Booking booking2 = new Booking();
        connector.dbCreate(booking2);
        connector.dbRemoveAll();
        assertEquals(0, connector.dbsearchAll().size());
    }

    @Test
    void testDbRemoveById() {
        Booking booking = new Booking();
        connector.dbCreate(booking);
        connector.dbRemoveById(booking.getId());
        assertNull(connector.dbsearchById(booking.getId()));
    }

    @Test
    void testDbUpdate() {
        Booking booking = new Booking();
        connector.dbCreate(booking);
        connector.dbUpdate(booking);
        Booking updatedBooking = connector.dbsearchById(booking.getId());

    }
}
