package user;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "age")
    private int age;
    @Column(name = "created_at")
    private LocalDate created_at;

    public User(String name,String email,int age){
        this.name = name;
        this.age = age;
        this.email = email;
        this.created_at = LocalDate.now();
    }

    public void showInConsole(){
        System.out.printf("%d. %s %d год email: %s create:%s\n",
                this.getId(), this.getName(), this.getAge(), this.getEmail(), this.getCreated_at());
    }
}
