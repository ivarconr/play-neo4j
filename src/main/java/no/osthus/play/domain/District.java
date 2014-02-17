package no.osthus.play.domain;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.Node;

public class District {
    private final Node underlyingNode;

    public District(Node underlyingNode) {
        this.underlyingNode = underlyingNode;
    }

    public String getName() {
        return underlyingNode.getProperty("name").toString();
    }

    public Country getCountry() {
        Node countryNode = underlyingNode
                .getSingleRelationship(DynamicRelationshipType.withName("COUNTRY"), Direction.OUTGOING).getEndNode();
        return new Country(countryNode);
    }

    public Altitude getAltitude() {
        Node node = underlyingNode
                .getSingleRelationship(DynamicRelationshipType.withName("ALTITUDE"), Direction.OUTGOING).getEndNode();
        return new Altitude(node);
    }
}
