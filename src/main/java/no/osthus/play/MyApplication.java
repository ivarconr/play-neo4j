package no.osthus.play;

import com.sun.jersey.spi.container.servlet.ServletContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import java.util.EnumSet;

public class MyApplication {

    public static void main(String[] args) throws Exception {
        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }

        Server server = new Server(Integer.valueOf(webPort));
        WebAppContext context = new WebAppContext();
        context.setContextPath("/");
        context.setResourceBase("src/main/webapp");
        context.setWelcomeFiles(new String[]{"index.html"});


        server.setHandler(context);

        //FilterHolder sh = new FilterHolder();
        //sh.setFilter(new ServletContainer());

        //context.addFilter(sh, "/rest/*", EnumSet.of(DispatcherType.REQUEST));
        ServletHolder sh = context.addServlet(com.sun.jersey.spi.container.servlet.ServletContainer.class, "/rest/*");
        sh.setInitParameter("com.sun.jersey.config.property.packages", "no.osthus.play");
        sh.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");
        /*sh.setInitParameter("com.sun.jersey.config.property.WebPageContentRegex",
                "(/(image|js|css)/?.*)|" +
                        "(/.*\\.html)|(/favicon\\.ico)|" +
                        "(/robots\\.txt)");*/

        try {
            server.start();
            server.join();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }

    }
}
