package no.osthus.play.domain;

import org.neo4j.graphdb.Node;

public class BeanType {
    private final Node underlyingNode;

    public BeanType(Node underlyingNode) {
        this.underlyingNode = underlyingNode;
    }

    public String getName() {
        return underlyingNode.getProperty("name").toString();
    }

    public Long getId() {
        return underlyingNode.getId();
    }
}
