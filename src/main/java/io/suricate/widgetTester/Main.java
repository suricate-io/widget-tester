package io.suricate.widgetTester;

import io.suricate.widgetTester.model.dto.nashorn.NashornRequest;
import io.suricate.widgetTester.model.dto.nashorn.NashornResponse;
import io.suricate.widgetTester.model.dto.widget.WidgetDto;
import io.suricate.widgetTester.service.nashorn.NashornWidgetExecuteAsyncTask;
import io.suricate.widgetTester.utils.JsonUtils;
import io.suricate.widgetTester.utils.WidgetUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;


public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {

        WidgetDto widget = WidgetUtils.getWidget(
            // Path to folder containing js file for widget
            new File("D:\\work\\github\\suricate-io\\widgets\\content\\gitlab\\widgets\\gitlabGroupDetails")
        );

        NashornRequest nashornRequest = new NashornRequest();
        nashornRequest.setDelay(widget.getDelay());
        nashornRequest.setScript(widget.getBackendJs());
        nashornRequest.setProperties(
                // List of configuration key/value pairs required for widget
            "WIDGET_CONFIG_GITLAB_TOKEN=xxxx\n" +
                "WIDGET_CONFIG_GITLAB_URL=https://gitlab.com\n" +
                    "GITLAB_GROUPID=xxx"
        );
        NashornWidgetExecuteAsyncTask nashornWidgetExecuteAsyncTask = new NashornWidgetExecuteAsyncTask(nashornRequest, WidgetUtils.getWidgetVariables(widget));
        NashornResponse response = nashornWidgetExecuteAsyncTask.call();
        if(StringUtils.isNotBlank(response.getLog())) {
            LOGGER.info("----------- START LOG ---------");
            LOGGER.info(response.getLog());
            LOGGER.info("----------- END LOG ---------");
        }
        if (response.getError() != null) {
            LOGGER.info("----------- START ERROR ---------");
            LOGGER.info(response.getError().name());
            LOGGER.info("----------- END ERROR ---------");
        }
        LOGGER.info("------------ START RESPONSE --------");
        LOGGER.info("\n" + JsonUtils.prettifyJson(response.getData()));
        LOGGER.info("------------ END RESPONSE --------");
    }
}
