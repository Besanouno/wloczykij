package pl.basistam.wloczykij.json;

import lombok.Data;

@Data
public class EventUserParam {
    private String eventGuid;
    private String[] statusCode;
}
