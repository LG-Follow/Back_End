package com.example.lgfollow_server.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

@Embeddable
public class UserDeviceId implements Serializable { //UserDevice의 composite key 생성을 위한 Class임
    @Column(name = "userId")
    private Long userId;

    @Column(name = "deviceId")
    private Long deviceId;
}
