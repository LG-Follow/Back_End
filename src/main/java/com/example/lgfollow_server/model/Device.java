package com.example.lgfollow_server.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "device")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String device_name;

    @Column
    private String device_model;

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<UserDevice> userDevices = new ArrayList<>();
}
