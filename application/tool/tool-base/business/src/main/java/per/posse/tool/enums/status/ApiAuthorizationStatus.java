package per.posse.tool.enums.status;

public enum ApiAuthorizationStatus {

    SUCCESS, //
    BLANK_TOKEN, //
    TOKEN_NOT_FOUND, //
    LOGIN_IN_ANOTHER_PLACE, //
    ;

    public boolean isBlankToken() {
        return this == BLANK_TOKEN;
    }

    public boolean isTokenNotFound() {
        return this == TOKEN_NOT_FOUND;
    }

    public boolean isSuccess() {
        return this == SUCCESS;
    }

    public boolean isFail() {
        return this != SUCCESS;
    }

    public boolean isLoginAnotherBrowser() {
        return this == LOGIN_IN_ANOTHER_PLACE;
    }
}
