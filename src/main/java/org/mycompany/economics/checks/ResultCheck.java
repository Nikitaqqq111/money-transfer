package org.mycompany.economics.checks;

public class ResultCheck {

    public final boolean ok;
    public final String rejectReason;

    public ResultCheck(String rejectReason) {
        this.ok = false;
        this.rejectReason = rejectReason;
    }

    public ResultCheck() {
        this.ok = true;
        this.rejectReason = "";
    }
}
