package io.hasura.sdk.auth;

public class AuthErrorResponse {
    private String code;
    private String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}