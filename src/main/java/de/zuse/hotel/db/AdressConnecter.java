package de.zuse.hotel.db;

import de.zuse.hotel.core.Address;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class AdressConnecter implements DataBankFunktion  {
    private EntityManager manager ;
    private EntityManagerFactory managerFactory;


    public AdressConnecter (){
        managerFactory  = Persistence.createEntityManagerFactory(JDBCConnecter.PERSISTENCE_NAME);
        manager = managerFactory.createEntityManager();
    }

    public void dbcreate (Address address){
        manager.getTransaction().begin();
        manager.persist(address);
        manager.getTransaction().commit();
    }


    public List<Address> dbserscheAll (){
        List<Address> addresses = manager.createNativeQuery("SELECT * FROM address", Address.class)
                .getResultList();


        return addresses;


    }

    @Override
    public void dbCreate() {

    }

    @Override
    public List<?> dbSerscheAll() {
        return null;
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
