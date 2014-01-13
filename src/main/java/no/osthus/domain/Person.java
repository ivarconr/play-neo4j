package no.osthus.domain;

import java.util.Set;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;

@NodeEntity
public class Person {
    private String firstName, lastName, email;

    @GraphId
    private Long id;

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

    public Person(String firstName, String lastName, String email, long loginId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.loginId = loginId;
    }

    public Person() {
    }

    @Indexed(unique = true)
    private Long loginId;

    @RelatedTo(type="KNOWS", direction = Direction.OUTGOING)
    Set<Person> friends;
}
