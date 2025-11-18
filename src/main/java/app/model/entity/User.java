package app.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "age", nullable = false)
    private Integer age;
    @Column(name = "created_at")
    private LocalDate createdAt;

    public User(String name, String email, int age) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.createdAt = LocalDate.now();
    }
}
