package no.osthus.play.domain;


import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

import java.util.List;


//Todo:
// probably better to fetch all relations eagerly in constructor
// instead of loading em lazily?
public class Coffee {
    private final Node underlyingNode;

    public Coffee(Node node) {
        this.underlyingNode = node;
    }

    public String getName() {
        return underlyingNode.getProperty("name").toString();
    }

    public Long getId() {
        return underlyingNode.getId();
    }

    public Farm getFarmedBy() {
        Relationship rel = underlyingNode.getSingleRelationship(
                DynamicRelationshipType.withName("IS_FARMED_BY"),
                Direction.OUTGOING);
        if(rel != null) {
            return new Farm(rel.getEndNode());
        } else {
            return null;
        }
    }

    //TODO: figure out why jackson does not support iterable here.
    public Iterable<BeanType> getBeans() {
        Iterable<Relationship> beanTypes = underlyingNode.getRelationships(
                DynamicRelationshipType.withName("BEAN_TYPE"),
                Direction.OUTGOING);

        return Lists.newArrayList(Iterables.transform(beanTypes, new Function<Relationship, BeanType>() {
            @Override
            public BeanType apply(Relationship relationship) {
                return new BeanType(relationship.getEndNode());
            }
        }));
    }

    @Override
    public String toString() {
        return "Coffee{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                '}';
    }
}
