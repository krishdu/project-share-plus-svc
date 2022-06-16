package com.krishnendu.projectshareplussvc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="posts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid", strategy = "uuid2")
    private String postId;

    @Lob
    private String description;

    @Lob
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_userid", nullable = false, referencedColumnName = "userId")
    private UserEntity user;

    private Date createDate;
    private Date modifyDate;

    @PrePersist
    protected void onCreate() {
        createDate = new Date();
    }
    @PreUpdate
    protected void onUpdate() {
        modifyDate = new Date();
    }
}
