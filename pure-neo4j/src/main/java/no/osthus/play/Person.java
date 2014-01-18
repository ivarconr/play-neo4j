package no.osthus.play;

import org.neo4j.graphdb.Label;

import java.util.HashSet;
import java.util.Set;

public class Person {
    public static final Label TYPE = new Label() {
        @Override
        public String name() {
            return "Person";
        }
    };
    private final Long id;
    private final String name;
    private final Long loginId;
    private final Set<WorksAtRelationship> worksAtRelationships;

    public Person(String name, Long loginId) {
        this.id=null;
        this.name = name;
        this.loginId = loginId;
        worksAtRelationships = new HashSet<>();
    }

    public Person(Long id, String name, Long loginId) {
        this.id = id;
        this.name = name;
        this.loginId = loginId;
        this.worksAtRelationships = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getLoginId() {
        return loginId;
    }

    public void addRelationship(WorksAtRelationship worksAtRelationship) {
        this.worksAtRelationships.add(worksAtRelationship);
    }
}
