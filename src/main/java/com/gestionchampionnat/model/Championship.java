package com.gestionchampionnat.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
@Table(name = "championship")
public class Championship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotNull(message = "Le champ ne peut pas être null")
    @NotBlank(message = "Le champ ne peut pas être vide")
    private String name;

    @Column(name = "start_date")
    @NotNull(message = "Le champ ne peut pas être null")
    private Date startDate;

    @Column(name = "end_date")
    @NotNull(message = "Le champ ne peut pas être null")
    private Date endDate;

    @Column(name = "won_point")
    private int wonPoint;

    @Column(name = "lost_point")
    private int lostPoint;

    @Column(name = "draw_point")
    private int drawPoint;

    public Championship(String name, Date startDate, Date endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.wonPoint = 0;
        this.lostPoint = 0;
        this.drawPoint = 0;
    }

    public Championship() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getWonPoint() {
        return wonPoint;
    }

    public void setWonPoint(int wonPoint) {
        this.wonPoint = wonPoint;
    }

    public int getLostPoint() {
        return lostPoint;
    }

    public void setLostPoint(int lostPoint) {
        this.lostPoint = lostPoint;
    }

    public int getDrawPoint() {
        return drawPoint;
    }

    public void setDrawPoint(int drawPoint) {
        this.drawPoint = drawPoint;
    }
}
