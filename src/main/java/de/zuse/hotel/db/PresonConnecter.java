package de.zuse.hotel.db;

import de.zuse.hotel.core.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;



public class PresonConnecter implements DatabaseOperations
{
    private EntityManager manager ;
    private EntityManagerFactory managerFactory;

    public PresonConnecter (){
        managerFactory  = Persistence.createEntityManagerFactory(JDBCConnecter.PERSISTENCE_NAME);
        manager = managerFactory.createEntityManager();
    }


    @Override
    public void dbCreate(Object object) {
        if (object instanceof Person ){
            Person person = (Person) object;
        manager.getTransaction().begin();
        manager.persist(person);
        manager.getTransaction().commit();
        }
    }

    @Override
    public List<?> dbsearchAll() {
        List<Person> allPerson = manager.createNativeQuery("SELECT * FROM address", Person.class)
                .getResultList();
        return allPerson;
    }

    @Override
    public <T> T dbsearchById  (int id) {
        manager.getTransaction().begin();
        Person person = manager.find(Person.class, id);
        manager.getTransaction().commit();
        manager.close();
        return (T) person ;
    }

    @Override
    public void dbRemoveAll() {
        manager.getTransaction().begin();
        manager.createNativeQuery("INSERT INTO Person_trash_collection SELECT * FROM Person").executeUpdate();
        manager.createNativeQuery("DELETE FROM Person").executeUpdate();
        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public void dbRemoveById(int id) {
        manager.getTransaction().begin();
        manager.createNativeQuery("INSERT INTO Person_trash_collection SELECT * FROM Person WHERE Id = :id")
                .setParameter("id", id)
                .executeUpdate();
        manager.createNativeQuery("DELETE FROM Person WHERE Id = :id")
                .setParameter("id", id)
                .executeUpdate();
        manager.getTransaction().commit();
        manager.close();
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
    public List<?> dbSerscheforanythinhg(String searchTerm) { // jan 
        String query = "SELECT * FROM address WHERE ";
        query += "Id = ?1 OR ";
        query += "FirstName LIKE ?2 OR ";
        query += "LastName LIKE ?2 OR ";
        query += "birthday LIKE ?2 OR ";
        query += "Phone = ?3 OR ";
        query += "Email LIKE ?2";

        Query nativeQuery = manager.createNativeQuery(query, Person.class);
        nativeQuery.setParameter(1, Integer.parseInt(searchTerm));
        nativeQuery.setParameter(2, "%" + searchTerm + "%");
        nativeQuery.setParameter(3, Integer.parseInt(searchTerm));
        List<Person> result = nativeQuery.getResultList();
        return result;
    }

}
