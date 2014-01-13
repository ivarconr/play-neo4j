package no.osthus.controller;

import no.osthus.domain.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PersonsController {

    @Autowired
    private PersonRepository repo;

    @RequestMapping("/persons")
    public String persons(Model model) {
        model.addAttribute("persons", repo.findAll().iterator());
        return "persons/list";
    }
}
