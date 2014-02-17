package no.osthus.play;

import org.codehaus.jackson.map.SerializationConfig;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class HelloWorld {

    public static void main(String[] args) throws Exception{
        int port = Integer.valueOf(System.getenv("PORT"));
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(port).build();


        ResourceConfig config = new ResourceConfig()
                .packages("no.osthus.play")
                .register(JacksonFeature.class);

        Server server = JettyHttpContainerFactory.createServer(baseUri, config);
    }
}
