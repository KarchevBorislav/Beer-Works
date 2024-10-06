package spring.framework.beerworks.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {


    @Id
    @GeneratedValue( generator = "UUID")
    @Column(length = 36,columnDefinition = "varchar",updatable = false,nullable = false)
    private UUID id;

    private String customerName;
    
    @Version
    private Integer version;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
