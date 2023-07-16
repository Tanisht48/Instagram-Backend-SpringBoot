package com.geekster.Instagram.backend.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "InstaPost")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private PostType postType;
    private String postData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_User_Id")
    private User user;
}
