package co.com.example.authentication_security.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;


    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=]).*$",
            message = "La contraseña debe contener al menos un dígito, una letra y un carácter especial")
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 600, message = "La contraseña debe tener entre 8 y 64 caracteres.")
    @Column(name = "password")
    private String password;
}
