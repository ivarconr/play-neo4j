package no.osthus.play.domain;


import com.google.common.collect.Lists;
import no.osthus.play.domain.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.impl.util.StringLogger;
import org.neo4j.test.TestGraphDatabaseFactory;

import java.util.Iterator;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class PersonRepositoryTest {
    private PersonRepository personRepository;
    private GraphDatabaseService graphDb;


    @Before
    public void prepareTestDatabase() {
        graphDb = new TestGraphDatabaseFactory().newImpermanentDatabase();
        personRepository = new PersonRepository(graphDb);

    }

    @After
    public void destroyTestDatabase() {
        graphDb.shutdown();
    }

    @Test
    public void should_create_person_node_with_name_property() {
        Person p = new Person("Ola Hansen", 123l);

        long nodeId = personRepository.create(p);

        try ( Transaction tx = graphDb.beginTx() ){
            Node foundNode = graphDb.getNodeById(nodeId);
            assertThat( foundNode.getId(), is( nodeId ) );
            assertThat( (String) foundNode.getProperty( "name" ), is( p.getName() ) );
            tx.success();
        }
    }

    @Test
    public void should_create_person_node_with_person_label() {
        personRepository.create(new Person("Ola Hansen", 123l));
        personRepository.create(new Person("Per Heimly", 22l));

        ExecutionEngine engine = new ExecutionEngine( graphDb, StringLogger.SYSTEM );

        ExecutionResult result;
        List<Node> nodes;
        try ( Transaction ignored = graphDb.beginTx() ) {
            result = engine.execute( "MATCH (n:Person) RETURN n;" );

            Iterator<Node> resultNodes = result.javaColumnAs("n");
            nodes = Lists.newArrayList(resultNodes);

            assertNotNull(nodes);
            assertThat(nodes.size(), is(2));
            assertThat(nodes.get(1).hasLabel(Person.TYPE), is(true));
        }
    }

    @Test
    public void shoul_return_all_persons() {
        personRepository.create(new Person("Ola Hansen", 123l));
        personRepository.create(new Person("Per Heimly", 22l));

        List<Person> persons = personRepository.all();
        assertThat(persons.size(), is(2));
    }

    @Test
    public void should_have_work_in_company_relationship() {
        Company company = new Company("Selskapet", 123l);
        new CompanyRepository(graphDb).create(company);

        Person ola = new Person("Ola Hansen", 123l);
        ola.addRelationship(new WorksAtRelationship(ola, company, 1985));
        /*long nodeId = personRepository.create(ola);



        try ( Transaction tx = graphDb.beginTx() ){
            Node foundNode = graphDb.getRelationshipTypes(nodeId);
            assertThat( foundNode.getId(), is( nodeId ) );
            assertThat( (String) foundNode.getProperty( "name" ), is( p.getName() ) );
            tx.success();
        }*/

    }

}
