package io.suricate.widgetTester.model.dto.nashorn;

import io.suricate.widgetTester.model.dto.AbstractDto;
import io.suricate.widgetTester.model.enums.WidgetVariableType;
import lombok.*;

import java.util.Map;

/**
 * Widget variable used for communication with the clients via webservices
 */
@Getter @Setter @NoArgsConstructor @EqualsAndHashCode(callSuper = false) @ToString
public class WidgetVariableResponse extends AbstractDto {

    /**
     * The variable name
     */
    private String name;

    /**
     * The variable description
     */
    private String description;

    /**
     * The data
     */
    private String data;

    /**
     * The variable type
     */
    private WidgetVariableType type;

    /**
     * If the variable is required
     */
    private boolean required;

    /**
     * Map of values
     */
    private Map<String, String> values;
}
