package de.zuse.hotel.core;
import de.zuse.hotel.core.Address;
import de.zuse.hotel.db.AdressConnecter;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.List;

public class AddressConnecterTest {

    private AdressConnecter connector;

    @BeforeEach
    void setUp() {
        connector = new AdressConnecter();
    }

    @AfterEach
    void tearDown() {
        connector.dbRemoveAll();
    }

    @Test
    void dbCreate() {
        Address address = new Address("Syria", "Damascus", "aboromane", "66534", 30);
        connector.dbCreate(address);
        List<?> result = connector.dbsearchAll();
        assertEquals(1, result.size());
    }

    @Test
    void dbsearchAll() {
        Address address1 = new Address("Syria", "Damascus", "aboromane", "66534", 30);
        Address address2 = new Address("Syria", "Damascus", "aboromane", "66534", 30);
        connector.dbCreate(address1);
        connector.dbCreate(address2);
        List<?> result = connector.dbsearchAll();
        assertEquals(2, result.size());
    }
    @Test
    void dbsearchById() {
        Address address = new Address("Syria", "Damascus", "aboromane", "66534", 30);
        connector.dbCreate(address);
        Address result = connector.dbsearchById(address.getId());
        assertEquals(address, result);
    }

    @Test
    void dbRemoveAll() {

        Address address1 = new Address("Syria", "Damascus", "aboromane", "66534", 30);
        Address address2 = new Address("Syria", "Damascus", "aboromane", "66534", 30);
        connector.dbCreate(address1);
        connector.dbCreate(address2);
        connector.dbRemoveAll();
        List<?> result = connector.dbsearchAll();
        assertEquals(0, result.size());
    }

    @Test
    void dbRemoveById() {
        Address address = new Address("Syria", "Damascus", "aboromane", "66534", 30);
        connector.dbCreate(address);
        connector.dbRemoveById(address.getId());
        Address result = connector.dbsearchById(address.getId());
        assertNull(result);
    }

    @Test
    void dbUpdate() {
        Address address = new Address("Syria", "Damascus", "aboromane", "66534", 30);
        connector.dbCreate(address);
        address.setCity("Damascus");
        connector.dbUpdate(address);
        Address result = connector.dbsearchById(address.getId());
        assertEquals("Damascus", result.getCity());
    }

    @Test
    void dbSerscheforanythinhg() {

        Address address1 = new Address("Syria", "Damascus", "aboromane", "66534", 30);
    }
}

