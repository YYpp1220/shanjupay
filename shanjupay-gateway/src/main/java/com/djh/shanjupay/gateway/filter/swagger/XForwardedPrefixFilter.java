package com.djh.shanjupay.gateway.filter.swagger;

import org.springframework.cloud.gateway.filter.headers.HttpHeadersFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

@Component
public class XForwardedPrefixFilter implements HttpHeadersFilter, Ordered {
    @Override
    public HttpHeaders filter(HttpHeaders input, ServerWebExchange exchange) {
        List<String> xForwareds = input.get("X-Forwarded-Prefix");
        assert xForwareds != null;
        String forward = xForwareds.get(0);
        if (forward.contains(",")) {
            forward = forward.replaceAll(",/api", "");
        }

        input.set("X-Forwarded-Prefix", forward);
        return input;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
