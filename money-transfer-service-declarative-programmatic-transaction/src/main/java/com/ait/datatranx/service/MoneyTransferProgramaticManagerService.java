package com.ait.datatranx.service;

import java.text.NumberFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.ait.datatranx.entity.Account;
import com.ait.datatranx.exception.InsufficientAmountException;
import com.ait.datatranx.exception.InvalidAmount;
import com.ait.datatranx.exception.ResourceNotFoundException;
import com.ait.datatranx.repository.IAccountRepository;

@Service
@Qualifier("progTrxManagerBean")
public class MoneyTransferProgramaticManagerService implements IAccountService {

	private final NumberFormat fmt = NumberFormat.getCurrencyInstance();

	@Autowired
	private IAccountRepository accountRepository;

	@Autowired
	private PlatformTransactionManager transactionManager;

	@Override
	public List<Account> getAllAccounts() {
		return accountRepository.findAll();
	}

	@Override
	public void addAccount(Account account) {
		accountRepository.save(account);
	}

	@Override
	public Optional<Account> getAccount(int accountId) {
		return accountRepository.findById((long) accountId);
	}

	@Override
	public void transferMoney(Account from, Account to, double amount, double fee) {
		/**
		 * Transaction consists of two steps: 
		 * 1) withdraw from the sender account an amount.
		 * 2) deposit the same amount to the beneficiary's account
		 */

		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
		TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
		
		try {
			withdraw(from, amount, fee);
			deposit(to, amount);
			transactionManager.commit(transactionStatus);
		} catch (ResourceNotFoundException e) {
			transactionManager.rollback(transactionStatus);
			throw e;
		}

	}

	/**
	 * Deposits the specified amount into the account.
	 */
	private void deposit(Account to, double amount) {
		Account accountToCredit = getAccount(to.getId().intValue()).get();

		if (amount < 0) // deposit value is negative
		{
			throw new InvalidAmount(
					"Error: Deposit amount is invalid." + accountToCredit.getAcctNumber() + " " + fmt.format(amount));

		} else {
			accountToCredit.setBalance(accountToCredit.getBalance() + amount);
			accountToCredit.setLast_operation("Credited");
		}
	}

	/**
	 * Withdraws the specified amount from the account and returns the new balance.
	 */

	private void withdraw(Account from, double amount, double fee) {
		Account accountToDebit = getAccount(from.getId().intValue()).get();

		amount += fee;
		if (amount < 0) // withdraw value is negative
		{
			throw new InvalidAmount("Error: Withdraw amount is invalid. for the Account: "
					+ accountToDebit.getAcctNumber() + " Name: " + accountToDebit.getName());
		} else if (amount > accountToDebit.getBalance()) // withdraw value exceeds balance
		{
			throw new InsufficientAmountException(
					"Error: Insufficient funds.\n Account: " + accountToDebit.getAcctNumber() + "\n" + "Requested:"
							+ fmt.format(amount) + "Available: " + "\n" + fmt.format(accountToDebit.getBalance()));
		} else {
			accountToDebit.setBalance(accountToDebit.getBalance() - amount);
			accountToDebit.setLast_operation("Debited");
		}
	}

}
