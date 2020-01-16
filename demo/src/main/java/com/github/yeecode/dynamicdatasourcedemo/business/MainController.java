package com.github.yeecode.dynamicdatasourcedemo.business;


import com.github.yeecode.dynamicdatasource.DynamicDataSource;
import com.github.yeecode.dynamicdatasource.model.DataSourceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MainController {
    @Autowired
    private UserService userService;
    @Autowired
    private DynamicDataSource dynamicDataSource;

    @RequestMapping(value = "/01")
    public String queryFromDS01() {
        DataSourceInfo dataSourceInfo = new DataSourceInfo("ds01",
                "com.mysql.cj.jdbc.Driver",
                "jdbc:mysql://localhost:3306/datasource01?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8",
                "root",
                "yeecode");
        dynamicDataSource.addDataSource(dataSourceInfo, true);
        dynamicDataSource.switchDataSource("ds01");
        return userService.select();
    }

    @RequestMapping(value = "/02")
    public String queryFromDS02() {
        DataSourceInfo dataSourceInfo = new DataSourceInfo("ds02",
                "com.mysql.cj.jdbc.Driver",
                "jdbc:mysql://localhost:3306/datasource02?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8",
                "root",
                "yeecode");
        dynamicDataSource.addAndSwitchDataSource(dataSourceInfo, true);
        return userService.select();
    }

    @RequestMapping(value = "/")
    public String queryFromDS() {
        DataSourceInfo dataSourceInfo = new DataSourceInfo("ds01",
                "com.mysql.cj.jdbc.Driver",
                "jdbc:mysql://localhost:3306/datasource01?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8",
                "root",
                "yeecode");
        dynamicDataSource.addAndSwitchDataSource(dataSourceInfo,true);
        String out = userService.select();

        dataSourceInfo = new DataSourceInfo("ds02",
                "com.mysql.cj.jdbc.Driver",
                "jdbc:mysql://localhost:3306/datasource02?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8",
                "root",
                "yeecode");
        dynamicDataSource.addAndSwitchDataSource(dataSourceInfo,true);
        out += "<br>";
        out += userService.select();
        return out;
    }
}
