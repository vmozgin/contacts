package com.example.contacts.repository.mapper;

import com.example.contacts.model.Contact;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.springframework.jdbc.core.RowMapper;

public class ContactRowMapper implements RowMapper<Contact> {
	@Override
	public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
		Contact contact = new Contact();
		contact.setId((UUID) rs.getObject("id"));
		contact.setFirstName(rs.getString("first_name"));
		contact.setLastName(rs.getString("last_name"));
		contact.setEmail(rs.getString("email"));
		contact.setPhone(rs.getString("phone"));
		return contact;
	}
}
