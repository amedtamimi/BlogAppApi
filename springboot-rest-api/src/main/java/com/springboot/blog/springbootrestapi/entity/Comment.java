package com.springboot.blog.springbootrestapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments", uniqueConstraints = {@UniqueConstraint(columnNames= {"email"})})
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
    @Column(name = "body", nullable = false)
      private String body;

    @Column(name = "email", nullable = false)
      private String email;

    @Column(name = "name", nullable = false)
      private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post", nullable = false)
      private Post post;

}
