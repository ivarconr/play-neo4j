package no.osthus;

import no.osthus.domain.*;


public class InitDb {
    public InitDb(PersonRepository personRepo, CompanyRepository companyRepository) {
        Company finn = new Company(-3002l, "FINN.no");
        companyRepository.save(finn);


        Person p = new Person("Ola", "Hansen", "me@mail.com", 123l);
        p = personRepo.save(p);

        p.addCurrentCompany(finn, 2013);
        personRepo.save(p);
    }
}
