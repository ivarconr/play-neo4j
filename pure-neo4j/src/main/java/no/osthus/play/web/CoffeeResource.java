package no.osthus.play.web;

import no.osthus.play.domain.Coffee;
import no.osthus.play.domain.CoffeeRepository;
import no.osthus.play.domain.Person;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/coffees")
public class CoffeeResource {
    private final CoffeeRepository repository;

    public CoffeeResource() {
        this.repository = new CoffeeRepository(System.getenv("GRAPHENEDB_URL"));
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String all() {
        List<Coffee> coffees = repository.findAll();
        StringBuilder builder = new StringBuilder();
        for(Coffee c : coffees) {
            builder.append(c.toString());
            builder.append("\n");
        }
        return builder.toString();
    }
}
