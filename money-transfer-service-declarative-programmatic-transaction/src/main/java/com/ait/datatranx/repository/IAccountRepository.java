package com.ait.datatranx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ait.datatranx.entity.Account;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {
	
}