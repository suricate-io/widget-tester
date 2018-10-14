package io.suricate.widgetTester.model.dto.nashorn;

import io.suricate.widgetTester.model.enums.WidgetState;
import io.suricate.widgetTester.model.dto.AbstractDto;
import io.suricate.widgetTester.utils.JsonUtils;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * Represent a nashorn request (used for execute the widget JS, with nashorn)
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false) @ToString
public class NashornRequest extends AbstractDto {


    /**
     * Nashorn Properties
     */
    private String properties;

    /**
     * javascript script
     */
    private String script;

    /**
     * Previous json data
     */
    private String previousData;

    /**
     * project id
     */
    private Long projectId;


    /**
     * Project widget ID
     */
    private Long projectWidgetId;

    /**
     * The delay to launch the script
     */
    private Long delay;

    /**
     * Widget State
     */
    private WidgetState widgetState;

    /**
     * Boolean to indicate if the
     */
    private boolean alreadySuccess;

    /**
     * The override timeout
     */
    private Long timeout;

    /**
     * Full constructor
     *
     * @param properties The project widget backend config
     * @param script The widget js script
     * @param previousData The data of the last execution
     * @param projectId The project id
     * @param technicalId The project widget id
     * @param delay The delay before the next run
     * @param timeout The timeout before interruption of the run
     * @param state The project widget state
     * @param lastSuccess The last success date
     */
    public NashornRequest(String properties, String script, String previousData, Long projectId, Long technicalId, Long delay, Long timeout, WidgetState state, Date lastSuccess) {
        this.properties = properties;
        this.script = script;
        this.previousData = previousData;
        this.projectId = projectId;
        this.projectWidgetId = technicalId;
        this.delay = delay;
        this.widgetState = state;
        this.timeout = timeout;
        this.alreadySuccess = lastSuccess != null;
    }

    /**
     * Method used to validate all bean data
     * @return true if all data are Valid, false otherwise
     */
    public boolean isValid(){
        return projectId != null
            && projectWidgetId != null
            && JsonUtils.isJsonValid(previousData)
            && StringUtils.isNotEmpty(script)
            && delay != null;
    }
}
