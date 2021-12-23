package com.pokeapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokeapi.Entity.Movimiento;
import com.pokeapi.Entity.Pokemon;
import com.pokeapi.constants.Constantes;
import com.pokeapi.service.interfaces.IPokemonService;

@Service
public class PokemonServiceImpl implements IPokemonService {

	RestTemplate restTemplate = new RestTemplate();

	ResponseEntity<String> response;
	ObjectMapper mapper = new ObjectMapper();
	List<Pokemon> listaPokemones = new ArrayList<Pokemon>();

	@Override
	public Pokemon findPokemon(String id) throws JsonMappingException, JsonProcessingException {

		Pokemon pokemon = new Pokemon();

		String UrlPokemon = Constantes.URL + "pokemon/";
		try {
			response = restTemplate.getForEntity(UrlPokemon + id, String.class);
		} catch (Exception e) {
			e.getMessage();
			// TODO: handle exception
		}
		
		if (response== null) {
			pokemon.setNombre("el dato ingresado no es correcto");
		}else {
			pokemon = constriutPokemon(response);
		}
		
		
		
		return pokemon;
	}

	@Override
	public List<Pokemon> findAll(String limite, String numPokemon)
			throws JsonMappingException, JsonProcessingException {

		
		List<Pokemon> listaPokemones = new ArrayList<Pokemon>();

		Pokemon pokemon = new Pokemon();

		String UrlPokemon = Constantes.URL + "pokemon?limit=" + limite + "&offset=" + numPokemon;
		response = restTemplate.getForEntity(UrlPokemon, String.class);

		System.out.println(response);

		JsonNode pokemonesList = mapper.readTree(response.getBody());
		JsonNode pokemones = pokemonesList.path("results");
		
		for (int i = 0; i < pokemones.size(); i++) {
			String urlpokemon = pokemones.get(i).path("url").asText();

			pokemon = constriutPokemon(response = restTemplate.getForEntity(urlpokemon, String.class));

			listaPokemones.add(pokemon);
		}
	
		return listaPokemones;
	}

	public Pokemon constriutPokemon(ResponseEntity<String> responsePokemon)
			throws JsonMappingException, JsonProcessingException {

		List<Movimiento> listaMovientos = new ArrayList<Movimiento>();
		Pokemon pokemon = new Pokemon();

		JsonNode poke = mapper.readTree(responsePokemon.getBody());
		JsonNode imagenfrente = poke.path("sprites");
		JsonNode imagenUrl = imagenfrente.path("front_default");
		pokemon.setImagen(imagenUrl.asText());

		JsonNode nombre = poke.path("name");

		pokemon.setNombre(nombre.asText());

		JsonNode movimientos = poke.path("moves");

		int limit = 1;
		if (movimientos.size() > 1) {
			limit = 4;
		}

		for (int i = 0; i < limit; i++) {

			JsonNode movimiento = movimientos.get(i).path("move");

			response = restTemplate.getForEntity(movimiento.path("url").asText(), String.class);
			JsonNode movimientosResponse = mapper.readTree(response.getBody());
			JsonNode nombreMovimiento = movimientosResponse.path("names").get(5).path("name");

			Movimiento movi = new Movimiento();
			movi.setName(nombreMovimiento.asText());
			movi.setUrl(movimiento.path("url").asText());

			listaMovientos.add(movi);

		}

		JsonNode identificador = poke.path("id");
		pokemon.setMovimientos(listaMovientos);
		pokemon.setIdentificador(identificador.asInt());
		return pokemon;

	}

}
