package com.example.contacts.model;

import java.util.UUID;
import lombok.Data;

@Data
public class Contact {

	private UUID id;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
}
