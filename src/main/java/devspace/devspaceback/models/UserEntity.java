package devspace.devspaceback.models;

import devspace.devspaceback.models.media.UserMedia;
import devspace.devspaceback.roles.Role;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account")
@EntityListeners(AuditingEntityListener.class)
public class UserEntity implements UserDetails, Principal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;

    private String lastname;

    private String nickname;

    @JoinColumn(unique = true)
    private String email;

    private String password;

    private String bio;

    private boolean accountLocked;

    private boolean enabled;

//    @OneToMany(mappedBy = "user_entity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<UserMedia> userMedia;
//
//    @ManyToMany
//    @JoinTable(name = "friends", joinColumns = @JoinColumn(name = "user1_id"), inverseJoinColumns = @JoinColumn(name = "user2_id"))
//    private List<UserEntity> friends;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

//    @OneToMany(fetch = FetchType.EAGER)
//    private List<PostEntity> posts;

    @CreatedDate
    @JoinColumn(nullable = false, updatable = false)
    private LocalDateTime registerDate;

    @LastModifiedDate
    @JoinColumn(insertable = false)
    private LocalDateTime lastOnlineTime;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getRoleName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getName() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public String getFullName(){
        return firstname + " " + lastname;
    }

}
