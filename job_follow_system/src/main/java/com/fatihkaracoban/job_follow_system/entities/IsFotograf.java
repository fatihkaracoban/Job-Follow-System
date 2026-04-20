package com.fatihkaracoban.job_follow_system.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "is_fotograflari")
public class IsFotograf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String dosyaAdi; // Fotoğrafın diske kaydedilen adı (örn: 1234-5678.jpg)

    @ManyToOne
    @JoinColumn(name = "is_id", nullable = false)
    private Is is;

}
