package com.deinsoft.puntoventa.business.commons.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommonService<E,ID> {
	
	public Iterable<E> findAll();
	
	public Page<E> findAll(Pageable pageable);
	
	public Optional<E> findById(ID id);
	
	public E save(E entity);
	
	public void deleteById(ID id);

}
