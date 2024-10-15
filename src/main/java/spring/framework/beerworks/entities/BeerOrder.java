package spring.framework.beerworks.entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.security.Timestamp;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class BeerOrder {


    @Id
    @GeneratedValue(generator = "UUID")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36,columnDefinition = "varchar(36)",updatable = false,nullable = false)
    private UUID id;

    @Version
    private Integer version;


    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createDate;

    public boolean isNew(){
        return this.id == null;
    }

    private String customerRef;

    @ManyToOne
    private Customer customer;


    @OneToMany(mappedBy = "beerOrder")
    private Set<BeerOrderLine> beerOrderLines;

}
