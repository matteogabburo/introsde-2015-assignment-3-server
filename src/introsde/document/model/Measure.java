package introsde.document.model;

import introsde.document.dao.Assignment3Dao;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Entity
@Table(name= "Measure")
@NamedQuery(name = "Measure.findAll", query = "SELECT l FROM Measure l")
@XmlRootElement
public class Measure implements Serializable
{
    @Id
    @GeneratedValue(generator="sqlite_measure")
    @TableGenerator(name="sqlite_measure", table="sqlite_sequence",
            pkColumnName="name", valueColumnName="seq",
            pkColumnValue="Measure")
    @Column(name="idMeasure")
    private Long idMeasure;
    @Column(name="mid")
    private Long mid;
    @Temporal(TemporalType.DATE)
    @Column(name="dateRegistered")
    private Date dateRegistered;
    @Column(name="measureType")
    private String measureType;
    @Column(name="measureValue")
    private String measureValue;
    @Column(name="measureValueType")
    private String measureValueType;    //String, integer, real
    @ManyToOne
    @JoinColumn(name="idPerson",referencedColumnName="idPerson")
    private Person person;

    //getters and setters
    public Long getMid() {return mid;}
    public void setMid(Long mid) {this.mid = mid;}
    public Date getDateRegistered() {return dateRegistered;}
    public void setDateRegistered(String dateRegistered) throws ParseException {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Date date = format.parse(dateRegistered);
        this.dateRegistered = date;
    }
    public String getMeasureType() {return measureType;}
    public void setMeasureType(String measureType) {this.measureType = measureType;}
    public String getMeasureValue() {return measureValue;}
    public void setMeasureValue(String measureValue) {this.measureValue = measureValue;}
    public String getMeasureValueType() {return measureValueType;}
    public void setMeasureValueType(String measureValueType) {this.measureValueType = measureValueType;}
    @XmlTransient
    public Person getPerson() {return person;}
    public void setPerson(Person person) {this.person = person;}

    public Long getIdMeasure() {
        return idMeasure;
    }
    public void setIdMeasure(Long idMeasure) {
        this.idMeasure = idMeasure;
    }
    public void setDateRegistered(Date dateRegistered) {
        this.dateRegistered = dateRegistered;
    }



    //DB methods
    public static List<Measure> getAll() {
        EntityManager em = Assignment3Dao.instance.createEntityManager();
        List<Measure> list = em.createNamedQuery("Measure.findAll", Measure.class).getResultList();
        Assignment3Dao.instance.closeConnections(em);
        return list;
    }

    public static List<Measure> readPersonHistory(Long id, String measureType){return Assignment3Dao.readPersonHistory(id, measureType);}
    public static List<String> readMeasureTypes(){return Assignment3Dao.readMeasureTypes();}

    public static Measure saveMeasure(Long id, Measure m){
        Person p = Assignment3Dao.getPersonById(id);
        m.setPerson(p);

        //set mid
        m.setMid(Measure.getMaxMid(p) + 1);

        return Assignment3Dao.saveMeasure(id, m);
    }

    private static Long getMaxMid(Person p)
    {
        return Assignment3Dao.getMaxMid(p.getId());
    }

    public static Measure updateMeasure(Long id, Measure p){return  Assignment3Dao.updateMeasure(id, p);}
    public static void deleteMeasure(Long id, Measure p){Assignment3Dao.deleteMeasure(id, p);}

    public static List<Measure> readPersonMeasure(Long id, String measureType, Long mid) {
        return Assignment3Dao.readPersonMeasure( id, measureType,mid);
    }
}