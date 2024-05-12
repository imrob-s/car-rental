package dev.imrob.carrental.mapper;

import dev.imrob.carrental.dto.ClienteDTO;
import dev.imrob.carrental.entity.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClienteMapper {
    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    ClienteDTO toDto(Cliente cliente);

    Cliente toEntity(ClienteDTO clienteDTO);

    List<ClienteDTO> toDto(List<Cliente> entities);

    List<Cliente> toEntity(List<ClienteDTO> dtos);
}
