package tonatyw.es;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AggMethodEnum {
    MAX("1","MAX"),
    MIN("1","MIN"),
    AVG("1","AVG"),
    SUM("1","SUM"),
    STATS("1","STATS");

    private String name;
    private String value;
}
