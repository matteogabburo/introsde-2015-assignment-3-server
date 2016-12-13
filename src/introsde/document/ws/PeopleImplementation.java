package introsde.document.ws;

import introsde.document.dao.Assignment3Dao;
import introsde.document.model.Measure;
import introsde.document.model.Person;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by matteo on 02/12/15.
 */

//for understand the methods you can go to People.interface, there every methods have a small explaination
@WebService(endpointInterface = "introsde.document.ws.People", serviceName="PeopleService")
public class PeopleImplementation implements People {
    @Override
    public List<Person> readPersonList() {

        List<Person> pList = Person.getAll();
        List<Person> res = new ArrayList<>();
        Person tmp;
        for(int i = 0; i < pList.size(); i++) {
            tmp = pList.get(i);
            tmp.setCurrentHealth(Assignment3Dao.getCurrentHealth(pList.get(i).getId()));
            tmp.setHealthHistory(Assignment3Dao.getHealthHistory(pList.get(i).getId()));
            res.add(tmp);
        }

        return res;
    }
    @Override
    public Person readPerson(Long id) {return Person.getPersonById(id);}
    @Override
    public Person updatePerson(Person person) {return Person.updatePerson(person);}
    @Override
    public Person createPerson(Person person) {return Person.savePerson(person);}
    @Override
    public void deletePerson(Long id)
    {
        //Measure.deleteMeasure(id, new Measure());
        Person.deletePerson(id);
    }

    @Override
    public List<Measure> readPersonHistory(Long id, String measureType) {
        return Measure.readPersonHistory(id, measureType);
    }
    @Override
    public List<String> readMeasureTypes() {
        return Measure.readMeasureTypes();
    }

    @Override
    public List<Measure> readPersonMeasures(Long id, String measureType, Long mid)
    {
        return Measure.readPersonMeasure( id,  measureType,  mid);
    }

    @Override
    public Measure savePersonMeasure(Long id, Measure m) {
        Person p = Assignment3Dao.getPersonById(id);
        m.setPerson(p);
        //set mid
        m.setMid(Measure.getMaxMid(p) + 1);

        return Measure.saveMeasure(id, m);
    }
    @Override
    public Measure updatePersonMeasure(Long id, Measure m) {

        Long mid = m.getMid();

        Measure.deleteMeasure(id, m);
        Person p = Assignment3Dao.getPersonById(id);
        m.setPerson(p);
        m.setMid(mid);

        //return Measure.saveMeasure(id, m);
        return Measure.updateMeasure(id, m);
    }
}
