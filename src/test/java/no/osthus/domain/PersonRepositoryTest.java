package no.osthus.domain;

import com.google.common.collect.Lists;
import no.osthus.ApplicationConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.helpers.collection.IteratorUtil;
import org.neo4j.kernel.impl.util.StringLogger;
import org.neo4j.kernel.logging.LogbackService;
import org.neo4j.tooling.GlobalGraphOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import scala.collection.Iterator;

import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =  ApplicationConfig.class)
public class PersonRepositoryTest {
    Logger logger = LoggerFactory.getLogger(PersonRepositoryTest.class);

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private GraphDatabaseService db;

    private Neo4jOperations neo;

    @Test
    public void should_add_person_node() {
        Person ola = new Person("Ola", "Hansen", "me@mail.com", 123l);
        personRepository.save(ola);

        Node node = db.getNodeById(ola.getId());
        assertThat(node, is(not(nullValue())));
    }

    @Test
    public void should_add_person_node_with_props() {
        Person ola = new Person("Ola", "Hansen", "me@mail.com", 123l);
        personRepository.save(ola);

        ExecutionEngine engine = new ExecutionEngine(db, StringLogger.SYSTEM);

        ExecutionResult result;
        Transaction ignored = db.beginTx();
        try {
            result = engine.execute( "start n=node(*) where n.__type__ = 'no.osthus.domain.Person' RETURN n" );
            Iterator<Node> n_column = result.columnAs( "n" );

            Iterator<Node> ddds = IteratorUtil.asIterable(n_column);
             assertThat(result.toString(), is("Ola"));
        } finally {
            ignored.finish();
        }
    }
}
