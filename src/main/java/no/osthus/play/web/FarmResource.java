package no.osthus.play.web;

import no.osthus.play.domain.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/farms")
public class FarmResource {
    private final Repository<Farm> repository;

    public FarmResource() {
        this.repository = new FarmRepository(System.getenv("GRAPHENEDB_URL"));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Iterable<Farm> all() {
        return repository.findAll();
    }
}
