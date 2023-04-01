package de.zuse.hotel.db;

import de.zuse.hotel.core.Person;
import de.zuse.hotel.util.ZuseCore;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;


public class PresonConnecter implements DatabaseOperations
{
    @Override
    public void dbCreate(Object object)
    {
        if (!(object instanceof Person))
            ZuseCore.coreAssert(false, "object must be Person");

        EntityManager manager = JDBCConnecter.getEntityManagerFactory().createEntityManager();
        Person person = (Person) object;
        manager.getTransaction().begin();
        manager.persist(person);
        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public List<?> dbsearchAll()
    {
        EntityManager manager = JDBCConnecter.getEntityManagerFactory().createEntityManager();
        manager.getTransaction().begin();

        List<Person> allPerson = manager.createNativeQuery("SELECT * FROM Person", Person.class)
                .getResultList();

        manager.getTransaction().commit();
        manager.close();
        return allPerson;
    }

    @Override
    public <T> T dbsearchById(int id)
    {
        EntityManager manager = JDBCConnecter.getEntityManagerFactory().createEntityManager();
        manager.getTransaction().begin();
        Person person = manager.find(Person.class, id);
        manager.getTransaction().commit();
        manager.close();
        return (T) person;
    }

    @Override
    public void dbRemoveAll()
    {
        EntityManager manager = JDBCConnecter.getEntityManagerFactory().createEntityManager();
        manager.getTransaction().begin();
        manager.createNativeQuery("INSERT INTO Person_trash_collection SELECT * FROM Person").executeUpdate();
        manager.createNativeQuery("DELETE FROM Person").executeUpdate();
        manager.getTransaction().commit();
        manager.close();
    }

    /**
     * Make sure by deleting a Guest to delete/cancel all his Booking(s) first!!! other way it will fail
     * @param id
     */
    @Override
    public void dbRemoveById(int id)
    {
        EntityManager manager = JDBCConnecter.getEntityManagerFactory().createEntityManager();
        manager.getTransaction().begin();

        manager.createNativeQuery("INSERT INTO Person_trash_collection SELECT * FROM Person WHERE Person_id = :id")
                .setParameter("id", id)
                .executeUpdate();

        manager.createNativeQuery("DELETE FROM Person WHERE Person_id = :id")
                .setParameter("id", id)
                .executeUpdate();

        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public void dbUpdate(Object object)
    {
        if (!(object instanceof Person))
            ZuseCore.coreAssert(false, "object must be Person");

        EntityManager manager = JDBCConnecter.getEntityManagerFactory().createEntityManager();
        Person person = (Person) object;
        System.out.println((Person) dbsearchById(person.getId()));
        manager.getTransaction().begin();
        manager.merge(person);
        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public List<Person> dbSerschforanythinhg(Object searchFilter)
    {
        if (!(searchFilter instanceof PersonSearchFilter))
            ZuseCore.coreAssert(false, "the SearchFilter class you try to add is not BookingSearchFilter");

        PersonSearchFilter personSearchFilter = (PersonSearchFilter) searchFilter;
        EntityManager manager = JDBCConnecter.getEntityManagerFactory().createEntityManager();
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Person> criteria = builder.createQuery(Person.class);
        Root<Person> personRoot = criteria.from(Person.class);
        criteria.select(personRoot);

        List<Predicate> predicates = new ArrayList<>();

        if (personSearchFilter.firstName != null)
            predicates.add(builder.equal(personRoot.get("firstName"), personSearchFilter.firstName));

        if (personSearchFilter.lastName != null)
            predicates.add(builder.equal(personRoot.get("lastName"), personSearchFilter.lastName));

        if (personSearchFilter.birthday != null)
            predicates.add(builder.greaterThanOrEqualTo(personRoot.get("birthday"), personSearchFilter.birthday));

        if (personSearchFilter.email != null)
            predicates.add(builder.lessThanOrEqualTo(personRoot.get("email"), personSearchFilter.email));

        if (personSearchFilter.telNumber != null)
            predicates.add(builder.equal(personRoot.get("telNumber"), personSearchFilter.telNumber));

        criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<Person> query = manager.createQuery(criteria);

        return query.getResultList();
    }
}
