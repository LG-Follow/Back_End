package com.example.lgfollow_server.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @JsonBackReference
    private Users user;

    @Column
    private String imageName;

    @Column
    private String imageUrl;

    @Column
    private String imageType;

    @Column
    private double width;

    @Column
    private double height;

    @Column
    private double size;

    @Column
    private boolean isActive;

    @Column
    private int version;

    @Column
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "image")
    @JsonManagedReference
    private Image image;

}
