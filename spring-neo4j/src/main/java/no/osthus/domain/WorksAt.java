package no.osthus.domain;

import org.springframework.data.neo4j.annotation.*;


@RelationshipEntity(type = "WORKS_AT")
public class WorksAt {
    @GraphId Long id;

    @StartNode Person person;

    @Fetch
    @EndNode Company company;

    int startYear;

    public WorksAt(Person person, Company company, int startYear) {
        this.person = person;
        this.company = company;
        this.startYear = startYear;
    }

    public WorksAt() {
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }

    public int getStartYear() {
        return startYear;
    }
}
