package no.osthus.play;

import com.google.common.collect.Lists;
import com.google.common.io.CharStreams;
import no.osthus.play.domain.Person;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.GraphDatabaseAPI;
import org.neo4j.kernel.impl.util.StringLogger;
import org.neo4j.server.CommunityNeoServer;
import org.neo4j.server.helpers.CommunityServerBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class AppTest {
    private static CommunityNeoServer server;
    private static GraphDatabaseService grapdb;

    @BeforeClass
    public static void setup() throws IOException {
        server = CommunityServerBuilder.server().build();
        server.start();
        grapdb = server.getDatabase().getGraph();
        loadDb();
    }

    private static void loadDb() throws IOException {
        InputStream stream = AppTest.class.getClassLoader().getResourceAsStream("init_db.cypher");
        String query = CharStreams.toString(new InputStreamReader(stream, "UTF-8"));
        ExecutionEngine engine = new ExecutionEngine(grapdb, StringLogger.SYSTEM);
        engine.execute(query);
    }

    @AfterClass
    public static void teardown() {
        server.stop();
    }

    @Test
    @Ignore
    public void doSomeCool() throws IOException {
        ExecutionEngine engine = new ExecutionEngine(grapdb, StringLogger.SYSTEM);

        ExecutionResult result;


        try ( Transaction ignored = grapdb.beginTx() ) {
            result = engine.execute( "MATCH (n:Coffee) RETURN n;" );

            Iterator<Node> resultNodes = result.javaColumnAs("n");
            List<Node> nodes = Lists.newArrayList(resultNodes);

            for(Node n : nodes) {
                System.out.println(n.getProperty("name"));

            }

            assertNotNull(nodes);
            assertThat(nodes.size(), is(2));
            assertThat(nodes.get(1).hasProperty("name"), is(true));
        }
    }

}