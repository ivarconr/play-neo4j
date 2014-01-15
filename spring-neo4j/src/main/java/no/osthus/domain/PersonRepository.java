package no.osthus.domain;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PersonRepository extends GraphRepository<Person> {

    Person findOne(Long id);

    <C extends Person> C save(C person);
}