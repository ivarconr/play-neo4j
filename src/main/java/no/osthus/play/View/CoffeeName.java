package no.osthus.play.View;

import no.osthus.play.domain.Coffee;

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
}
