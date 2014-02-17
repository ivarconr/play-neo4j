package no.osthus.play.domain;

import org.neo4j.graphdb.Node;

public class Altitude {
    private final Node underNode;

    public Altitude(Node underNode) {
        this.underNode = underNode;
    }

    public Long getAltitude() {
        return Long.parseLong(underNode.getProperty("meters").toString());
    }
}
