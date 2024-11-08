package com.example.lgfollow_server.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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
    private boolean isActive;

    @Column
    private Long version;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "image")
    @JsonManagedReference
    private Prompt prompt;

}
