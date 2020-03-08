package org.mycompany;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mycompany.utils.JSONUtils;

import java.io.InputStream;
import java.math.BigDecimal;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AccountsApiTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() {
        server = Runner.startServer();
        Client c = ClientBuilder.newClient();
        target = c.target(Runner.BASE_URI);
    }

    @After
    public void tearDown() {
        server.shutdownNow();
    }

    @Test
    public void testCreateAccountsIncorrect() throws Exception {
        JSONArray accountsInvalid = (JSONArray) JSONUtils.read("accountsInvalid");
        Response responseCreateFailed = target.path("/accounts/createWithList").request().buildPost(Entity.entity(accountsInvalid, APPLICATION_JSON)).invoke();
        assertEquals(500, responseCreateFailed.getStatus());

        JSONArray jsonPostAccounts = (JSONArray) JSONUtils.read("accounts");
        Response responseCreate = target.path("/accounts/createWithList").request().buildPost(Entity.entity(jsonPostAccounts, APPLICATION_JSON)).invoke();
        assertEquals(200, responseCreate.getStatus());
    }

    @Test
    public void testCreateAccountsSuccess() throws Exception {
        JSONArray jsonPostAccounts = (JSONArray) JSONUtils.read("accounts");
        Response responseCreate = target.path("/accounts/createWithList").request().buildPost(Entity.entity(jsonPostAccounts, APPLICATION_JSON)).invoke();
        assertEquals(200, responseCreate.getStatus());
        assert jsonPostAccounts != null;

        Response responseGet = target.path("/accounts").request().buildGet().invoke();
        assertEquals(200, responseGet.getStatus());
        JSONArray jsonGetAccounts = (JSONArray) JSONUtils.read((InputStream) responseGet.getEntity());

        assert jsonGetAccounts != null;
        compareJSONAccounts(jsonPostAccounts, jsonGetAccounts);
    }

    /*
     * transfer 11111111111111111111 RUR -> 22222222222222222222 RUR    300 RUR
     *
     */
    @Test
    public void testTransferMoneySuccess() throws Exception {

        final String from = "11111111111111111111";
        final String to = "22222222222222222222";

        final String expectedBalanceFrom = "200";
        final String expectedBalanceTo = "600";


        JSONArray jsonPostAccounts = (JSONArray) JSONUtils.read("accounts");
        Response responseCreate = target.path("/accounts/createWithList").request().buildPost(Entity.entity(jsonPostAccounts, APPLICATION_JSON)).invoke();
        assertEquals(200, responseCreate.getStatus());

        JSONObject jsonTransferObject = (JSONObject) JSONUtils.read("transferRUR");
        Response responseTransfer = target.path("/accounts/transfer").request().buildPost(Entity.entity(jsonTransferObject, APPLICATION_JSON)).invoke();
        assertEquals(200, responseCreate.getStatus());

        Response responseGet = target.path("/accounts").request().buildGet().invoke();
        assertEquals(200, responseGet.getStatus());
        JSONArray jsonGetAccounts = (JSONArray) JSONUtils.read((InputStream) responseGet.getEntity());

        assert jsonGetAccounts != null;
        assert jsonPostAccounts != null;
        assertEquals(jsonGetAccounts.size(), jsonPostAccounts.size());
        for (Object jsonGetObject : jsonGetAccounts) {
            JSONObject account = (JSONObject) jsonGetObject;
            Object id = account.get("id");
            Object balance = account.get("balance");
            if (id.equals(from)) {
                assertEquals(expectedBalanceFrom, balance.toString());
            }
            if (id.equals(to)) {
                assertEquals(expectedBalanceTo, balance.toString());
            }
        }
    }

    /*
     * transfer 11111111111111111111 RUR -> 22222222222222222222 RUR    10.2 RUR
     *
     */
    @Test
    public void testTransferMoneySuccess2() throws Exception {

        final String from = "11111111111111111111";
        final String to = "22222222222222222222";

        final String expectedBalanceFrom = "489.8";
        final String expectedBalanceTo = "310.2";


        JSONArray jsonPostAccounts = (JSONArray) JSONUtils.read("accounts");
        Response responseCreate = target.path("/accounts/createWithList").request().buildPost(Entity.entity(jsonPostAccounts, APPLICATION_JSON)).invoke();
        assertEquals(200, responseCreate.getStatus());

        JSONObject jsonTransferObject = (JSONObject) JSONUtils.read("transferRURDoubleNumber");
        Response responseTransfer = target.path("/accounts/transfer").request().buildPost(Entity.entity(jsonTransferObject, APPLICATION_JSON)).invoke();
        assertEquals(200, responseCreate.getStatus());

        Response responseGet = target.path("/accounts").request().buildGet().invoke();
        assertEquals(200, responseGet.getStatus());
        JSONArray jsonGetAccounts = (JSONArray) JSONUtils.read((InputStream) responseGet.getEntity());

        assert jsonGetAccounts != null;
        assert jsonPostAccounts != null;
        assertEquals(jsonGetAccounts.size(), jsonPostAccounts.size());
        for (Object jsonGetObject : jsonGetAccounts) {
            JSONObject account = (JSONObject) jsonGetObject;
            Object id = account.get("id");
            Object balance = account.get("balance");
            if (id.equals(from)) {
                assertEquals(new BigDecimal(expectedBalanceFrom), new BigDecimal(balance.toString()));
            }
            if (id.equals(to)) {
                assertEquals(new BigDecimal(expectedBalanceTo), new BigDecimal(balance.toString()));
            }
        }
    }


    /*
     * transfer 11111111111111111111 RUR -> 22222222222222222222 RUR    300 EUR
     *
     */
    @Test
    public void testTransferMoneyBadCur() throws Exception {
        JSONArray jsonPostAccounts = (JSONArray) JSONUtils.read("accounts");
        assert jsonPostAccounts != null;
        Response responseCreate = target.path("/accounts/createWithList").request().buildPost(Entity.entity(jsonPostAccounts, APPLICATION_JSON)).invoke();
        assertEquals(200, responseCreate.getStatus());

        JSONObject jsonTransferObject = (JSONObject) JSONUtils.read("transferEUR");
        Response responseTransfer = target.path("/accounts/transfer").request().buildPost(Entity.entity(jsonTransferObject, APPLICATION_JSON)).invoke();
        assertEquals(400, responseTransfer.getStatus());
        assertEquals("11111111111111111111: Currencies are not equal", responseTransfer.getStatusInfo().getReasonPhrase());
        Response responseGet = target.path("/accounts").request().buildGet().invoke();
        assertEquals(200, responseGet.getStatus());
        JSONArray jsonGetAccounts = (JSONArray) JSONUtils.read((InputStream) responseGet.getEntity());
        assert jsonGetAccounts != null;

        compareJSONAccounts(jsonPostAccounts, jsonGetAccounts);
    }

    /*
     * transfer for unknown account
     *
     */
    @Test
    public void testTransferMoneyUnknownAccount() throws Exception {
        JSONArray jsonPostAccounts = (JSONArray) JSONUtils.read("accounts");
        assert jsonPostAccounts != null;
        Response responseCreate = target.path("/accounts/createWithList").request().buildPost(Entity.entity(jsonPostAccounts, APPLICATION_JSON)).invoke();
        assertEquals(200, responseCreate.getStatus());

        JSONObject jsonTransferObject = (JSONObject) JSONUtils.read("transferUnknown");
        Response responseTransfer = target.path("/accounts/transfer").request().buildPost(Entity.entity(jsonTransferObject, APPLICATION_JSON)).invoke();
        assertEquals(400, responseTransfer.getStatus());
        assertEquals("Unknown account: 99999999999999999999", responseTransfer.getStatusInfo().getReasonPhrase());
    }

    private void compareJSONAccounts(JSONArray jsonPostAccounts, JSONArray jsonGetAccounts) {
        assertEquals(jsonGetAccounts.size(), jsonPostAccounts.size());
        for (Object jsonPostObject : jsonPostAccounts) {
            JSONObject postAccount = (JSONObject) jsonPostObject;
            Object idExpected = postAccount.get("id");
            Object curExpected = postAccount.get("cur");
            Object balanceExpected = postAccount.get("balance");
            boolean isFound = false;
            for (Object jsonGetObject : jsonGetAccounts) {
                JSONObject getAccount = (JSONObject) jsonGetObject;
                Object id = getAccount.get("id");
                Object cur = getAccount.get("cur");
                Object balance = getAccount.get("balance");
                if (id.equals(idExpected) && cur.equals(curExpected)) {
                    assertEquals(new BigDecimal(balanceExpected.toString()), new BigDecimal(balance.toString()));
                    isFound = true;
                    break;
                }
            }
            assertTrue(isFound);
        }
    }
}
