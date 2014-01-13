package no.osthus;

import no.osthus.domain.Person;
import no.osthus.domain.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class InitDb {

    @Autowired
    public InitDb(PersonRepository repo) {
        Person p = new Person("test", "test", "me@mail.com", 123l);
        repo.save(p);
    }
}
