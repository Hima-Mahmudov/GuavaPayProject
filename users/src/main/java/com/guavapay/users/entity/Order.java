package com.guavapay.users.entity;

import com.guavapay.users.entity.enums.CardPeriod;
import com.guavapay.users.entity.enums.CardType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseEntity{

    @Column(name = "card_type")
    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @Column(name = "card_holder_name")
    private String cardHolderName;

    @Column(name = "card_period")
    @Enumerated(EnumType.STRING)
    private CardPeriod cardPeriod;

    @Column(name = "urgent")
    private boolean urgent;

    @Column(name = "codeword")
    private String codeword;

    @Column(name = "submitted")
    private boolean submitted;

    @ManyToOne
    @JoinColumn(name = "fk_user_id")
    private User user;
}
