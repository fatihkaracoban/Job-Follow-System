package com.fatihkaracoban.job_follow_system.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fatihkaracoban.job_follow_system.entities.Kullanici;
import com.fatihkaracoban.job_follow_system.repository.IKullaniciRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IKullaniciRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Kullanici k = repo.findByKullaniciAdi(username)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı yok!"));
        return User.builder()
                .username(k.getKullaniciAdi())
                .password(k.getSifre())
                .roles(k.getRol().replace("ROLE_", ""))
                .build();
    }
}
