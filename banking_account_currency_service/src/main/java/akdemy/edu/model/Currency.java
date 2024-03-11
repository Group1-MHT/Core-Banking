package akdemy.edu.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "currencies")
@Builder
public class Currency {
    @Id
    @Column(name = "currency_code", nullable = false)
    private String currencyCode;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "symbol", nullable = false, unique = true)
    private String symbol;
}
