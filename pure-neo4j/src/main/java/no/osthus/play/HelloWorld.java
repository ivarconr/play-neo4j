package no.osthus.play;

import no.osthus.play.web.MyResource;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

public class HelloWorld extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.getWriter().print("Hello from Java!\n");
    }
    public static void main(String[] args) throws Exception{
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(Integer.valueOf(System.getenv("PORT"))).build();
        ResourceConfig config = new ResourceConfig(MyResource.class);

        //Server server = new Server());
        Server server = JettyHttpContainerFactory.createServer(baseUri, config);
        //ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        //context.setContextPath("/");
        //server.setHandler(context);
        //context.addServlet(new ServletHolder(new HelloWorld()),"/*");
        //context.addServlet(new ServletHolder(new ServletContainer(new PackagesResourceConfig("com.sun.jersey.samples.helloworld"))), "/");

        //server.start();
        //server.join();
    }
}
