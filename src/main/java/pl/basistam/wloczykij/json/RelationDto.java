package pl.basistam.wloczykij.json;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RelationDto {
    private String login;
    private String name;
    private boolean related;
}
