package no.osthus.play.domain;

import org.neo4j.graphdb.Node;

public class Country {

    private final Node underlyingNode;

    public Country(Node node) {
        this.underlyingNode = node;
    }

    public String getName() {
        return underlyingNode.getProperty("name").toString();
    }
}
