package no.osthus.play.domain;

import com.google.common.collect.Iterables;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CoffeeRepositoryTest extends IntegrationTestSupport {
    final String SERVER_ROOT_URI  = "http://localhost:7474";
    private CoffeeRepository coffeeRepository;

    @Before
    public void setup_dependencies() {
        coffeeRepository = new CoffeeRepository(SERVER_ROOT_URI);
    }

    @Test
    public void should_return_two_coffees() {
        //TODO: need more accurate verification.
        assertThat(Iterables.size(coffeeRepository.findAll()), greaterThanOrEqualTo(2));
    }

    @Test
    public void should_create_new_coffee() {
        Coffee saved = coffeeRepository.save(new Coffee("Test"));
        assertThat(saved.id.isPresent(), is(true));
    }

    @Test
    public void should_find_coffee() {
        Coffee saved   = coffeeRepository.save(new Coffee("Test"));
        Coffee fetched = coffeeRepository.findOne(saved.id.get()).get();

        assertThat(saved, is(fetched));
    }

    @Test
    public void should_remove_coffee() {
        Coffee saved   = coffeeRepository.save(new Coffee("Test"));
        coffeeRepository.remove(saved);

        assertThat(coffeeRepository.findOne(saved.id.get()).isPresent(), is(false));
    }

    @Test
    public void should_return_size_of_repo() {
        assertThat(coffeeRepository.count(), greaterThanOrEqualTo(2l));
    }
}
