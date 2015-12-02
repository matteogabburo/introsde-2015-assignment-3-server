package introsde.document.ws;

import introsde.document.model.Measure;
import introsde.document.model.Person;

import javax.jws.WebService;
import java.util.List;

/**
 * Created by matteo on 02/12/15.
 */

//for understand the methods you can go to People.interface, there every methods have a small explaination
@WebService(endpointInterface = "introsde.document.ws.PeopleImplementation")
public class PeopleImplementation implements People {
    @Override
    public List<Person> readPersonList() {return Person.getAll();}
    @Override
    public Person readPerson(Long id) {return Person.getPersonById(id);}
    @Override
    public Person updatePerson(Person person) {return Person.updatePerson(person);}
    @Override
    public Person createPerson(Person person) {return Person.savePerson(person);}
    @Override
    public void deletePerson(Long id) {Person.deletePerson(id);}

    @Override
    public List<Measure> readPersonHistory(Long id, String measureType) {
        return Measure.readPersonHistory(id, measureType);
    }
    @Override
    public List<String> readMeasureTypes() {
        return Measure.readMeasureTypes();
    }
    @Override
    public Measure savePersonMeasure(Long id, Measure m) {
        return Measure.saveMeasure(id, m);
    }
    @Override
    public Measure updatePersonMeasure(Long id, Measure m) {
        return Measure.updateMeasure(id, m);
    }
}
