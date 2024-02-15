package pl.senla.pricer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_profile",
        uniqueConstraints = @UniqueConstraint(columnNames = {"first_name", "last_name"}))
public class UserProfile implements Serializable {

    @Serial
    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "user_id")
    private Long userId;

}
