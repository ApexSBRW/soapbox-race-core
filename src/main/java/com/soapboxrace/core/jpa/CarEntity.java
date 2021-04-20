/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "CAR")
@NamedQueries({
        @NamedQuery(name = "CarEntity.findByPersonaId", //
                query = "SELECT obj FROM CarEntity obj WHERE obj.persona.personaId = :persona ORDER BY obj.id"),
        @NamedQuery(name = "CarEntity.findByPersonaIdEager", //
                query = "SELECT obj FROM CarEntity obj WHERE obj.persona.personaId = :persona ORDER BY obj.id"), //
        @NamedQuery(name = "CarEntity.findNumNonRentalsByPersonaId", //
                query = "SELECT COUNT(obj) FROM CarEntity obj  WHERE obj.persona.personaId = :persona" +
                        " AND obj.expirationDate IS NULL"), //
        @NamedQuery(name = "CarEntity.findNumByPersonaId",
                query = "SELECT COUNT(obj) FROM CarEntity obj WHERE obj.persona.personaId = :persona"),
        @NamedQuery(name = "CarEntity.deleteByPersona", //
                query = "DELETE FROM CarEntity obj WHERE obj.persona = :persona"), //
        @NamedQuery(name = "CarEntity.deleteAllExpired", //
                query = "DELETE FROM CarEntity obj  WHERE obj.expirationDate <= CURRENT_TIMESTAMP")
})
public class CarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = PersonaEntity.class, cascade = CascadeType.DETACH, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "personaId", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_CAR_PERSONA_personaId"))
    private PersonaEntity persona;

    private int durability;
    private float heat;
    private LocalDateTime expirationDate;
    private String ownershipType = "CustomizedCar";

    private int baseCar;
    private int carClassHash;
    private boolean isPreset;
    private int level;
    private String name;
    private int physicsProfileHash;
    private int rating;
    private float resalePrice;
    private float rideHeightDrop;
    private int skillModSlotCount;
    private int version;

    @OneToMany(mappedBy = "car", targetEntity = PaintEntity.class,
            orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @LazyCollection(LazyCollectionOption.FALSE)
    @Fetch(FetchMode.SUBSELECT)
    private Set<PaintEntity> paints;

    @OneToMany(mappedBy = "car", targetEntity = PerformancePartEntity.class,
            orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @LazyCollection(LazyCollectionOption.FALSE)
    @Fetch(FetchMode.SUBSELECT)
    private Set<PerformancePartEntity> performanceParts;

    @OneToMany(mappedBy = "car", targetEntity = SkillModPartEntity.class,
            orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @LazyCollection(LazyCollectionOption.FALSE)
    @Fetch(FetchMode.SUBSELECT)
    private Set<SkillModPartEntity> skillModParts;

    @OneToMany(mappedBy = "car", targetEntity = VinylEntity.class,
            orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @LazyCollection(LazyCollectionOption.FALSE)
    @Fetch(FetchMode.SUBSELECT)
    private Set<VinylEntity> vinyls;

    @OneToMany(mappedBy = "car", targetEntity = VisualPartEntity.class,
            orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @LazyCollection(LazyCollectionOption.FALSE)
    @Fetch(FetchMode.SUBSELECT)
    private Set<VisualPartEntity> visualParts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonaEntity getPersona() {
        return persona;
    }

    public void setPersona(PersonaEntity personaEntity) {
        this.persona = personaEntity;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public float getHeat() {
        return heat;
    }

    public void setHeat(float heat) {
        this.heat = heat;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getOwnershipType() {
        return ownershipType;
    }

    public void setOwnershipType(String ownershipType) {
        this.ownershipType = ownershipType;
    }

    public int getBaseCar() {
        return baseCar;
    }

    public void setBaseCar(int baseCar) {
        this.baseCar = baseCar;
    }

    public int getCarClassHash() {
        return carClassHash;
    }

    public void setCarClassHash(int carClassHash) {
        this.carClassHash = carClassHash;
    }

    public void setCarClassHashFromClassInt(int finalClassInt) {
        const int CLASSHASH_E = 872416321;
        const int CLASSHASH_D = 415909161;
        const int CLASSHASH_C = 1866825865;
        const int CLASSHASH_B = -406473455;
        const int CLASSHASH_A = -406473455;
        const int CLASSHASH_S = -2142411446;

        int carclassHash = CLASSHASH_E;
        if (finalClassInt >= 250 && finalClassInt < 400) {
            carclassHash = CLASSHASH_D;
        } else if (finalClassInt >= 400 && finalClassInt < 500) {
            carclassHash = CLASSHASH_C;
        } else if (finalClassInt >= 500 && finalClassInt < 600) {
            carclassHash = CLASSHASH_B;
        } else if (finalClassInt >= 600 && finalClassInt < 750) {
            carclassHash = CLASSHASH_A;
        } else if (finalClassInt >= 750) {
            carclassHash = CLASSHASH_S;
        }

        this.carClassHash = carclassHash;
    }

    public boolean isPreset() {
        return isPreset;
    }

    public void setPreset(boolean isPreset) {
        this.isPreset = isPreset;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhysicsProfileHash() {
        return physicsProfileHash;
    }

    public void setPhysicsProfileHash(int physicsProfileHash) {
        this.physicsProfileHash = physicsProfileHash;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public float getResalePrice() {
        return resalePrice;
    }

    public void setResalePrice(float resalePrice) {
        this.resalePrice = resalePrice;
    }

    public float getRideHeightDrop() {
        return rideHeightDrop;
    }

    public void setRideHeightDrop(float rideHeightDrop) {
        this.rideHeightDrop = rideHeightDrop;
    }

    public int getSkillModSlotCount() {
        return skillModSlotCount;
    }

    public void setSkillModSlotCount(int skillModSlotCount) {
        this.skillModSlotCount = skillModSlotCount;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Set<PerformancePartEntity> getPerformanceParts() {
        return performanceParts;
    }

    public void setPerformanceParts(Set<PerformancePartEntity> performanceParts) {
        this.performanceParts = performanceParts;
    }

    public Set<SkillModPartEntity> getSkillModParts() {
        return skillModParts;
    }

    public void setSkillModParts(Set<SkillModPartEntity> skillModParts) {
        this.skillModParts = skillModParts;
    }

    public Set<VisualPartEntity> getVisualParts() {
        return visualParts;
    }

    public void setVisualParts(Set<VisualPartEntity> visualParts) {
        this.visualParts = visualParts;
    }

    public Set<PaintEntity> getPaints() {
        return paints;
    }

    public void setPaints(Set<PaintEntity> paints) {
        this.paints = paints;
    }

    public Set<VinylEntity> getVinyls() {
        return vinyls;
    }

    public void setVinyls(Set<VinylEntity> vinyls) {
        this.vinyls = vinyls;
    }
}
