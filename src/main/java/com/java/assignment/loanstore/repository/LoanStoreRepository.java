package com.java.assignment.loanstore.repository;

import com.java.assignment.loanstore.model.LoanStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanStoreRepository extends JpaRepository<LoanStore, String> {
    Optional<List<LoanStore>> findByCustomerId(String customerId);
    Optional<List<LoanStore>> findByLenderId(String lenderId);

}
