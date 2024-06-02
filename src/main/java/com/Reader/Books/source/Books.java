package com.Reader.Books.source;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class Books {

	@Id
	@GeneratedValue
	private Integer id;
	
	private String description;
	
	@Lob
	private byte[] file;
	
	public byte[] getFile() {
		return file;
	}
	public void setFile(byte[] file) {
		this.file = file;
	}


	@ManyToOne(fetch=FetchType.LAZY)
	@JsonIgnore
	private Reader reader;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Reader getReader() {
		return reader;
	}
	public void setReader(Reader reader) {
		this.reader = reader;
	}
	
	
	@Override
	public String toString() {
		return String.format("Post [id=%s, description=%s]", id, description);
	}
}
