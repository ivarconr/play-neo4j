package no.osthus.domain;

import java.util.HashSet;
import java.util.Set;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.*;

@NodeEntity
public class Person {
    private String firstName, lastName, email;

    @GraphId
    private Long id;

    @Indexed(unique = true)
    private Long loginId;

    @RelatedTo(type="KNOWS", direction = Direction.BOTH)
    Set<Person> friends;

    @Fetch
    @RelatedToVia(type="WORKS_AT", direction = Direction.OUTGOING)
    Set<WorksAt> workedAtCompanies;

    public Person(String firstName, String lastName, String email, long loginId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.loginId = loginId;
        workedAtCompanies = new HashSet<>();
    }

    public Person() {
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Set<WorksAt> getWorkedAtCompanies() {
        return workedAtCompanies;
    }

    public void addCurrentCompany(Company c, int startYear) {
        workedAtCompanies.add(new WorksAt(this, c, startYear));
    }
}
