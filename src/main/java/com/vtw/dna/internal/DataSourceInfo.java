package com.vtw.dna.internal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class DataSourceInfo {

    private String databaseProduct;
    private String name;
    private String description;
    private String url;
    private String username;
    private String password;

    @JsonIgnore
    public String getDriverClassName() {
        switch (databaseProduct) {
            case "PostgreSQL":
                return "org.postgresql.Driver";
            case "MySQL":
                return "com.mysql.jdbc.Driver";
            case "Oracle":
                return "oracle.jdbc.OracleDriver";
            case "Tibero":
                return "com.tmax.tibero.jdbc.TbDriver";
            default:
                return "org.postgresql.Driver";
        }
    }
}
