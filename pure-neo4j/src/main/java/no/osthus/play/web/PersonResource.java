package no.osthus.play.web;

import no.osthus.play.domain.Person;
import no.osthus.play.domain.PersonRepository;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.rest.graphdb.RestGraphDatabase;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/persons")
public class PersonResource {
    private final PersonRepository personRepository;

    public PersonResource() {
        GraphDatabaseService gds = new RestGraphDatabase("http://localhost:7474/db/data");
        personRepository =  new PersonRepository(gds);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String all() {
        List<Person> persons = personRepository.all();
        StringBuilder builder = new StringBuilder();
        for(Person p : persons) {
            builder.append(p.toString());
            builder.append("\n");
        }
        return builder.toString();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String all(@PathParam("id") long id) {
        return "";
    }

    @GET
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public String create() {
        long id = personRepository.create(new Person("ola", 1l));
        return "created: " + id;
    }
}
