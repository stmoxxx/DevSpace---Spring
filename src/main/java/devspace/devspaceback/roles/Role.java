package devspace.devspaceback.roles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import devspace.devspaceback.models.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<UserEntity> users;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime registerDate;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastOnlineTime;
}
