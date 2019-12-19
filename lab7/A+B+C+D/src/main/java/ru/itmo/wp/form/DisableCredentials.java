package ru.itmo.wp.form;

import javax.validation.constraints.NotNull;

public class DisableCredentials {
    @NotNull
    private long id;

    @NotNull
    private boolean disable;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }
}
