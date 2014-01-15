package no.osthus.play;

import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.helpers.collection.IteratorUtil;
import org.neo4j.kernel.impl.util.StringLogger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PersonRepository {

    private final GraphDatabaseService graphDb;
    private final ExecutionEngine engine;

    public PersonRepository(GraphDatabaseService graphDb) {
        this.graphDb = graphDb;
        this.engine =  new ExecutionEngine( graphDb, StringLogger.SYSTEM );
    }

    public long create(Person p) {
        Node n = null;
        try ( Transaction tx = graphDb.beginTx() ) {
            n = graphDb.createNode();
            n.setProperty( "name", p.getName());
            n.setProperty( "loginId", p.getLoginId());
            n.addLabel(p.TYPE);
            tx.success();
        }
        return n.getId();
    }

    public List<Person> all() {
        List<Person> persons =  new ArrayList<>();
        ExecutionResult result;

        try ( Transaction tx = graphDb.beginTx() ) {
            result = engine.execute( "MATCH (n:Person) RETURN n;" );
            Iterator<Node> resultNodes = result.javaColumnAs("n");
            for(Node node : IteratorUtil.asIterable(resultNodes)) {
                persons.add(
                        new Person(
                            node.getId(),
                            (String) node.getProperty("name"),
                            (Long)node.getProperty("loginId"))
                );
            }
            tx.success();
        }

        return persons;
    }
}