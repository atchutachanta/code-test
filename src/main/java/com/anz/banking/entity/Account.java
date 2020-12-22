package com.anz.banking.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table
public class Account extends Auditable<Account> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountNumber;

    /*
        @Column(name = "ACCOUNT_HOLDER_ID", insertable = false, updatable = false)
        private Long accountHolderId;
    */
    @NotNull
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Currency currency;

    private BigDecimal balance;
    private LocalDate balanceDate;

    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    @JoinColumn(name = "ACCOUNT_HOLDER_ID", nullable = false)
    @JsonBackReference
    private AccountHolder accountHolder;


    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Transaction> transactions;

    public void setBalance(BigDecimal balance) {
        if (balance != null && !balance.equals(this.balance))
            this.setBalanceDate(LocalDateTime.now().toLocalDate());
        this.balance = balance;
    }

    public enum AccountType {
        SAVINGS, CURRENT
    }

    public enum Currency {
        SGD, AUD
    }

}
