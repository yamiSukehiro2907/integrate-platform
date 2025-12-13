package com.integrate.core.domain;

import com.integrate.core.enums.EndpointStatus;
import com.integrate.core.enums.Method;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "endpoints", indexes = {@Index(name = "idx_endpoints_unique_id", columnList = "uniqueId")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Endpoint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Method method;

    @Column(nullable = false)
    private String path;

    @Column(nullable = false, unique = true, length = 12)
    private String uniqueId;

    private String summary;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EndpointStatus status;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "version_id", nullable = false)
    private ContractVersion contractVersion;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
