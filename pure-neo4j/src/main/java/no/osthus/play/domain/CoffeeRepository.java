package no.osthus.play.domain;

import org.neo4j.graphdb.*;
import org.neo4j.rest.graphdb.RestGraphDatabase;
import org.neo4j.rest.graphdb.entity.RestNode;
import org.neo4j.rest.graphdb.query.RestCypherQueryEngine;
import org.neo4j.rest.graphdb.util.QueryResult;

import java.net.URI;
import java.util.*;

import com.google.common.base.Optional;

public class CoffeeRepository {
    private final RestGraphDatabase gds;

    public CoffeeRepository(String SERVER_ROOT_URI) {
        String userInfo = URI.create(SERVER_ROOT_URI).getUserInfo();
        String[] credentials = new String[2];
        if(userInfo != null) {
            credentials = userInfo.split(":");
        }

        this.gds = new RestGraphDatabase(SERVER_ROOT_URI + "/db/data/", credentials[0], credentials[1]);
    }

    public boolean verifyConnection() {
        //TODO
        return true;
    }

    public List<Coffee> findAll() {
        List<Coffee> coffees = new ArrayList<>();

        final RestCypherQueryEngine engine = new RestCypherQueryEngine(gds.getRestAPI());
        QueryResult<Map<String,Object>> result = engine.query("MATCH (n:Coffee) return n;", map("id", Optional.<Long>absent()));

        System.out.println("query created");
        for (Map<String, Object> row : result) {
            RestNode node = (RestNode)row.get("n");
            String name = node.getProperty("name").toString();
            Long id = node.getId();
            coffees.add(new Coffee(id, name));
        }

        return coffees;
    }

    public Coffee save(Coffee coffee) {
        Node n = null;
        try ( Transaction tx = gds.beginTx() ) {
            n = gds.createNode();
            n.setProperty( "name", coffee.name);
            n.addLabel(DynamicLabel.label("Coffee"));
            tx.success();
        }
        return new Coffee(n.getId(), coffee.name);
    }

    public void delete(Coffee coffee) {
        final RestCypherQueryEngine engine = new RestCypherQueryEngine(gds.getRestAPI());
        engine.query("START n=node({id}) DELETE n;", map("id",coffee.id));
    }

    public Coffee findOne(Long id) {
        try {
            Node node = gds.getNodeById(id);
            return new Coffee(node.getId(), node.getProperty("name").toString());
        } catch (NotFoundException ex) {
            return null;
        }
    }

    private Map<String, Object> map(String key, Optional<Long> value) {
        Map<String, Object> values =  new HashMap<>();
        values.put(key, value.or(0L));
        return values;
    }
}
