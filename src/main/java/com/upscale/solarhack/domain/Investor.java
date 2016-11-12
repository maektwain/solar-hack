package com.upscale.solarhack.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Investor.
 */
@Entity
@Table(name = "investor")
public class Investor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "tenure")
    private Integer tenure;

    @ManyToOne
    private User user;

    @ManyToOne
    private Solarprojects solarprojects;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public Investor amount(Long amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Integer getTenure() {
        return tenure;
    }

    public Investor tenure(Integer tenure) {
        this.tenure = tenure;
        return this;
    }

    public void setTenure(Integer tenure) {
        this.tenure = tenure;
    }

    public User getUser() {
        return user;
    }

    public Investor user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Solarprojects getSolarprojects() {
        return solarprojects;
    }

    public Investor solarprojects(Solarprojects solarprojects) {
        this.solarprojects = solarprojects;
        return this;
    }

    public void setSolarprojects(Solarprojects solarprojects) {
        this.solarprojects = solarprojects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Investor investor = (Investor) o;
        if(investor.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, investor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Investor{" +
            "id=" + id +
            ", amount='" + amount + "'" +
            ", tenure='" + tenure + "'" +
            '}';
    }
}
