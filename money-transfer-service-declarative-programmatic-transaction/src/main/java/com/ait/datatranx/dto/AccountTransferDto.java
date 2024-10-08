package com.ait.datatranx.dto;
import com.ait.datatranx.entity.Account;


public class AccountTransferDto {
	private Account from;
	private Account to;
	private double amount;
	private int currency_code;
	
	
	public AccountTransferDto(Account from, Account to, double amount) {
		super();
		this.from = from;
		this.to = to;
		this.amount = amount;
	}
	
	public Account getFrom() {
		return from;
	}
	public void setFrom(Account from) {
		this.from = from;
	}
	public Account getTo() {
		return to;
	}
	public void setTo(Account to) {
		this.to = to;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getCurrency_code() {
		return currency_code;
	}
	public void setCurrency_code(int currency_code) {
		this.currency_code = currency_code;
	}

	@Override
	public String toString() {
		return "AccountTransferDto [from=" + from + ", to=" + to + ", amount=" + amount + ", currency_code="
				+ currency_code + "]";
	}
    
	
}
