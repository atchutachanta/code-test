package com.anz.banking.repository;

import com.anz.banking.entity.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountHolderRepository  extends JpaRepository<AccountHolder,Long > {
}
