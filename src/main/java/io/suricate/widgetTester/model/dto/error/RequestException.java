package io.suricate.widgetTester.model.dto.error;

public class RequestException extends Exception {

    private String technicalData;

    private String response;

    public RequestException(String technicalData, String response) {
        super(technicalData);
        this.technicalData = technicalData;
        this.response = response;
    }

    public String getTechnicalData() {
        return technicalData;
    }

    public String getResponse() {
        return response;
    }
}
