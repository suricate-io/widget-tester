package io.suricate.widgetTester.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.suricate.widgetTester.model.dto.nashorn.WidgetVariableResponse;
import io.suricate.widgetTester.model.dto.widget.WidgetDto;
import io.suricate.widgetTester.model.dto.widget.WidgetParamDto;
import io.suricate.widgetTester.model.dto.widget.WidgetParamValueDto;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public final class WidgetUtils {

    /**
     * Object mapper for jackson
     */
    private static ObjectMapper mapper = null;
    static {
        mapper = new ObjectMapper(new YAMLFactory());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Class logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WidgetUtils.class);

    /**
     * Method used to get widget from Folder
     * @param widgetFolder widget folder
     * @return the Widget bean
     * @throws IOException
     */
    public static WidgetDto getWidget(File widgetFolder) throws IOException {
        if (widgetFolder == null ) return null;
        WidgetDto widget = new WidgetDto();
        List<File> files = FilesUtils.getFiles(widgetFolder);
        if (files != null){
            for (File file: files){
                if (FilenameUtils.getBaseName(file.getName()).equals("image")) {
                    widget.setImage(FileUtils.readFileToByteArray(file));
                } else if (FilenameUtils.getBaseName(file.getName()).equals("description")){
                    mapper.readerForUpdating(widget).readValue(file);
                } else if (FilenameUtils.getBaseName(file.getName()).equals("script")){
                    widget.setBackendJs(StringUtils.trimToNull(FileUtils.readFileToString(file, StandardCharsets.UTF_8)));
                } else if (FilenameUtils.getBaseName(file.getName()).equals("style")){
                    widget.setCssContent(StringUtils.trimToNull(FileUtils.readFileToString(file, StandardCharsets.UTF_8)));
                } else if (FilenameUtils.getBaseName(file.getName()).equals("content")){
                    widget.setHtmlContent(StringUtils.trimToNull(FileUtils.readFileToString(file, StandardCharsets.UTF_8)));
                }  else if ("params".equals(FilenameUtils.getBaseName(file.getName()))) {
                    mapper.readerForUpdating(widget).readValue(file);
                }
            }
            if (widget.getDelay() == null) {
                LOGGER.error("Widget delay must no be null : {}", widgetFolder.getPath());
                return null;
            }
            if (widget.getDelay() > 0 && StringUtils.isBlank(widget.getBackendJs())) {
                LOGGER.error("Widget script must not be empty when delay > 0 : {}", widgetFolder.getPath());
                return null;
            }

            if (StringUtils.isAnyBlank(widget.getCssContent(), widget.getDescription(), widget.getHtmlContent(), widget.getTechnicalName(), widget.getName())) {
                LOGGER.error("Widget is not well formatted : {}", widgetFolder.getPath());
                return null;
            }
        }
        return widget;
    }

    /**
     * Get the list of the variables for a widget
     *
     * @param widget The widget
     * @return The list of variables related
     */
    public static List<WidgetVariableResponse> getWidgetVariables(final WidgetDto widget) {
        List<WidgetVariableResponse> widgetVariableResponses = new ArrayList<>();

        for (WidgetParamDto widgetParam : widget.getWidgetParams()) {
            WidgetVariableResponse widgetVariableResponse = new WidgetVariableResponse();
            widgetVariableResponse.setName(widgetParam.getName());
            widgetVariableResponse.setDescription(widgetParam.getDescription());
            widgetVariableResponse.setType(widgetParam.getType());

            if (widgetVariableResponse.getType() != null) {
                switch (widgetVariableResponse.getType()) {
                    case COMBO:
                        widgetVariableResponse.setValues(getWidgetParamValuesAsMap(widgetParam.getValues()));
                        break;

                    case MULTIPLE:
                        widgetVariableResponse.setValues(getWidgetParamValuesAsMap(widgetParam.getValues()));
                        break;

                    default:
                        widgetVariableResponse.setData(StringUtils.trimToNull(widgetParam.getDefaultValue()));
                        break;
                }
            }

            widgetVariableResponses.add(widgetVariableResponse);
        }

        return widgetVariableResponses;
    }

    /**
     * Get the widget param list as a Map
     *
     * @param widgetParamValues The list of the widget param values
     * @return The list as a Map<String, String>
     */
    public static Map<String, String> getWidgetParamValuesAsMap(List<WidgetParamValueDto> widgetParamValues) {
        return widgetParamValues
            .stream()
            .collect(Collectors.toMap(WidgetParamValueDto::getJsKey, WidgetParamValueDto::getValue));
    }

    /**
     * Private constructor
     */
    private WidgetUtils() {
    }
}
