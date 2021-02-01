package com.guavapay.order.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "card_credentials")
@Data
public class CardCredentials extends BaseEntity {

    @Column(name = "card_number")
    private Long cardNumber;

    @Column(name = "account_number")
    private String accountNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_order_id", referencedColumnName = "id")
    private Order order;
}
