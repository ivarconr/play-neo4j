package no.osthus.domain;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.*;

import java.util.Set;

@NodeEntity
public class Company {
    @GraphId
    private Long id;

    @Indexed
    private Long orgId;

    private String name;

    @RelatedTo(type="WORKS_AT", direction = Direction.INCOMING)
    private Set<Person> employees;

    public Company(Long orgId, String name) {
        this.orgId = orgId;
        this.name = name;
    }

    public Company() {
    }

    public Long getId() {
        return id;
    }

    public Long getOrgId() {
        return orgId;
    }

    public String getName() {
        return name;
    }
}
