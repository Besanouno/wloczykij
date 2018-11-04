package pl.basistam.wloczykij.json;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorsJson {
    private List<String> errors;

    public ErrorsJson(String error) {
        errors = new ArrayList<>(1);
        errors.add(error);
    }
}
