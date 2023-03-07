package com.ftteporal.ft.config;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class VelocityEngineConfig {

    @Bean
    public VelocityEngine velocityEngine() throws Exception {

        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.addProperty("string.resource.loader.class", StringResourceLoader.class.getName());
        velocityEngine.addProperty("string.resource.loader.repository.static", "false");

        Properties properties = new Properties();
        properties.setProperty("input.encoding", "UTF-8");
        properties.setProperty("output.encoding", "UTF-8");
        properties.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.Log4JLogChute");
        properties.setProperty(Velocity.RESOURCE_LOADER, "string");
        properties.setProperty("string.resource.loader.class", org.apache.velocity.runtime.resource.loader.StringResourceLoader.class.getName());
        properties.setProperty("string.resource.loader.repository.static", "false");
        properties.setProperty("runtime.strict_mode.enable", "false");
        properties.setProperty("runtime.references.strict", "false");

        velocityEngine.init(properties);
        return velocityEngine;
    }

}
