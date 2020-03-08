package org.mycompany.economics.model;

public enum Operation {
    INCREASE, DECREASE;

    public Operation invert() {
        if (this == INCREASE) {
            return DECREASE;
        } else {
            return INCREASE;
        }
    }
}
