package no.osthus.controller;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import java.util.Locale;

public class JsonViewResolver implements ViewResolver {

    /**
     * Get the view to use.
     *
     * @return Always returns an instance of {@link MappingJacksonJsonView}.
     */
    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        MappingJacksonJsonView view = new MappingJacksonJsonView();
        view.setPrettyPrint(true);      // Lay the JSON out to be nicely readable
        return view;
    }
}
