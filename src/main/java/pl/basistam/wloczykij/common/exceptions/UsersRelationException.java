package pl.basistam.wloczykij.common.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UsersRelationException extends RuntimeException {
    private String message;
}
