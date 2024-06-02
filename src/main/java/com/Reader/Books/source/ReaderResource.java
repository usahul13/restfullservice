package com.Reader.Books.source;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class ReaderResource {

	@Autowired
	private ReaderDaoservice service;

	
	@GetMapping("/users")
	public List<Reader> retrieveAllReaders() {
		return service.findAll();
	}


	@GetMapping("/users/{id}")
	public Reader retrieveUser(@PathVariable int id) {

		Reader user = service.findOne(id);
		System.out.println(user);
		if (user == null)
			throw new ReadernotFoundException("id-" + id);

		return user;
	}
	

	@GetMapping("/users/hateoas/{id}")
	public EntityModel<Reader> retrieveUser_hateoas(@PathVariable int id) {
		Reader reader = service.findOne(id);

		if (reader == null)
			throw new ReadernotFoundException("id-" + id);


		EntityModel<Reader> resource = EntityModel.of(reader);

		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllReaders());

		resource.add(linkTo.withRel("all-users"));

		
		return resource;
	}



	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Validated @RequestBody Reader user) {

		Reader saveduser = service.save(user);

		

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saveduser.getId())
				.toUri();
		
		

		return ResponseEntity.created(location).build();

	}

	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		Reader reader = service.deleteById(id);

		if (reader == null)
			throw new ReadernotFoundException("id-" + id);
	}

	
	
}
