package com.insigno.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerBasicDetailsEntity {
	
	private long sequence_number;
	
	
	private String application_id;
	
	
	private String tenant_id;
	
	
	private String customer_id;
	
	
	private String customer_password;
	
	
	private String customer_email;
	
	
}
