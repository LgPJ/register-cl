package com.cl.registercl.utils;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


@Service
public class RegexProperties {

    Properties properties;

    public RegexProperties() throws FileNotFoundException, IOException {
        this.properties = new Properties();
        this.properties.load(new FileInputStream(new File("./src/main/resources/application.properties")));

    }

    public String get(String nameProperties) throws FileNotFoundException, IOException {
        return properties.get(nameProperties).toString();
    }


}
