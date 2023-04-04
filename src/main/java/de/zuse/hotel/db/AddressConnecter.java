package de.zuse.hotel.db;

import de.zuse.hotel.core.Address;
import de.zuse.hotel.util.ZuseCore;
import org.hibernate.cfg.NotYetImplementedException;

import javax.persistence.EntityManager;
import java.util.List;

public class AddressConnecter implements DatabaseOperations
{

    @Override
    public void dbCreate(Object object)
    {
        if (!(object instanceof Address))
            ZuseCore.coreAssert(false, "object must be Address");

        EntityManager manager = JDBCConnecter.getEntityManagerFactory().createEntityManager();
        Address address = (Address) object;
        manager.getTransaction().begin();
        manager.persist(address);
        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public List<Address> dbsearchAll()
    {
        EntityManager manager = JDBCConnecter.getEntityManagerFactory().createEntityManager();
        manager.getTransaction().begin();
        List<Address> oneAddresses = manager.createNativeQuery("SELECT * FROM Address", Address.class).getResultList();
        manager.getTransaction().commit();
        manager.close();
        return oneAddresses;
    }

    @Override
    public <T> T dbsearchById(int id)
    {
        EntityManager manager = JDBCConnecter.getEntityManagerFactory().createEntityManager();
        manager.getTransaction().begin();
        Address address = manager.find(Address.class, id);
        manager.getTransaction().commit();
        manager.close();
        return (T) address;
        // TODO: this need to be changed latter pls by Basel, should be safer
    }

    @Override
    public void dbRemoveAll()
    {
        EntityManager manager = JDBCConnecter.getEntityManagerFactory().createEntityManager();
        manager.getTransaction().begin();
        manager.createNativeQuery("INSERT INTO Address_trash_collection SELECT * FROM Address").executeUpdate();
        manager.createNativeQuery("DELETE FROM Address").executeUpdate();
        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public void dbRemoveById(int id)
    {
        EntityManager manager = JDBCConnecter.getEntityManagerFactory().createEntityManager();
        manager.getTransaction().begin();

        manager.createNativeQuery("INSERT INTO Address_trash_collection SELECT * FROM Address WHERE Address_id = :id")
                .setParameter("id", id)
                .executeUpdate();

        manager.createNativeQuery("DELETE FROM Address WHERE Address_id = :id")
                .setParameter("id", id)
                .executeUpdate();

        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public void dbUpdate(Object object)
    {
        if (!(object instanceof Address))
            ZuseCore.coreAssert(false, "object must be Address");

        EntityManager manager = JDBCConnecter.getEntityManagerFactory().createEntityManager();
        Address address = (Address) object;
        System.out.println((Address) dbsearchById(address.getId()));
        manager.getTransaction().begin();
        manager.merge(address);
        manager.getTransaction().commit();
        manager.close();

    }

    @Override
    public List<Address> dbSerschforanythinhg(Object searchFilter)
    {
        throw new NotYetImplementedException(); // we do not Support filter for address yet
    }
}
