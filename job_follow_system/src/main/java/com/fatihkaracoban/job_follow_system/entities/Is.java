package com.fatihkaracoban.job_follow_system.entities;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "İş adı boş bırakılamaz!")
    private String isAdi;

    @NotNull(message = "Ücret girilmelidir!")
    @Min(value = 0, message = "Ücret 0'dan küçük olamaz!")
    private Double ucret;

    @NotNull(message = "Başlama tarihi seçilmelidir!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate baslamaTarihi;

    @Column(length = 500)
    private String adres;

    private String durum = "Yapılacak";

    private boolean aktif = true; // Yumuşak silme

    @OneToMany(mappedBy = "is", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Malzeme> malzemeler;

    // Is.java içindeki fotoğraf alanı
    @OneToMany(mappedBy = "is", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IsFotograf> fotograflar = new java.util.ArrayList<>();

// Getter ve Setter'ını eklemeyi unutma
    public java.util.List<IsFotograf> getFotograflar() {
        return fotograflar;
    }

    public void setFotograflar(java.util.List<IsFotograf> fotograflar) {
        this.fotograflar = fotograflar;
    }

}
