package com.java110.code;

import java.util.List;
import java.util.Map;

public class Data {

    private String packagePath;

    private String id;

    private String name;

    private String desc;

    private String newBusinessTypeCd;

    private String updateBusinessTypeCd;

    private String deleteBusinessTypeCd;

    private String businessTableName;

    private String tableName;

    private Map params;

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map getParams() {
        return params;
    }

    public void setParams(Map params) {
        this.params = params;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public String getBusinessTableName() {
        return businessTableName;
    }

    public void setBusinessTableName(String businessTableName) {
        this.businessTableName = businessTableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getNewBusinessTypeCd() {
        return newBusinessTypeCd;
    }

    public void setNewBusinessTypeCd(String newBusinessTypeCd) {
        this.newBusinessTypeCd = newBusinessTypeCd;
    }

    public String getUpdateBusinessTypeCd() {
        return updateBusinessTypeCd;
    }

    public void setUpdateBusinessTypeCd(String updateBusinessTypeCd) {
        this.updateBusinessTypeCd = updateBusinessTypeCd;
    }

    public String getDeleteBusinessTypeCd() {
        return deleteBusinessTypeCd;
    }

    public void setDeleteBusinessTypeCd(String deleteBusinessTypeCd) {
        this.deleteBusinessTypeCd = deleteBusinessTypeCd;
    }
}