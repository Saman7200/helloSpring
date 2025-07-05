package com.example.hellospring;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.persistence.*;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "can not empty")
    @Size(min = 2,max = 30 ,message = "you name should be between 2 and 30 letter")

    private String name;

    public Person() {}

    public Person(String name) {
        this.name = name;
    }

    // Getter و Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}