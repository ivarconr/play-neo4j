package no.osthus.play.domain;

import com.google.common.collect.Iterables;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;

import java.util.Map;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class CoffeeRepositoryTest extends IntegrationTestSupport {
    final String SERVER_ROOT_URI  = "http://localhost:7474";
    private CoffeeRepository coffeeRepository;
    private FarmRepository farmRepository;

    @Before
    public void setup_dependencies() {
        coffeeRepository = new CoffeeRepository(SERVER_ROOT_URI);
        farmRepository = new FarmRepository(SERVER_ROOT_URI);
    }

    @Test
    public void should_return_two_coffees() {
        //TODO: need more accurate verification.
        assertThat(Iterables.size(coffeeRepository.findAll()), greaterThanOrEqualTo(2));
    }

    @Test
    public void should_create_new_coffee() {
        Coffee saved = coffeeRepository.save("Test");
        assertThat(saved.getId(), greaterThan(0l));

        coffeeRepository.remove(saved);
    }

    @Test
    public void should_find_coffee() {
        Coffee saved   = coffeeRepository.save("Test");
        Coffee fetched = coffeeRepository.findOne(saved.getId()).get();

        assertThat(saved.getId(), is(fetched.getId()));
        assertThat(saved.getName(), is(fetched.getName()));
    }

    @Test
    public void should_remove_coffee() {
        Coffee saved   = coffeeRepository.save("Test");
        coffeeRepository.remove(saved);

        assertThat(coffeeRepository.findOne(saved.getId()).isPresent(), is(false));
    }

    @Test
    public void should_return_size_of_repo() {
        assertThat(coffeeRepository.count(), greaterThanOrEqualTo(2l));
    }

    @Test
    public void should_return_coffee_with_farm() {
        Coffee coffee = coffeeRepository.findAll().iterator().next();
        assertThat(coffee.getFarmedBy(), is(not(nullValue())));
    }

    @Test
    public void should_return_coffee_with_bean_type() {
        Coffee coffee = coffeeRepository.findAll().iterator().next();
        assertThat(Iterables.size(coffee.getBeans()), greaterThan(0));
    }

    @Test
    public void create_farmed_relation_ship() {
        Coffee c   = coffeeRepository.save("Coffee alala");
        Farm f = farmRepository.save("Farmville");
        coffeeRepository.createFarmedRelationship(c, f);

        Transaction transaction = null;
        try {
            transaction = grapdb.beginTx();
            ExecutionResult result = engine.execute("MATCH (c)-[:IS_FARMED_BY]-(f) return c, f");
            ResourceIterator<Map<String,Object>> mapResourceIterator = result.javaIterator();
            Node cNode = (Node)mapResourceIterator.next().get("c");
            Node fNode = (Node)mapResourceIterator.next().get("f");
            System.out.println(cNode.getProperty("name"));
            System.out.println(fNode.getProperty("name"));

        } finally {
            transaction.success();
        }

    }
}
