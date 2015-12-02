package introsde.document.ws;

/**
 * Created by matteo on 02/12/15.
 */

import introsde.document.model.Measure;
import introsde.document.model.Person;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import java.util.List;


@WebService
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL) //optional
public interface People {

    //Methods for Person ************************************************

    //Method #1
    //return all the people in the DB
    @WebMethod(operationName="readPersonList")
    @WebResult(name = "people")
    List<Person> readPersonList();

    //Method #2
    //read a person with given ID
    @WebMethod(operationName = "readPerson")
    @WebResult(name = "person")
    Person readPerson(Long id);

    //Method #3
    //update the personal information of th person identified by {id}
    @WebMethod(operationName = "updatePerson")
    @WebResult(name = "person")
    Person updatePerson(Person person);

    //Method #4
    //create a new person and return the newly created person with assigned id
    @WebMethod(operationName = "createPerson")
    @WebResult(name = "person")
    Person createPerson(Person person);

    //Method #5
    //delete Person identified by id
    @WebMethod(operationName = "deletePerson")
    void deletePerson(Long id);

    //Methods for Measures ************************************************

    //Method #6
    //return the list of values of measure for a person with a given id
    @WebMethod(operationName = "readPersonHistory")
    @WebResult(name = "history")
    List<Measure> readPersonHistory(Long id, String measureType);

    //Method #7
    //should return the list of Measures
    @WebMethod(operationName = "readMeasureTypes")
    @WebResult(name = "measures")
    List<String>readMeasureTypes();

    //Method #8
    //should return the value of {measureType} identified by {mid} for person identified by {id}
    @WebMethod(operationName = "savePersonMeasure")
    @WebResult(name = "measure")
    Measure savePersonMeasure(Long id, Measure measure);

    //Method #9
    //should update the measure identified with {m.mid} related to the person identified by {id}
    @WebMethod(operationName = "updatePersonMeasure")
    @WebResult(name = "measure")
    Measure updatePersonMeasure(Long id, Measure m);
}
