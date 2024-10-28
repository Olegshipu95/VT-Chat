package itmo.high_perf_sys.chat.model.entity;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "phone")
    private String phone;
    @Column(name = "status")
    private String status;
    @Column(name = "city")
    private String city;
    @Column(name = "bith_date")
    private LocalDate bith_date;
    @Column(name = "marital_status")
    private MaritalStatus marital_status;
    @Column(name = "gender")
    private boolean gender;
    public Long getId(){
        return this.id;
    }
}
