package dev.imrob.carrental.dto.mapper;

import dev.imrob.carrental.dto.UsuarioCreateDto;
import dev.imrob.carrental.dto.UsuarioDTO;
import dev.imrob.carrental.dto.UsuarioResponseDto;
import dev.imrob.carrental.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsuarioMapper {
    UsuarioMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(UsuarioMapper.class);

    UsuarioDTO toDto(Usuario usuario);
    UsuarioResponseDto toResponseDto(Usuario usuario);
    Usuario toEntity(UsuarioCreateDto usuarioCreateDto);
    List<UsuarioResponseDto> toDto(List<Usuario> usuarios);
}
