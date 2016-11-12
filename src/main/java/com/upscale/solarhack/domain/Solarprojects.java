package com.upscale.solarhack.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Solarprojects.
 */
@Entity
@Table(name = "solarprojects")
public class Solarprojects implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "productname")
    private String productname;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "size")
    private Long size;

    @Column(name = "cost")
    private Long cost;

    @Column(name = "riskscore")
    private Long riskscore;

    @Column(name = "emission")
    private Long emission;

    @Column(name = "returnOnInvestment")
    private Long returnOnInvestment;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductname() {
        return productname;
    }

    public Solarprojects productname(String productname) {
        this.productname = productname;
        return this;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductType() {
        return productType;
    }

    public Solarprojects productType(String productType) {
        this.productType = productType;
        return this;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Long getSize() {
        return size;
    }

    public Solarprojects size(Long size) {
        this.size = size;
        return this;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getCost() {
        return cost;
    }

    public Solarprojects cost(Long cost) {
        this.cost = cost;
        return this;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public Long getRiskscore() {
        return riskscore;
    }

    public Solarprojects riskscore(Long riskscore) {
        this.riskscore = riskscore;
        return this;
    }

    public void setRiskscore(Long riskscore) {
        this.riskscore = riskscore;
    }

    public Long getReturnOnInvestment() {
        return returnOnInvestment;
    }

    public void setReturnOnInvestment(Long returnOnInvestment) {
        this.returnOnInvestment = returnOnInvestment;
    }

    public Long getEmission() {
        return emission;
    }

    public void setEmission(Long emission) {
        this.emission = emission;
    }

    public User getVendor_id() {
        return user;
    }

    public Solarprojects vendor_id(User user) {
        this.user = user;
        return this;
    }

    public void setVendor_id(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Solarprojects solarprojects = (Solarprojects) o;
        if(solarprojects.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, solarprojects.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Solarprojects{" +
            "id=" + id +
            ", productname='" + productname + "'" +
            ", productType='" + productType + "'" +
            ", size='" + size + "'" +
            ", cost='" + cost + "'" +
            ", riskscore='" + riskscore + "'" +
            ", user='" + user + "'" +
            '}';
    }
}
