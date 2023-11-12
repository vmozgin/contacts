package com.example.contacts.repository;

import com.example.contacts.exception.ContactNofFoundException;
import com.example.contacts.model.Contact;
import com.example.contacts.repository.mapper.ContactRowMapper;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ContactsRepository {

	private final JdbcTemplate jdbcTemplate;

	public List<Contact> findAll() {
		String sql  = "SELECT * FROM contacts";

		return jdbcTemplate.query(sql, new ContactRowMapper());
	}

	public Optional<Contact> findById(UUID id) {
		String sql = "SELECT * FROM contacts WHERE id = ?";
		Contact contact = DataAccessUtils.singleResult(
				jdbcTemplate.query(
						sql,
						new ArgumentPreparedStatementSetter(new Object[]{id}),
						new RowMapperResultSetExtractor<>(new ContactRowMapper(), 1)
				)
		);

		return Optional.ofNullable(contact);
	}

	public Contact save(Contact contact) {
		contact.setId(UUID.randomUUID());
		String sql = "INSERT INTO contacts (id, first_name, last_name, email, phone) VALUES (?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, contact.getId(), contact.getFirstName(), contact.getLastName(), contact.getEmail(), contact.getPhone());

		return contact;
	}

	public Contact update(Contact contact) {
		Contact existedContact = findById(contact.getId()).orElse(null);

		if (existedContact != null) {
			String sql = "UPDATE CONTACTS SET first_name = ?, last_name = ?, email = ?, phone = ? WHERE id = ?";
			jdbcTemplate.update(sql, contact.getFirstName(), contact.getLastName(), contact.getEmail(), contact.getPhone(), contact.getId());
			return contact;
		}

		throw new ContactNofFoundException(String.format("Contact with id = %s not found ", contact.getId()));
	}

	public void deleteById(UUID id) {
		String sql = "DELETE FROM contacts WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}
}
