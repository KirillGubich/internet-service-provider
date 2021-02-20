package com.epam.jwd.provider.model;

public class ConnectionPoolProperties {
    private final String databaseUrl;
    private final String databaseUser;
    private final String databasePassword;
    private final Integer defaultPoolSize;
    private final Integer maxPoolSize;
    private final Integer percentageForExpansion;
    private final Integer expansionStep;

    public ConnectionPoolProperties(String databaseUrl, String databaseUser, String databasePassword, Integer defaultPoolSize,
                                    Integer maxPoolSize, Integer percentageForExpansion, Integer expansionStep) {
        this.databaseUrl = databaseUrl;
        this.databaseUser = databaseUser;
        this.databasePassword = databasePassword;
        this.defaultPoolSize = defaultPoolSize;
        this.maxPoolSize = maxPoolSize;
        this.percentageForExpansion = percentageForExpansion;
        this.expansionStep = expansionStep;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public Integer getDefaultPoolSize() {
        return defaultPoolSize;
    }

    public Integer getMaxPoolSize() {
        return maxPoolSize;
    }

    public Integer getPercentageForExpansion() {
        return percentageForExpansion;
    }

    public Integer getExpansionStep() {
        return expansionStep;
    }
}
