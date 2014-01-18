package no.osthus.play.domain;

public class WorksAtRelationship {
    private final Person person;
    private final Company company;
    private final Integer startYear;

    public WorksAtRelationship(Person person, Company company, Integer startYear) {
        this.person = person;
        this.company = company;
        this.startYear = startYear;
    }
}
