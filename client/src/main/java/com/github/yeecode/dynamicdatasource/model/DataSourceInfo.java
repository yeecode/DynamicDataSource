package com.github.yeecode.dynamicdatasource.model;


import javax.sql.DataSource;

public class DataSourceInfo {
    private String name;
    private String driverClassName;
    private String url;
    private String userName;
    private String password;

    public DataSourceInfo(String name, String driverClassName, String url, String userName, String password) {
        this.name = name;
        this.driverClassName = driverClassName;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DataSourceInfo) {
            DataSourceInfo other = (DataSourceInfo) obj;
            return equalString(this.name, other.getName()) &&
                    equalString(this.driverClassName, other.getDriverClassName()) &&
                    equalString(this.url, other.getUrl()) &&
                    equalString(this.userName, other.getUserName()) &&
                    equalString(this.password, other.getPassword());
        } else {
            return false;
        }
    }

    private boolean equalString(String a, String b) {
        return (a != null && a.equals(b)) || (a == null && b == null);
    }
}
