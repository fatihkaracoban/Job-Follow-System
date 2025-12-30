package com.fatihkaracoban.job_follow_system.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Malzeme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double birimFiyat;
    private Double toplamFiyat;
    private String malzemeAdi;
    private Integer adet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "is_id", nullable = false)
    private Is is;

    @PrePersist
    @PreUpdate
    public void calculateToplamFiyat() {
        if (this.adet != null && this.birimFiyat != null) {
            this.toplamFiyat = this.adet * this.birimFiyat;
        } else {
            this.toplamFiyat = 0.0;
        }
    }
}
