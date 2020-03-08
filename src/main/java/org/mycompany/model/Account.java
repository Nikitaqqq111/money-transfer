package org.mycompany.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * org.mycompany.economics.model.Account
 */
public class Account {
    @JsonProperty("id")
    private String id = null;

    @JsonProperty("cur")
    private String cur = null;

    @JsonProperty("balance")
    private String balance = null;

    public Account id(String id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     *
     * @return id
     **/
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Account cur(String cur) {
        this.cur = cur;
        return this;
    }

    /**
     * Get cur
     *
     * @return cur
     **/
    @JsonProperty("cur")
    public String getCur() {
        return cur;
    }

    public void setCur(String cur) {
        this.cur = cur;
    }

    public Account balance(String balance) {
        this.balance = balance;
        return this;
    }

    /**
     * Get balance
     *
     * @return balance
     **/
    @JsonProperty("balance")
    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return Objects.equals(this.id, account.id) &&
                Objects.equals(this.cur, account.cur) &&
                Objects.equals(this.balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cur, balance);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class org.mycompany.economics.model.Account {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    cur: ").append(toIndentedString(cur)).append("\n");
        sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

