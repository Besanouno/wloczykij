package pl.basistam.wloczykij.common.exceptions;

public class CredentialsTakenException extends RuntimeException {
    private String message;

    public CredentialsTakenException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
