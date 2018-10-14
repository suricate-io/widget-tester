package io.suricate.widgetTester.model.dto.project;

import io.suricate.widgetTester.model.dto.AbstractDto;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class ProjectWidgetPositionDto extends AbstractDto {

    /**
     * The project widget id related to this project widget position
     */
    private Long projectWidgetId;

    /**
     * The start column of this widget
     */
    private int col;

    /**
     * The start row of the widget
     */
    private int row;

    /**
     * The number of columns for this widget
     */
    private int width;

    /**
     * The number of rows
     */
    private int height;
}