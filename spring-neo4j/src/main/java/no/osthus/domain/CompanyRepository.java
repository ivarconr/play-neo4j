package no.osthus.domain;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CompanyRepository extends GraphRepository<Company> {

    <C extends Company> C save(C company);
}
