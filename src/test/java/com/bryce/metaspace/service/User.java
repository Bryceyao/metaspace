package com.bryce.metaspace.service;


public class User {
    
    private Integer id;
    
    private String disCode;//区县Code

    private String disName;//区县名称

    private String proName;//省名称
    
    public User() {
        
    }

    public User(Integer id,String disCode) {
        this.id=id;
        this.disCode=disCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDisCode() {
        return disCode;
    }

    public void setDisCode(String disCode) {
        this.disCode = disCode;
    }

    public String getDisName() {
        return disName;
    }

    public void setDisName(String disName) {
        this.disName = disName;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    
}
