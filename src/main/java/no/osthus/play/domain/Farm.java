package no.osthus.play.domain;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

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

    public District getDistrict() {
        Relationship rel = underlyingNode.getSingleRelationship(
                DynamicRelationshipType.withName("IN_DISTRICT"),
                Direction.OUTGOING);
        if(rel != null) {
            return new District(rel.getEndNode());
        } else {
            return null;
        }
    }
}
