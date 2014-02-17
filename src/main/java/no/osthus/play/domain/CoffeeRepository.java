package no.osthus.play.domain;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import org.neo4j.graphdb.*;
import org.neo4j.rest.graphdb.RestGraphDatabase;
import org.neo4j.rest.graphdb.entity.RestNode;
import org.neo4j.rest.graphdb.query.QueryEngine;
import org.neo4j.rest.graphdb.query.RestCypherQueryEngine;
import org.neo4j.rest.graphdb.util.QueryResult;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoffeeRepository implements Repository<Coffee> {
    private final static Logger logger = LoggerFactory.getLogger(CoffeeRepository.class);
    private final RestGraphDatabase gds;
    private final QueryEngine engine;

    public CoffeeRepository(String SERVER_ROOT_URI) {
        String userInfo = URI.create(SERVER_ROOT_URI).getUserInfo();
        String[] credentials = new String[2];
        if(userInfo != null) {
            credentials = userInfo.split(":");
        }

        gds = new RestGraphDatabase(SERVER_ROOT_URI + "/db/data/", credentials[0], credentials[1]);
        engine = new RestCypherQueryEngine(gds.getRestAPI());
    }

    public Iterable<Coffee> findAll() {
        QueryResult<Map<String,Object>> result = engine.query("MATCH (n:Coffee) return n;", map("id", 0l));

        return Iterables.transform(result, new Function<Map<String, Object>, Coffee>() {
            @Override
            public Coffee apply(Map<String, Object> row) {
                RestNode node = (RestNode)row.get("n");
                return new Coffee(node);
            }
        });
    }

    @Override
    public Long count() {
        QueryResult<Map<String,Object>> result =
                engine.query("MATCH (n:Coffee) return count(n) as count", new HashMap<String, Object>());

        return Long.parseLong(result.iterator().next().get("count").toString());
    }

    public Coffee save(String name) {
        Node n = null;
        try ( Transaction tx = gds.beginTx() ) {
            n = gds.createNode();
            n.setProperty( "name", name);
            n.addLabel(DynamicLabel.label("Coffee"));
            tx.success();
        }
        return new Coffee(n);
    }

    public void remove(Coffee coffee) {
        engine.query("START n=node({id}) DELETE n;", map("id",coffee.getId()));
    }

    public Optional<Coffee> findOne(Long id) {
        Coffee found = null;
        try {
            Node node = gds.getNodeById(id);
            found = new Coffee(node);
        } catch (NotFoundException ex) {
            logger.warn("Did not find node using id {}", id, ex);
        } finally {
            return Optional.fromNullable(found);
        }
    }

    private Map<String, Object> map(String key, Long value) {
        Map<String, Object> values =  new HashMap<>();
        values.put(key, value);
        return values;
    }

    public void createFarmedRelationship(Coffee c, Farm f) {
        Node cNode = gds.getNodeById(c.getId());
        Node fNode = gds.getNodeById(f.getId());
        cNode.createRelationshipTo(fNode, DynamicRelationshipType.withName("IS_FARMED_BY"));
    }
}
