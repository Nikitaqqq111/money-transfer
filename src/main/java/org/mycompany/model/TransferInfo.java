package org.mycompany.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class TransferInfo {
    @JsonProperty("from")
    private String from = null;

    @JsonProperty("to")
    private String to = null;

    @JsonProperty("amount")
    private String amount = null;

    @JsonProperty("cur")
    private String cur = null;

    public TransferInfo from(String from) {
        this.from = from;
        return this;
    }

    /**
     * Get from
     *
     * @return from
     **/
    @JsonProperty("from")
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public TransferInfo to(String to) {
        this.to = to;
        return this;
    }

    /**
     * Get to
     *
     * @return to
     **/
    @JsonProperty("to")
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public TransferInfo amount(String amount) {
        this.amount = amount;
        return this;
    }

    /**
     * Get amount
     *
     * @return amount
     **/
    @JsonProperty("amount")
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public TransferInfo cur(String cur) {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TransferInfo transferInfo = (TransferInfo) o;
        return Objects.equals(this.from, transferInfo.from) &&
                Objects.equals(this.to, transferInfo.to) &&
                Objects.equals(this.amount, transferInfo.amount) &&
                Objects.equals(this.cur, transferInfo.cur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, amount, cur);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TransferInfo {\n");

        sb.append("    from: ").append(toIndentedString(from)).append("\n");
        sb.append("    to: ").append(toIndentedString(to)).append("\n");
        sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
        sb.append("    cur: ").append(toIndentedString(cur)).append("\n");
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

