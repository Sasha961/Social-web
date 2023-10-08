package ru.skillbox.group39.friends.dto.enums;

public enum StatusCode {

    FRIEND("FRIEND"),
    REQUEST_TO("REQUEST_TO"),
    REQUEST_FROM("REQUEST_FROM"),
    BLOCKED("BLOCKED"),
    DECLINED("DECLINED"),
    SUBSCRIBED("SUBSCRIBED"),
    NONE("NONE"),
    WATCHING("WATCHING"),
    REJECTING("REJECTING"),
    RECOMMENDATION("RECOMMENDATION");

    private final String status;

    StatusCode(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return status;
    }
}
