package com.Reader.Books.source;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


import org.springframework.stereotype.Service;

@Service
public class ReaderDaoservice {

	private static List<Reader> readers = new ArrayList<>();

	private static int readersCount = 3;

	static {
		readers.add(new Reader(1, "Adam", new Date()));
		readers.add(new Reader(2, "Eve", new Date()));
		readers.add(new Reader(3, "Jack", new Date()));
	}

	public List<Reader> findAll() {
		return readers;
	}

	public Reader save(Reader reader) {
		if (reader.getId() == null) {
			reader.setId(++readersCount);
		}
		readers.add(reader);
		return reader;
	}

	public Reader findOne(int id) {
		for (Reader reader : readers) {
			if (reader.getId() == id) {
				return reader;
			}
		}
		return null;
	}

	public Reader deleteById(int id) {
		Iterator<Reader> iterator = readers.iterator();
		while (iterator.hasNext()) {
			Reader reader = iterator.next();
			if (reader.getId() == id) {
				iterator.remove();
				return reader;
			}
		}
		return null;
	}

}
