package com.pokeapi.service.interfaces;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.pokeapi.Entity.Pokemon;


public interface IPokemonService {

	public Pokemon findPokemon(String id) throws JsonMappingException, JsonProcessingException;

	public List<Pokemon> findAll(String limite, String numPokemon) throws JsonMappingException, JsonProcessingException;


}
