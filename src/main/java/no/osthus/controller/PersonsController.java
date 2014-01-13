package no.osthus.controller;

import com.google.common.collect.Lists;
import no.osthus.domain.Person;
import no.osthus.domain.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PersonsController {

    @Autowired
    private PersonRepository repo;

    @RequestMapping(value="/persons", method=RequestMethod.GET)
    @ResponseBody()
    public List<Person> persons() {
        List<Person> p = Lists.newArrayList(repo.findAll().iterator());
        return p;
    }
}
