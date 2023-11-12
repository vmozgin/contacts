package com.example.contacts.service;

import com.example.contacts.model.Contact;
import com.example.contacts.repository.ContactsRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ContactsService {

	private final ContactsRepository contactsRepository;


	public ContactsService(ContactsRepository contactsRepository) {
		this.contactsRepository = contactsRepository;
	}

	public List<Contact> findAll() {
		return contactsRepository.findAll();
	}

	public Contact save(Contact contact) {
		return contactsRepository.save(contact);
	}

	public Contact update(Contact contact) {
		return contactsRepository.update(contact);
	}

	public Optional<Contact> findById(UUID id) {
		return contactsRepository.findById(id);
	}

	public void deleteById(UUID id) {
		contactsRepository.deleteById(id);
	}
}
