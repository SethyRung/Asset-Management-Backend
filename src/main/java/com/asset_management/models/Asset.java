package com.asset_management.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String serialNumber;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private String status;  // E.g., Active, In Repair, Retired

    private String location;

    private Boolean isDeleted = false;

    @Temporal(TemporalType.DATE)
    private Date acquisitionDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User assignedTo;

    @Temporal(TemporalType.DATE)
    private Date warrantyExpiryDate;

    private String documents;
}

