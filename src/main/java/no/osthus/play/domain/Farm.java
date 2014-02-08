package no.osthus.play.domain;

import com.google.common.base.Optional;

public class Farm {
    private final String name;
    private final Optional<Long> id;

    public Farm(String name) {
        this(name, null);
    }

    public Farm(String name, Long id) {
        this.name = name;
        this.id = Optional.fromNullable(id);
    }

    public String getName() {
        return name;
    }

    public Optional<Long> getId() {
        return id;
    }
}
