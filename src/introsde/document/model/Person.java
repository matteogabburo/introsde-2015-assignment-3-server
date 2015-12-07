package introsde.document.model;

import introsde.document.dao.Assignment3Dao;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name= "Person")
@XmlRootElement
public class Person implements Serializable
{
    @Id
    @GeneratedValue(generator="sqlite_person")
    @TableGenerator(name="sqlite_person", table="sqlite_sequence",
            pkColumnName="name", valueColumnName="seq",
            pkColumnValue="Person")
    @Column(name="idPerson")
    private Long idPerson;

    @Column(name="firstname")
    private String firstname;
    @Column(name="lastname")
    private String lastname;

    @OneToMany(mappedBy="person",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private List<Measure> currentHealth;    //one for each type measure
    @OneToMany(mappedBy="person",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private List<Measure> healthHistory;    //all measurements

    //Getters and Setters
    public Long getId() {return idPerson;}
    public void setId(Long id) {this.idPerson = id;}
    public String getFirstname() {return firstname;}
    public void setFirstname(String firstname) {this.firstname = firstname;}
    public String getLastname() {return lastname;}
    public void setLastname(String lastname) {this.lastname = lastname;}
    public List<Measure> getCurrentHealth() {
        return currentHealth;
    }
    public void setCurrentHealth(List<Measure> currentHealth) {
        this.currentHealth = Assignment3Dao.getCurrentHealth(this.getId());
    }
    public void setCurrentHealth() {this.currentHealth = Assignment3Dao.getCurrentHealth(this.getId());}
    public List<Measure> getHealthHistory() {
        return healthHistory;
    }
    public void setHealthHistory(List<Measure> healthHistory) {
        this.healthHistory = healthHistory;
    }

    //Queries
    public static Person savePerson(Person p)
    {
        return p = Assignment3Dao.savePerson(p);
    }
    public static Person updatePerson(Person p)
    {
        System.out.println("\n\n");
        System.out.println(p.getFirstname());
        System.out.println(p.getId());
        System.out.println("\n\n");

        return p = Assignment3Dao.updatePerson(p);
    }
    public static void deletePerson(Long id)
    {
       Assignment3Dao.deletePerson(Person.getPersonById(id));
    }
    public static List<Person> getAll()
    {

        return Assignment3Dao.getAll();
    }
    public static Person getPersonById(Long id){return Assignment3Dao.getPersonById(id);}

}