package com.huaweiplugin.Entity;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "deviceData")
public class deviceData implements Serializable {

    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Id
    @Column(name = "mac")
    private String mac;

    @Column(name = "deviceId")
    private String deviceId;

    @Column(name = "verifyCode")
    private String verifyCode;

    @Column(name = "psk")
    private String psk;

    public deviceData() {

    }

    public deviceData( String mac, String deviceId, String verifyCode, String psk) {
        this.mac        = mac;
        this.deviceId   = deviceId;
        this.verifyCode = verifyCode;
        this.psk        = psk;
    }

    @Override
    public String toString() {
        return String.format("authData[id=%d, mac='%s', deviceId='%s' , verifyCode='%s' , , psk='%s']", id, mac, deviceId, verifyCode, psk);
    }
}