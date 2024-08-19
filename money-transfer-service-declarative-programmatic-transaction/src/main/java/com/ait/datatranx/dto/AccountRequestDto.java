package com.ait.datatranx.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequestDto {
	private long acctNumber;
	private double balance;
	private String name;
	//private double rate;
}
