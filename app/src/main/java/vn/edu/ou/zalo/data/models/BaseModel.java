package vn.edu.ou.zalo.data.models;

import java.util.Date;

public abstract class BaseModel {
    private String id;
    private long createdAt;
    private long updatedAt;

    public BaseModel() {
        createdAt = new Date().getTime();
        updatedAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
