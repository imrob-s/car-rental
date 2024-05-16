package dev.imrob.carrental.mapper;

import dev.imrob.carrental.dto.UsuarioDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsuarioMapper {
    UsuarioMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(UsuarioMapper.class);

    UsuarioDTO toDto(Usuario usuario);


}
