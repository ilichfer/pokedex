package com.pokeapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


import com.pokeapi.Entity.Movimiento;

@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface MovimientosMapper {
	MovimientosMapper INSTANCE = Mappers.getMapper(MovimientosMapper.class);

	
    @Mapping(target = "name", source = "nombre")
    @Mapping(target = "url", source = "url")

    Movimiento movimientoResponseToMovimientoDTO(String nombre, String url);
    
}
