package com.cupidconnect.cupidconnect.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity // là một đối tượng trong cơ sở dữ liệu
@Table(name = "premium_packages")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PremiumPackageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "package_id")
    private Long id;

    @Column(name = "package_name")
    private String packageName;

    @Column(name = "description")
    private String description;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "duration", nullable = false)
    private int duration;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;

    @OneToMany(mappedBy = "premiumPackageEntity")
    private List<UserPurchasesEntity> userPurchasesEntities;

    @OneToMany(mappedBy = "premiumPackageEntity")
    private List<PackageBenefitEntity> packageBenefitEntities;
}
