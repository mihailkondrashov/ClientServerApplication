package com.kondrashov.server.controllers.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

public class RequestDTO {

    private String startLine;
    private Map<String, String > headers = new HashMap<>();
    private Object body;

    public RequestDTO() {
    }

    public RequestDTO(String startLine, Map<String, String> headers, Object body) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestDTO that = (RequestDTO) o;
        return startLine.equals(that.startLine) && headers.equals(that.headers) && Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startLine, headers, body);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RequestDTO.class.getSimpleName() + "[", "]")
                .add("startLine='" + startLine + "'")
                .add("headers=" + headers)
                .add("body=" + body)
                .toString();
    }
}
