package no.osthus.play.domain;

import org.neo4j.graphdb.Label;

public class Company {
    public static Label TYPE = new Label() {
        @Override
        public String name() {
            return "Company";
        }
    };

    private final Long id;
    private final String name;
    private final Long orgId;

    public Company(String name, Long orgId) {
        this.id= null;
        this.name = name;
        this.orgId = orgId;
    }

    public Company(Long id, String name, Long orgId) {
        this.id = id;
        this.name = name;
        this.orgId = orgId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getOrgId() {
        return orgId;
    }
}
