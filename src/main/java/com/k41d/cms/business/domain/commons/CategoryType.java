package com.k41d.cms.business.domain.commons;


public enum CategoryType {
    TOPICS("TOPICS"),
    SINGLE_STATIC_TOPIC("STATIC"),
    URL("URL");
    public final String value;

    private CategoryType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
