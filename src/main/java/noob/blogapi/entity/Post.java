package noob.blogapi.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor


@Entity
@Table(
        name = "posts",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}
)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title",nullable = false)
    private String title;
    @Column(name = "description",nullable = false)
    private String description;
    @Column(name = "content",nullable = false)
    private String content;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<Comment> comments = new HashSet<>();
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post post)) return false;

        return getId() != null ? getId().equals(post.getId()) : post.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
