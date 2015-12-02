package introsde.document.model;

import introsde.document.dao.Assignment3Dao;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name= "Measure")
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
    public void setDateRegistered(Date dateRegistered) {this.dateRegistered = dateRegistered;}
    public String getMeasureType() {return measureType;}
    public void setMeasureType(String measureType) {this.measureType = measureType;}
    public String getMeasureValue() {return measureValue;}
    public void setMeasureValue(String measureValue) {this.measureValue = measureValue;}
    public String getMeasureValueType() {return measureValueType;}
    public void setMeasureValueType(String measureValueType) {this.measureValueType = measureValueType;}
    public Person getPerson() {return person;}
    public void setPerson(Person person) {this.person = person;}

    //DB methods
    public static List<Measure> readPersonHistory(Long id, String measureType){return Assignment3Dao.readPersonHistory(id, measureType);}
    public static List<String> readMeasureTypes(){return Assignment3Dao.readMeasureTypes();}

    public static Measure saveMeasure(Long id, Measure p){return Assignment3Dao.saveMeasure(id, p);}
    public static Measure updateMeasure(Long id, Measure p){return  Assignment3Dao.updateMeasure(id, p);}
    public static void deleteMeasure(Long id, Measure p){Assignment3Dao.deleteMeasure(id, p);}
}