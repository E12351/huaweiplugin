package com.huaweiplugin.Entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "authData")
public class authData implements Serializable {

    private static final long serialVersionUID = -3009157732242241606L;
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private long id;

    @Id
    @Column
    private long id;

    @Column(name = "platform")
    private String platform;

    @Column(name = "appId")
    private String appId;

    @Column(name = "secret")
    private String secret;

    public authData() {
    }

    public authData(long id,String platform, String appId, String secret) {
        this.platform = platform;
        this.appId = appId;
        this.secret = secret;
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("authData[id=%d, platform='%s', appId='%s' , secret='%s']", id, platform, appId, secret);
    }
}