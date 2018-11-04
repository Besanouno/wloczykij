package pl.basistam.wloczykij.common.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventAccessException extends RuntimeException {
    private String message;
}
