package com.anz.banking.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table
public class Transaction extends Auditable<Account> implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    public enum TransactionType {
        CREDIT,DEBIT
    }

    @NotNull
    @Enumerated(EnumType.STRING)
    private Currency currency;

    public enum Currency {
        SGD,AUD
    }

    private BigDecimal creditAmount;
    private BigDecimal debitAmount;

    @LastModifiedDate
    private LocalDate valueDate;// = LocalDateTime.now().toLocalDate();
    private String transactionNarrative;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ACCOUNT_ACCOUNT_NUMBER", nullable=false)
    @JsonBackReference
    private Account  account;

}
