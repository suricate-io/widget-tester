package io.suricate.widgetTester.model.dto.nashorn;

import io.suricate.widgetTester.model.dto.AbstractDto;
import io.suricate.widgetTester.model.enums.NashornErrorTypeEnum;
import io.suricate.widgetTester.utils.JsonUtils;
import lombok.*;

import java.util.Date;
/**
 * Represent the response after nashorn execution
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false) @ToString
public class NashornResponse extends AbstractDto {

    /**
     * The new calculated data
     */
    private String data;

    /**
     * the log data
     */
    private String log;

    /**
     * The project Id
     */
    private Long projectId;

    /**
     * The projectwidget Id
     */
    private Long projectWidgetId;

    /**
     * Launch date
     */
    private Date launchDate;

    /**
     * Error
     */
    private NashornErrorTypeEnum error;

    /**
     * Method used to check if the object is isValid
     * @return true if this object is isValid, false otherwise
     */
    public boolean isValid(){
        return JsonUtils.isJsonValid(data) && projectId != null && projectWidgetId != null && error == null;
    }

    public boolean isFatal() {
        return NashornErrorTypeEnum.FATAL == error;
    }
}
