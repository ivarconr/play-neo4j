package no.osthus;

import no.osthus.domain.Company;
import no.osthus.domain.CompanyRepository;
import no.osthus.domain.PersonRepository;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;

@Configuration
@ComponentScan
@EnableTransactionManagement
@EnableNeo4jRepositories
public class ApplicationConfig extends Neo4jConfiguration {
    @Autowired
    PersonRepository personRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    GraphDatabase graphDatabase;

    @Bean(destroyMethod = "shutdown")
    public GraphDatabaseService graphDatabaseService() {
        return new GraphDatabaseFactory().newEmbeddedDatabase("target/graph.db");
    }

    @PostConstruct
    public void initDb() {
       new InitDb(personRepository, companyRepository);
    }
}
