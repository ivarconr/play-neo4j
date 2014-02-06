package no.osthus.play.domain;


import com.google.common.base.Optional;

public class Coffee {
    final public Optional<Long> id;
    final public String name;

    public Coffee(String name) {
        this.id = Optional.absent();
        this.name = name;
    }

    public Coffee(Long id, String name) {
        this.id = Optional.fromNullable(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id.orNull();
    }

    @Override
    public String toString() {
        return "Coffee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coffee coffee = (Coffee) o;

        if (!id.equals(coffee.id)) return false;
        if (!name.equals(coffee.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
