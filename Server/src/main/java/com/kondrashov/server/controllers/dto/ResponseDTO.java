package com.kondrashov.server.controllers.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class ResponseDTO {

    private String startLine;
    private Map<String, String > headers = new HashMap<>();
    private Object body;

    public ResponseDTO() {
    }

    public ResponseDTO(String startLine, Map<String, String> headers, Object body) {
        this.startLine = startLine;
        this.headers = headers;
        this.body = body;
    }

    public String getStartLine() {
        return startLine;
    }

    public void setStartLine(String startLine) {
        this.startLine = startLine;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ResponseDTO.class.getSimpleName() + "[", "]")
                .add("startLine='" + startLine + "'")
                .add("headers=" + headers)
                .toString();
    }
}
