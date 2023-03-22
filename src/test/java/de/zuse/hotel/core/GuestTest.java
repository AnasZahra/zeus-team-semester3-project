package de.zuse.hotel.core;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;


class GuestTest {
    private Address address;

    @BeforeEach
    public void init() {
        address = new Address("Syria", "Damascus", "burkan", 66534, 30);
    }

    @Test
    void testFirstname() {
        final String expectedName = "WoW";
         Guest guest = new Guest();
        guest.setFirstname("WoW");
        final String actualName = guest.getFirstname();
        assertEquals(expectedName, actualName);

        Assertions.assertThrows(Exception.class, () ->
        {
            guest.setLastname(null);
        });

        Assertions.assertThrows(Exception.class, () ->
        {
            guest.setLastname("");
        });

        Assertions.assertThrows(Exception.class, () ->
        {
            guest.setLastname("123");
        });

    }

    @Test
    void testtLastname() {
        final String expectedName = "WoW";
        Guest guest = new Guest();
        guest.setLastname(expectedName);
        final String actualName = guest.getLastname();
        assertEquals(expectedName, actualName);

        Assertions.assertThrows(Exception.class, () ->
        {
            guest.setLastname(null);
        });

        Assertions.assertThrows(Exception.class, () ->
        {
            guest.setLastname("");
        });

        Assertions.assertThrows(Exception.class, () ->
        {
            guest.setLastname("123");
        });
    }

    @Test
    void testId() {
        final int expectedId = 12348;
        Guest guest = new Guest();
        guest.setId(expectedId);
        final int actualId = guest.getId();
        assertEquals(expectedId, actualId);

        Assertions.assertThrows(Exception.class, () ->
        {
            guest.setId(-10);
        });

    }



    @Test
    void testBirthday() {
        final LocalDate expectedBirthday = LocalDate.of(1998, 2, 3);
        Guest guest = new Guest();
        guest.setBirthday(expectedBirthday);
        final LocalDate actualBirthday = guest.getBirthday();
        assertEquals(expectedBirthday, actualBirthday);

        Assertions.assertThrows(Exception.class, () ->
        {
            guest.setBirthday(LocalDate.of(2010, 1, 1));
        });

    }

    @Test
    void testEmail() {
        final String expectedEmail = "zeus@gmail.com";
        Guest guest = new Guest();
        guest.setEmail(expectedEmail);
        final String actualEmail = guest.getEmail();
        assertEquals(expectedEmail, actualEmail);

        Assertions.assertThrows(Exception.class, () ->
        {
            guest.setEmail(null);
        });
    }


    @Test
    void testTelNumber() {
        final String expectedTelNumber = "015214874571";
        Guest guest = new Guest();
        guest.setTelNumber(expectedTelNumber);
        final String actualTelNumber = guest.getTelNumber();
        assertEquals(expectedTelNumber, actualTelNumber);

        Assertions.assertThrows(Exception.class, () ->
        {
            guest.setTelNumber("0152178541");
        });
    }


    @Test
    void testAddress() {
        final Address expectedAddress = new Address("germany", "Saarbruecken", "Zweibruecker", 66954, 23);
        Guest guest = new Guest();
        guest.setAddress(expectedAddress);
        final Address actualAddress = guest.getAddress();
        assertEquals(expectedAddress, actualAddress);

        Assertions.assertThrows(Exception.class, () ->
        {
            guest.setAddress(null);
        });
    }

}


