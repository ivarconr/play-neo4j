package no.osthus.play.domain;

import no.osthus.play.AppTest;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CoffeeRepositoryTest extends AppTest {
    final String SERVER_ROOT_URI  = "http://localhost:7474";
//    final String SERVER_ROOT_URI  = "http://test:vPoqq1rIfPNIenybRCXC@test.sb01.stations.graphenedb.com:24789";
    private CoffeeRepository coffeeRepository;

    @Before
    public void setup_dependencies() {
        coffeeRepository = new CoffeeRepository(SERVER_ROOT_URI);
    }

    @Test
    public void should_get_connectivity() {
        assertThat(coffeeRepository.verifyConnection(), is(true));
    }

    @Test
    public void should_return_two_coffees() {
        assertThat(coffeeRepository.findAll().size(), greaterThanOrEqualTo(2));
    }

    @Test
    public void should_create_new_coffee() {
        Coffee saved = coffeeRepository.save(new Coffee("Test"));
        assertThat(saved.id.isPresent(), is(true));
    }

    @Test
    public void should_find_coffee() {
        Coffee saved   = coffeeRepository.save(new Coffee("Test"));
        Coffee fetched = coffeeRepository.findOne(saved.id.get());

        assertThat(saved, is(fetched));
    }

    @Test
    public void should_remove_coffee() {
        Coffee saved   = coffeeRepository.save(new Coffee("Test"));
        coffeeRepository.delete(saved);

        assertThat( coffeeRepository.findOne(saved.id.get()), is(nullValue()));
    }
}
