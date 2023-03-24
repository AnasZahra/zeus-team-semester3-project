package de.zuse.hotel.db;

import de.zuse.hotel.core.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;



public class PresonConnecter implements DataBankOperation {
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
    public List<?> dbsearchById(int id) {
        List<Person> onePerson = manager.createNativeQuery("SELECT * FROM Person WHERE Id = :id", Person.class)
                .setParameter("id", id)
                .getResultList();
        return onePerson;
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
}
