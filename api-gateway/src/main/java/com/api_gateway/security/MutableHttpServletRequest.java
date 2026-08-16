package com.api_gateway.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.*;

public class MutableHttpServletRequest
        extends HttpServletRequestWrapper {

    private final Map<String, String> customHeaders =
            new HashMap<>();

    public MutableHttpServletRequest(
            HttpServletRequest request) {

        super(request);
    }

    public void putHeader(
            String name,
            String value) {

        customHeaders.put(
                name,
                value
        );
    }

    @Override
    public String getHeader(String name) {

        String value = customHeaders.get(name);

        if (value != null) {
            return value;
        }

        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {

        String value = customHeaders.get(name);

        if (value != null) {
            return Collections.enumeration(
                    Collections.singletonList(value)
            );
        }

        return super.getHeaders(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {

        Set<String> names = new HashSet<>();

        Enumeration<String> existing =
                super.getHeaderNames();

        if (existing != null) {

            while (existing.hasMoreElements()) {
                names.add(existing.nextElement());
            }
        }

        names.addAll(customHeaders.keySet());

        return Collections.enumeration(names);
    }
}