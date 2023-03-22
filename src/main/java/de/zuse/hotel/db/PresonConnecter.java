package de.zuse.hotel.db;

import de.zuse.hotel.core.Address;
import de.zuse.hotel.core.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;



public class PresonConnecter implements DataBankFunktion {
    private EntityManager manager ;
    private EntityManagerFactory managerFactory;

    public PresonConnecter (){
        managerFactory  = Persistence.createEntityManagerFactory(JDBCConnecter.PERSISTENCE_NAME);
        manager = managerFactory.createEntityManager();
    }


    @Override
    public void dbCreate() {

    }

    public void dbCreate(Person person) {
        manager.getTransaction().begin();
        manager.persist(person);
        manager.getTransaction().commit();
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
