package io.hasura.sdk.model.response;

public class HasuraErrorResponse {
    private String code;
    private String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}