package com.example.project.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String password;
    private boolean enabled;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    @ManyToMany(mappedBy = "employees")
    private List<Project> projects;

    @OneToMany(mappedBy = "author")
    private List<Task> authoredTasks;

    @OneToMany(mappedBy = "executor")
    private List<Task> executedTasks;


    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "employees", cascade = CascadeType.ALL)
    private Collection<Role> roles;

    public void addRole(Role role) {
        this.roles.add(role);
        role.getEmployees().add(this);
    }

    public void removeRole(long roleId) {
        Role role = this.roles.stream().filter(t -> t.getId() == roleId).findFirst().orElse(null);
        if (role != null) {
            this.roles.remove(role);
            role.getEmployees().remove(this);
        }
    }
}

