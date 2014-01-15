package no.osthus.play;

import org.neo4j.graphdb.Label;

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

    public Person(String name, Long loginId) {
        this.id=null;
        this.name = name;
        this.loginId = loginId;
    }

    public Person(Long id, String name, Long loginId) {
        this.id = id;
        this.name = name;
        this.loginId = loginId;
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
}
