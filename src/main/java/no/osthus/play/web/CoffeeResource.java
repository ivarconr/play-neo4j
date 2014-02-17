package no.osthus.play.web;

import no.osthus.play.domain.Coffee;
import no.osthus.play.domain.CoffeeRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/coffees")
public class CoffeeResource {
    private final CoffeeRepository repository;

    public CoffeeResource() {
        this.repository = new CoffeeRepository(System.getenv("GRAPHENEDB_URL"));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Iterable<Coffee> all() {
        return repository.findAll();
    }
}
