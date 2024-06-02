package com.Reader.Books.source;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.io.IOException;
import java.net.URI;
import java.nio.file.AccessDeniedException;
import java.util.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
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
	
	/*@PostMapping("/Readers/{id}/books")
	public ResponseEntity<Object> createPost(@PathVariable int id,@RequestBody Books book){
		Optional<Reader> readeroptional=readerrepository.findById(id);
		if(!readeroptional.isPresent())
			throw new ReadernotFoundException("id"+id);
		Reader reader=readeroptional.get();
		
		
		book.setReader(reader);
	    booksrepository.save(book);
	    URI location=ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(book.getId()).toUri();
	    return ResponseEntity.created(location).build();
	    
		
	}*/
	
	@PostMapping("/Readers/{id}/books")
	public ResponseEntity<Object> createPost(@PathVariable int id, @RequestParam("file") MultipartFile file, @RequestParam("description") String description){
	    Optional<Reader> readerOptional = readerrepository.findById(id);
	    if (!readerOptional.isPresent())
	        throw new ReadernotFoundException("id" + id);
	    Reader reader = readerOptional.get();

	    try {
	        byte[] fileData = file.getBytes();

	        Books book = new Books();
	        book.setFileData(fileData);
	        book.setDescription(description);
	        book.setReader(reader);

	        booksrepository.save(book);

	        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(book.getId()).toUri();
	        return ResponseEntity.created(location).build();
	    } catch (IOException ex) {
	        throw new RuntimeException("Failed to store file", ex);
	    }
	}

	@GetMapping("/Readers/{id}/books/{bookId}/download")
    public ResponseEntity<Resource> downloadPdf(@PathVariable int id, @PathVariable int bookId) throws BookNotFoundException, AccessDeniedException {
        // Find the book by ID
        Optional<Books> bookOptional = booksrepository.findById(bookId);
        if (!bookOptional.isPresent()) {
            throw new BookNotFoundException("Book not found with ID: " + bookId);
        }

        // Check if the book belongs to the specified reader
        Books book = bookOptional.get();
        if (!book.getReader().getId().equals(id)) {
            throw new AccessDeniedException("You do not have access to download this file");
        }

        // Set the file data and content type to PDF
        ByteArrayResource resource = new ByteArrayResource(book.getFileData());
        MediaType mediaType = MediaType.APPLICATION_PDF;

        // Return the PDF as a ResponseEntity
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + book.getDescription() + ".pdf")
                .contentType(mediaType)
                .body(resource);
    }

	
	
	
	}

