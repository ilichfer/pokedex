package com.pokeapi.controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.pokeapi.Entity.Pokemon;
import com.pokeapi.service.PokemonServiceImpl;

@Controller
@RequestMapping
public class pokemonController {

	@Autowired
	PokemonServiceImpl pokemonService;
	
	RestTemplate restTemplate = new RestTemplate();
	ResponseEntity<String> response;
	List<Pokemon> listaPokemones;

	@GetMapping("/greeting")
	public String greeting(
			@RequestParam(name = "name", required = false, defaultValue = "buscar un pokemon") String name,
			Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}
	
	@GetMapping("/pokemones")
	public String pokemones(
			@RequestParam(name = "name", required = false, defaultValue = "buscar un pokemon") String name,
			Model model) {
		model.addAttribute("name", name);
		return "pokemones";
	}
	
	
	@GetMapping("/pokemon/{id}")
	public String listarPokemon(@PathVariable("id") String id, Model model) throws JsonMappingException, JsonProcessingException{

		Pokemon pokemon = pokemonService.findPokemon(id);	
		
		model.addAttribute("imagen", pokemon.getImagen());	
		model.addAttribute("nombre",pokemon.getNombre());
		model.addAttribute("movimientos", pokemon.getMovimientos());
		model.addAttribute("identificador", pokemon.getIdentificador());

		return "greeting";
	}

	
	@GetMapping("/pokemones/{limite}/{num}")
	public String listarPokemones(@PathVariable("limite")String limite,@PathVariable("num") String numPokemon, Model model) throws JsonMappingException, JsonProcessingException{
				
		listaPokemones = pokemonService.findAll(limite, numPokemon);	
		model.addAttribute("pokemones", listaPokemones);
				
		return "pokemones";
	}
	
	  @PostMapping("buscar")
		public String buscarId(@RequestParam("id") String id, HttpServletResponse response, Model model) throws JsonMappingException, JsonProcessingException {
			Pokemon pokemon = pokemonService.findPokemon(id);	
			
			model.addAttribute("imagen", pokemon.getImagen());	
			model.addAttribute("nombre",pokemon.getNombre());
			model.addAttribute("movimientos", pokemon.getMovimientos());
			model.addAttribute("identificador", pokemon.getIdentificador());

			return "greeting";
	  }
	  
	  @PostMapping("/pokemones")
	  public String listar(@RequestParam("limite") String limite,@RequestParam("numPokemon") String numPokemon, HttpServletResponse response, Model model) throws JsonMappingException, JsonProcessingException {					
			listaPokemones = pokemonService.findAll(limite, numPokemon);	
			model.addAttribute("pokemones", listaPokemones);
					
			return "pokemones";
		}

	
}
