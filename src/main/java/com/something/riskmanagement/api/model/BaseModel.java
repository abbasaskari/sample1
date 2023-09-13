package com.something.riskmanagement.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

public abstract class BaseModel implements Serializable {

    private Long id;
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}
