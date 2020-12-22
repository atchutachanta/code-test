package com.anz.banking.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table
public class AccountHolder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String accountName;

        @OneToMany(mappedBy = "accountHolder", fetch = FetchType.LAZY)
        @JsonManagedReference
    private List<Account> accounts;

}
