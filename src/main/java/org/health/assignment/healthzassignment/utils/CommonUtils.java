package org.health.assignment.healthzassignment.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class CommonUtils {

    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Content-Type-Options", "nosniff");
        headers.set("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.setPragma("no-cache");
        return headers;
    }

}
