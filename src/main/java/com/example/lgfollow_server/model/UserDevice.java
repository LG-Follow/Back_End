package com.example.lgfollow_server.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "user_device")
public class UserDevice {
    @EmbeddedId
    private UserDeviceId id; // compositekey 형성을 위한 Class

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "userId")
    @JsonBackReference
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("deviceId")
    @JoinColumn(name = "device_id") //deviceId -> device_id
    @JsonBackReference
    private Device device;

    @Column
    private String location;

    @Column
    private boolean isActive;
}
