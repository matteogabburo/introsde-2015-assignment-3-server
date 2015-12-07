package introsde.document.endpoint;

import introsde.document.ws.PeopleImplementation;

import javax.xml.ws.spi.Provider;
import javax.xml.ws.Endpoint;
import java.net.InetAddress;

public class PeoplePublisher {
    public static String SERVER_URL = "https://guarded-anchorage-1835.herokuapp.com";
    public static String PORT = "6902";
    public static String BASE_URL = "/ws/people";

    public static String getEndpointURL() {
        return SERVER_URL+":"+PORT+BASE_URL;
    }

    public static void main(String[] args) {
        String endpointUrl = getEndpointURL();
        System.out.println("Starting People Service...");
        System.out.println("--> Published at = "+endpointUrl);
        Endpoint.publish(endpointUrl, new PeopleImplementation());
    }
}

