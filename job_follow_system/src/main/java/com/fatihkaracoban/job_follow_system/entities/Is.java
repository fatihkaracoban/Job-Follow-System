package com.fatihkaracoban.job_follow_system.entities;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "isler")
public class Is {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String isAdi;
    private Double ucret;

    @DateTimeFormat(pattern = "yyyy-MM-dd") 
    private LocalDate baslamaTarihi;
    
    private String durum; 

    @OneToMany(mappedBy = "is", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Malzeme> malzemeler;
}