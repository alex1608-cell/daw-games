package com.daw.persistence;

import org.springframework.data.repository.CrudRepository;

import com.daw.entities.Juego;

public interface TareaCrudRepository extends CrudRepository<Juego, Integer> {

}
