package com.example.sbsj_process.account.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@NoArgsConstructor
public class Member {

    @Id
    @Getter
    @Column(length = 16)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberNo;

    @Column(length = 16,unique = true, nullable = false)
    private String id;

    public Member(String id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Authentication> authentications = new HashSet<>();

    public boolean isRightPassword(String plainToCheck) {
        final Optional<Authentication> maybeBasicAuth = findBasicAuthentication();

        if (maybeBasicAuth.isPresent()) {
            final BasicAuthentication authentication = (BasicAuthentication) maybeBasicAuth.get();
            return authentication.isRightPassword(plainToCheck);
        }

        return false;
    }

    private Optional<Authentication> findBasicAuthentication () {
        return authentications
                .stream()
                .filter(authentication -> authentication instanceof BasicAuthentication)
                .findFirst();
    }

}