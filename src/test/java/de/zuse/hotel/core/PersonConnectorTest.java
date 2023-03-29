package de.zuse.hotel.core;
import org.testng.annotations.Test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import de.zuse.hotel.core.Person;
import de.zuse.hotel.db.PresonConnecter;

public class PresonConnecterTest {
    private PresonConnecter connecter = new PresonConnecter();

    @Test
    public void testDbCreate() {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        connecter.dbCreate(person);
        List<Person> persons = (List<Person>) connecter.dbsearchAll();
        assertEquals(1, persons.size());
        assertEquals("John", persons.get(0).getFirstName());
        assertEquals("Doe", persons.get(0).getLastName());
    }

    @Test
    public void testDbsearchAll() {
        Person person1 = new Person();
        person1.setFirstName("John");
        person1.setLastName("Doe");
        connecter.dbCreate(person1);

        Person person2 = new Person();
        person2.setFirstName("Jane");
        person2.setLastName("Doe");
        connecter.dbCreate(person2);

        List<Person> persons = (List<Person>) connecter.dbsearchAll();
        assertEquals(2, persons.size());
    }

    @Test
    public void testDbsearchById() {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        connecter.dbCreate(person);

        int id = person.getId();
        Person foundPerson = connecter.dbsearchById(id);

        assertEquals(id, foundPerson.getId());
        assertEquals("John", foundPerson.getFirstName());
        assertEquals("Doe", foundPerson.getLastName());
    }

    @Test
    public void testDbRemoveAll() {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        connecter.dbCreate(person);

        connecter.dbRemoveAll();

        List<Person> persons = (List<Person>) connecter.dbsearchAll();
        assertEquals(0, persons.size());
    }

    @Test
    public void testDbRemoveById() {
        Person person1 = new Person();
        person1.setFirstName("John");
        person1.setLastName("Doe");
        connecter.dbCreate(person1);

        Person person2 = new Person();
        person2.setFirstName("Jane");
        person2.setLastName("Doe");
        Object dbCreate = connecter.dbCreate;
    }
}

