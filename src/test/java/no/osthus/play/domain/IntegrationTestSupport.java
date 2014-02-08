package no.osthus.play.domain;

import com.google.common.collect.Lists;
import com.google.common.io.CharStreams;
import org.junit.*;
import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.impl.util.StringLogger;
import org.neo4j.server.CommunityNeoServer;
import org.neo4j.server.helpers.CommunityServerBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class IntegrationTestSupport {
    protected static CommunityNeoServer server;
    protected static GraphDatabaseService grapdb;
    protected static ExecutionEngine engine;

    @BeforeClass
    public static void setup() throws IOException {
        server = CommunityServerBuilder.server().build();
        server.start();
        grapdb = server.getDatabase().getGraph();
        engine = new ExecutionEngine(grapdb, StringLogger.SYSTEM);
    }

    @Before
    public void loadDb() throws IOException {
        InputStream stream = IntegrationTestSupport.class.getClassLoader().getResourceAsStream("init_db.cypher");
        String query = CharStreams.toString(new InputStreamReader(stream, "UTF-8"));
        engine.execute(query);
    }

    @After
    public void clearDb() {
        engine.execute("MATCH (c)-[r]-() delete c, r");
    }

    @AfterClass
    public static void teardown() {
        server.stop();
    }

    @Test
    public void simple_verify() throws IOException {
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
            assertThat(nodes.size(), greaterThanOrEqualTo(2));
            assertThat(nodes.get(1).hasProperty("name"), is(true));
        }
    }

}