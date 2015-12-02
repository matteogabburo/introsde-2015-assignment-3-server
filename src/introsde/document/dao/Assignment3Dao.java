package introsde.document.dao;

/**
 * Created by matteo on 02/12/15.
 */

import introsde.document.model.Measure;
import introsde.document.model.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public enum Assignment3Dao {
    instance;

    private EntityManagerFactory emf;

    private Assignment3Dao()
    {
        if (emf!=null) {
            emf.close();
        }
        emf = Persistence.createEntityManagerFactory("assignment3-jpa");
    }

    public EntityManager createEntityManager() {return emf.createEntityManager();}
    public void closeConnections(EntityManager em) {em.close();}
    public EntityTransaction getTransaction(EntityManager em) {return em.getTransaction();}
    public EntityManagerFactory getEntityManagerFactory() {return emf;}

    //Queries for Person
    public static Person savePerson(Person p)
    {
        EntityManager em = Assignment3Dao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(p);
        tx.commit();
        Assignment3Dao.instance.closeConnections(em);
        return p;
    }
    public static Person updatePerson(Person p)
    {
        EntityManager em = Assignment3Dao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p = em.merge(p);
        tx.commit();
        Assignment3Dao.instance.closeConnections(em);
        return p;
    }
    public static void deletePerson(Person p)
    {
        EntityManager em = Assignment3Dao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        em.remove(p);
        tx.commit();
        Assignment3Dao.instance.closeConnections(em);
    }
    public static List<Person> getAll()
    {
        String query = "SELECT p FROM Person p";

        EntityManager em = Assignment3Dao.instance.createEntityManager();
        List<Person> list = em.createQuery(query, Person.class).getResultList();
        Assignment3Dao.instance.closeConnections(em);
        return list;
    }
    public static Person getPersonById(Long id)
    {
        String query = "SELECT p FROM Person p WHERE p.idPerson = "+id;

        EntityManager em = Assignment3Dao.instance.createEntityManager();
        Person p = em.createQuery(query, Person.class).getSingleResult();
        Assignment3Dao.instance.closeConnections(em);
        return p;
    }

    //Queries for Measure
    public static List<Measure> readPersonHistory(Long id, String measureType)
    {
        String query = "SELECT m FROM Measure m WHERE m.idPerson ="+ id + " AND m.measureType = "+measureType;

        EntityManager em = Assignment3Dao.instance.createEntityManager();
        List<Measure> list = em.createQuery(query, Measure.class).getResultList();
        Assignment3Dao.instance.closeConnections(em);
        return list;
    }

    public static List<String>readMeasureTypes()
    {
        String query = "SELECT DISTINCT m.measureType FROM Measure m";

        EntityManager em = Assignment3Dao.instance.createEntityManager();
        List<String> list = em.createQuery(query, String.class).getResultList();
        Assignment3Dao.instance.closeConnections(em);
        return list;
    }

    public static Measure saveMeasure(Long id, Measure p)
    {
        EntityManager em = Assignment3Dao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(p);
        tx.commit();
        Assignment3Dao.instance.closeConnections(em);
        return p;
    }
    public static Measure updateMeasure(Long id, Measure p)
    {
        EntityManager em = Assignment3Dao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p = em.merge(p);
        tx.commit();
        Assignment3Dao.instance.closeConnections(em);
        return p;
    }
    public static void deleteMeasure(Long id, Measure p)
    {
        EntityManager em = Assignment3Dao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        em.remove(p);
        tx.commit();
        Assignment3Dao.instance.closeConnections(em);
    }
}