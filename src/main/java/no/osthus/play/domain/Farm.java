package no.osthus.play.domain;

import org.neo4j.graphdb.Node;

public class Farm {
    private final Node underlyingNode;

    public Farm(Node node) {
        this.underlyingNode = node;
    }

    public String getName() {
        return underlyingNode.getProperty("name").toString();
    }

    public Long getId() {
        return underlyingNode.getId();
    }
}
