package com.yuji.polygon.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @className: Permission
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-13 15:43:00
 */
public class Permission {

    private int id;

    private int pid;

    private String type;

    private Date gmtCreate;

    private Date gmtModified;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}
