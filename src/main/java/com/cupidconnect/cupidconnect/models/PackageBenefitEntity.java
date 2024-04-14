package com.cupidconnect.cupidconnect.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // là một đối tượng trong cơ sở dữ liệu
@Table(name = "package_benefits")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackageBenefitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "benefit_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "package_id")
    private PremiumPackageEntity premiumPackageEntity;

    @Column(name = "benefit_description")
    private String benefitDescription;

}
