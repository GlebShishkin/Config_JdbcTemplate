package ru.stepup.dock_demo.config.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatabaseProperty {

    private String url;
    private String username;
    private String password;
    private String driverClassName;
}