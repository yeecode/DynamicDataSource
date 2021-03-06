package com.github.yeecode.dynamicdatasourcedemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan("com.github.yeecode.dynamicdatasourcedemo.business")
@ComponentScan(basePackages = {"com.github.yeecode.dynamicdatasource","com.github.yeecode.dynamicdatasourcedemo"})
public class DynamicDataSourceDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DynamicDataSourceDemoApplication.class, args);
        System.out.println(
                "\n" +
                        "  _____                              _      _____        _         _____                          \n" +
                        " |  __ \\                            (_)    |  __ \\      | |       / ____|                         \n" +
                        " | |  | |_   _ _ __   __ _ _ __ ___  _  ___| |  | | __ _| |_ __ _| (___   ___  _   _ _ __ ___ ___ \n" +
                        " | |  | | | | | '_ \\ / _` | '_ ` _ \\| |/ __| |  | |/ _` | __/ _` |\\___ \\ / _ \\| | | | '__/ __/ _ \\\n" +
                        " | |__| | |_| | | | | (_| | | | | | | | (__| |__| | (_| | || (_| |____) | (_) | |_| | | | (_|  __/\n" +
                        " |_____/ \\__, |_| |_|\\__,_|_| |_| |_|_|\\___|_____/ \\__,_|\\__\\__,_|_____/ \\___/ \\__,_|_|  \\___\\___|\n" +
                        "          __/ |                                                                                   \n" +
                        "         |___/                                                                                    \n");
        System.out.println("**************************************************");
        System.out.println("Via http://127.0.0.1:12301/01 to query information from datasource01");
        System.out.println("Via http://127.0.0.1:12301/02 to query information from datasource02");
        System.out.println("Via http://127.0.0.1:12301 to query information from datasource01 and datasource02 at the same time.");
        System.out.println("**************************************************");
    }
}
