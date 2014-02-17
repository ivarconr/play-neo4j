package no.osthus.play.domain;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;
import org.neo4j.graphdb.Transaction;
import org.neo4j.rest.graphdb.RestGraphDatabase;
import org.neo4j.rest.graphdb.entity.RestNode;
import org.neo4j.rest.graphdb.query.QueryEngine;
import org.neo4j.rest.graphdb.query.RestCypherQueryEngine;
import org.neo4j.rest.graphdb.util.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class FarmRepository implements Repository<Farm> {
    private final static Logger logger = LoggerFactory.getLogger(FarmRepository.class);
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
            n.addLabel(DynamicLabel.label("Farm"));
            tx.success();
        }
        return new Farm(n);
    }

    @Override
    public Optional<Farm> findOne(Long id) {
        Farm found = null;
        try {
            Node node = gds.getNodeById(id);
            found = new Farm(node);
        } catch (NotFoundException ex) {
            logger.warn("Did not find node using id {}", id, ex);
        } finally {
            return Optional.fromNullable(found);
        }
    }

    @Override
    public void remove(Farm entity) {

    }

    @Override
    public Iterable<Farm> findAll() {
        QueryResult<Map<String,Object>> result = engine.query("MATCH (n:Coffee) return n;", map("id", 0l));

        return Iterables.transform(result, new Function<Map<String, Object>, Farm>() {
            @Override
            public Farm apply(Map<String, Object> row) {
                return new Farm((Node) row.get("n"));
            }
        });
    }

    @Override
    public Long count() {
        return null;
    }

    private Map<String, Object> map(String key, Long value) {
        Map<String, Object> values =  new HashMap<>();
        values.put(key, value);
        return values;
    }
}
