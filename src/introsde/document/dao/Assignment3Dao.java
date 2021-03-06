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
import java.util.ArrayList;
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
    {/*
        p = new Person();
        p.setId((long)151);
        p.setFirstname("Mario");
        p.setLastname("Rossi");*/

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
        EntityManager em = Assignment3Dao.instance.createEntityManager();
        Person p = em.find(Person.class, id);
        Assignment3Dao.instance.closeConnections(em);

        return p;
    }
    public static List<Measure>getCurrentHealth(Long id)
    {
        String query1 = "SELECT DISTINCT m.measureType FROM Measure m";

        EntityManager em = Assignment3Dao.instance.createEntityManager();
        List<String> types = em.createQuery(query1, String.class).getResultList();

        System.out.println("\n\n\n"+id+"\n\n\n");

        List<Measure> currentHealth = new ArrayList<>();
        String type;

        if(Assignment3Dao.countMeasure(id) > 0) {
            for (int i = 0; i < types.size(); i++) {
                type = types.get(i);

                String query2 = "SELECT l.idMeasure " +
                        " FROM Measure l " +
                        " WHERE idPerson = " + id + "" +
                        " AND " +
                        " l.dateRegistered = (" +
                        " SELECT MAX(m.dateRegistered) " +
                        " FROM Measure m" +
                        " WHERE m.measureType = \"" + type + "\" )";

                List<Number> tmp = (List<Number>) em.createNativeQuery(query2).getResultList();
                if (tmp != null)
                    currentHealth.add(Assignment3Dao.getMeasureById(tmp.get(0).longValue()));
            }
        }
        Assignment3Dao.instance.closeConnections(em);


        System.out.println("\n\n_______________________________________\n\n");
        for(int i = 0; i < currentHealth.size(); i++)
            System.out.println(currentHealth.get(i).getIdMeasure());
        System.out.println("\n\n_______________________________________\n\n");


        return currentHealth;
    }

    private static int countMeasure(Long id) {
        String query = "SELECT m FROM Measure m";// WHERE (m.idPerson ="+ id + ")";//AND (m.measureType = \'"+measureType+"\')";

        EntityManager em = Assignment3Dao.instance.createEntityManager();
        List<Measure> list = em.createQuery(query, Measure.class).getResultList();
        Assignment3Dao.instance.closeConnections(em);

        int counter = 0;
        for(int i = 0; i < list.size(); i++)
            if(list.get(i).getPerson().getId() == id)
                counter++;

        return counter;
    }

    private static Measure getMeasureById(Long id) {
        EntityManager em = Assignment3Dao.instance.createEntityManager();
        Measure m = em.find(Measure.class, id);
        Assignment3Dao.instance.closeConnections(em);

        return m;
    }

    //Queries for Measure
    public static List<Measure> readPersonHistory(Long id, String measureType)
    {
        String query = "SELECT m FROM Measure m";// WHERE (m.idPerson ="+ id + ")";//AND (m.measureType = \'"+measureType+"\')";

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
        System.out.println("*****************************************************************************");

        EntityManager em = Assignment3Dao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        em.remove(p);
        em.persist(p);
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

    public static List<Measure> getHealthHistory(Long id) {
        String query = "SELECT m FROM Measure m";// WHERE m.idPerson =\'"+ id+"\'";

        EntityManager em = Assignment3Dao.instance.createEntityManager();
        List<Measure> list = em.createQuery(query, Measure.class).getResultList();
        Assignment3Dao.instance.closeConnections(em);

        //Remove the unused things
        List<Measure> mList = new ArrayList<>();
        Measure m;
        for(int i = 0; i < list.size(); i++)
        {
            m = list.get(i);
            if(m.getPerson().getId() == id)
                mList.add(m);
        }

        return mList;
    }

    public static List<Measure> readPersonMeasure(Long id, String measureType, Long mid) {
        String query = "SELECT m FROM Measure m";//" WHERE m.idPerson ="+ id+" AND m.measureType = \'"+measureType+"\'" +
                //"AND m.mid = "+mid;
        EntityManager em = Assignment3Dao.instance.createEntityManager();
        List<Measure> list = em.createQuery(query, Measure.class).getResultList();
        Assignment3Dao.instance.closeConnections(em);


        //Remove the unused things
        List<Measure> mList = new ArrayList<>();
        Measure m;
        for(int i = 0; i < list.size(); i++)
        {
            m = list.get(i);
            if(m.getMid() == mid && m.getMeasureType() == measureType && m.getPerson().getId() == id)
                mList.add(m);
        }

        return list;
    }

    public static Long getMaxMid(Long id)
    {
        String query = "SELECT MAX(mid) FROM Measure WHERE idPerson = "+id;
        //"AND m.mid = "+mid;
        EntityManager em = Assignment3Dao.instance.createEntityManager();
        Long mid = ((Number) em.createNativeQuery(query).getSingleResult()).longValue();
        Assignment3Dao.instance.closeConnections(em);

        return mid;
    }
}
