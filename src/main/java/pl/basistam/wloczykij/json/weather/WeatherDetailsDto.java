package pl.basistam.wloczykij.json.weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDetailsDto {
    private Integer id;
    private String main;
    private String description;
    private String icon;
}
