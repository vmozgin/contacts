package com.example.contacts.controller;

import com.example.contacts.model.Contact;
import com.example.contacts.service.ContactsService;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactsController {

	private final ContactsService contactsService;

	public ContactsController(ContactsService contactsService) {
		this.contactsService = contactsService;
	}

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("contacts", contactsService.findAll());
		return "index";
	}

	@GetMapping( value = {"/contact/change/{id}", "/contact/change"})
	public String showChangeForm(Model model, @PathVariable(required = false) UUID id) {
		if (id != null) {
			Optional<Contact> contact = contactsService.findById(id);
			if (contact.isPresent()) {
				model.addAttribute("contact", contact.get());
			} else
				model.addAttribute("contact", new Contact());
		} else
			model.addAttribute("contact", new Contact());

		return "change";
	}

	@PostMapping("/contact/create")
	public String createContact(@ModelAttribute Contact contact) {
		if (contact.getId() != null) {
			contactsService.update(contact);
			return "redirect:/";
		} else
			contact.setId(UUID.randomUUID());
			contactsService.save(contact);
			return "redirect:/";
	}

	@GetMapping("/contact/delete/{id}")
	public String deleteContact(@PathVariable UUID id) {
		if (contactsService.findById(id).isPresent()) {
			contactsService.deleteById(id);
		}
		return "redirect:/";
	}
}
