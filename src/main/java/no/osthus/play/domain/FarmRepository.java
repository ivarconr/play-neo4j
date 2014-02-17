package no.osthus.play.domain;

import com.google.common.base.Optional;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.rest.graphdb.RestGraphDatabase;
import org.neo4j.rest.graphdb.query.QueryEngine;
import org.neo4j.rest.graphdb.query.RestCypherQueryEngine;

import java.net.URI;

public class FarmRepository implements Repository<Farm> {
    private final RestGraphDatabase gds;
    private final QueryEngine engine;

    public FarmRepository(String serverBaseUri) {
        String userInfo = URI.create(serverBaseUri).getUserInfo();
        String[] credentials = new String[2];
        if(userInfo != null) {
            credentials = userInfo.split(":");
        }

        gds = new RestGraphDatabase(serverBaseUri + "/db/data/", credentials[0], credentials[1]);
        engine = new RestCypherQueryEngine(gds.getRestAPI());
    }

    public Farm save(String name) {
        Node n = null;
        try ( Transaction tx = gds.beginTx() ) {
            n = gds.createNode();
            n.setProperty( "name", name);
            n.addLabel(DynamicLabel.label("Coffee"));
            tx.success();
        }
        return new Farm(n);
    }

    @Override
    public Optional<Farm> findOne(Long id) {
        return null;
    }

    @Override
    public void remove(Farm entity) {

    }

    @Override
    public Iterable<Farm> findAll() {
        return null;
    }

    @Override
    public Long count() {
        return null;
    }
}
