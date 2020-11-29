package id.vvangsa.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "TBL_CUSTOMER")
public class Customer {

    @Id
    @Column(name = "ID_CUST", length = 6)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "NM_CUST", length = 50, nullable = false)
    private String nama;

    @Column(name = "ALAMAT", length = 80, nullable = false)
    private String alamat;

    @Column(name = "NM_KOTA", length = 80, nullable = false)
    private String namaKota;

    @Column(name = "PENDAPATAN", precision = 18, scale = 2)
    private BigDecimal pendapatan;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNamaKota() {
        return namaKota;
    }

    public void setNamaKota(String namaKota) {
        this.namaKota = namaKota;
    }

    public BigDecimal getPendapatan() {
        return pendapatan;
    }

    public void setPendapatan(BigDecimal pendapatan) {
        this.pendapatan = pendapatan;
    }
}
