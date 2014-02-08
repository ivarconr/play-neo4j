package no.osthus.play.domain;

import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FarmRepositoryTest extends IntegrationTestSupport {
    final String SERVER_ROOT_URI  = "http://localhost:7474";
    private FarmRepository farmRepository;

    @Before
    public void setupRepo() {
        farmRepository = new FarmRepository(SERVER_ROOT_URI);
    }

    @Test
    public void should_create_farm() {
        Farm farm = new Farm("Test");
        Farm stored = farmRepository.save(farm);

        assertThat(stored.getName(), is(farm.getName()));
    }


}
