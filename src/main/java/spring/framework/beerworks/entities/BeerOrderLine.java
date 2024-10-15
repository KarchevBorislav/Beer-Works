package spring.framework.beerworks.entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerOrderLine {


    @Id
    @GeneratedValue(generator = "UUID")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @Version
    private Integer version;


    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createDate;

    @UpdateTimestamp
    private Timestamp lastModifiedDate;

    private Integer quantity = 0;
    private Integer quantityAllocated = 0;


    @ManyToOne
    private BeerOrder beerOrder;

    @ManyToOne
    private Beer beerId;

    public boolean isNew() {
        return this.id == null;
    }


}
