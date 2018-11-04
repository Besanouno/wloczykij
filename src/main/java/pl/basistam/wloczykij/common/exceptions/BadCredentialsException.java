package pl.basistam.wloczykij.common.exceptions;

public class BadCredentialsException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Bad credentials";
    }
}
