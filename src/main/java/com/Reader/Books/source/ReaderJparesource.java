package com.Reader.Books.source;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api")
public class ReaderJparesource {

	@Autowired
	ReaderRepository readerrepository;
	
	@Autowired
	BooksRepository booksrepository;
	
	@GetMapping("/Readers")
	public List<Reader> getAllreaders(){
		return readerrepository.findAll();
	}
	@GetMapping("/Readers/{id}")
	public EntityModel<Reader> getReader(@PathVariable int id){
		Optional<Reader> reader=readerrepository.findById(id);
		
		if(!reader.isPresent())
			throw new ReadernotFoundException("id"+id);
		EntityModel<Reader> resource=EntityModel.of(reader.get());
		WebMvcLinkBuilder link=linkTo(methodOn(this.getClass()).getAllreaders());
		resource.add(link.withRel("all-readers"));
		return resource;
	}
	
	@PostMapping("/Readers")
	public ResponseEntity<Object> createreader(@Valid @RequestBody Reader reader){
		Reader Savedreader=readerrepository.save(reader);
		URI uri=ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(Savedreader.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	 
	@GetMapping("/Readers/{id}/books")
	public List<Books> getAllReaders(@PathVariable int id){
	  Optional<Reader> readeroptional=readerrepository.findById(id);
	  if(!readeroptional.isPresent())
		  throw new ReadernotFoundException("id"+id);
	   return readeroptional.get().getBooks();
	  
	}
	
	@PostMapping("/Readers/{id}/books")
	public ResponseEntity<Object> createPost(@PathVariable int id,@RequestBody Books book){
		Optional<Reader> readeroptional=readerrepository.findById(id);
		if(!readeroptional.isPresent())
			throw new ReadernotFoundException("id"+id);
		Reader reader=readeroptional.get();
		book.setReader(reader);
	    booksrepository.save(book);
	    URI location=ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(book.getId()).toUri();
	    return ResponseEntity.created(location).build();
		
	}
	
	
	
	}

