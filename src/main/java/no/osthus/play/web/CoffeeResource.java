package no.osthus.play.web;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import no.osthus.play.View.CoffeeName;
import no.osthus.play.domain.Coffee;
import no.osthus.play.domain.CoffeeRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Coffee get(@PathParam("id") Long id) {
        return repository.findOne(id).orNull();
    }

    @GET
    @Path("/names")
    @Produces(MediaType.APPLICATION_JSON)
    public Iterable<CoffeeName> getNames() {
        return Iterables.transform(repository.findAll(), new Function<Coffee, CoffeeName>() {
            @Override
            public CoffeeName apply(Coffee coffee) {
                return new CoffeeName(coffee);
            }
        });
    }
}
