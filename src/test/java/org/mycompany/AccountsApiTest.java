package org.mycompany;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;

import org.json.simple.JSONAware;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mycompany.utils.JSONUtils;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

public class AccountsApiTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        server = Runner.startServer();
        Client c = ClientBuilder.newClient();
        target = c.target(Runner.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdownNow();
    }

    @Test
    public void testCreateAccountsWithListInput() throws Exception {
        JSONAware jsonAccounts = JSONUtils.read("accounts.json");
        Response response = target.path("/accounts/createWithList").request().buildPost(Entity.entity(jsonAccounts, APPLICATION_JSON)).invoke();
        target.path("/accounts").request().buildGet().invoke();
    }

    @Test
    public void testGetAccounts() throws Exception {
        target.path("/accounts").request().buildGet().invoke();
    }

    @Test
    public void testTransferMoney() throws Exception {
        target.path("/accounts/transfer").request().buildPost(null).invoke();
    }
}
