package com.Reader.Books.source;

import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Entity
public class Reader {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Size(min = 2, message = "name should minimum 2 character")
	private String name;
	
	@Past(message = "past date only allowed here")
	private Date birthdate;
	
	@OneToMany(mappedBy = "reader")
	private List<Books> books;
	
	protected Reader() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public List<Books> getBooks() {
		return books;
	}

	public void setBooks(List<Books> books) {
		this.books = books;
	}

	public Reader(Integer id, @Size(min = 2, message = "name should minimum 2 character") String name,
			@Past(message = "past date only allowed here") Date birthdate) {
		super();
		this.id = id;
		this.name = name;
		this.birthdate = birthdate;
	}

	@Override
	public String toString() {
		return String.format("Reader [id=%s, name=%s, birthDate=%s]", id, name, birthdate);
	}
	

}
