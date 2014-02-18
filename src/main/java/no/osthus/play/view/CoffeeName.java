package no.osthus.play.view;

import no.osthus.play.domain.Coffee;

import java.net.URI;

public class CoffeeName {
    private final Coffee coffee;

    public CoffeeName(Coffee coffee) {
        this.coffee = coffee;
    }

    public String getName() {
        return coffee.getName();
    }

    public Long getId() {
        return coffee.getId();
    }

    public URI getUri() {
        return URI.create("/coffees/" + coffee.getId());
    }
}
