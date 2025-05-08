package com.daw.repositories;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.entities.Juego;

public interface JuegoRepository extends ListCrudRepository<Juego, Integer> {

}
