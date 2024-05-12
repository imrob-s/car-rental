package dev.imrob.carrental.service;

import dev.imrob.carrental.dto.ClienteDTO;
import dev.imrob.carrental.entity.Cliente;
import dev.imrob.carrental.exceptions.ClienteNotFoundException;
import dev.imrob.carrental.mapper.ClienteMapper;
import dev.imrob.carrental.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repository;
    @Autowired
    private ClienteMapper mapper;

    public ClienteDTO findById(Long id) {
        Cliente cliente = repository.findById(id).orElseThrow(
                () -> new ClienteNotFoundException("Cliente com id " + id + " não foi encontrado"));
        return mapper.toDto(cliente);
    }
    public ClienteDTO findByCpf(String cpf) {
        Optional<Cliente> clienteOptional = repository.findByCpf(cpf);
        if (clienteOptional.isEmpty()) {
            throw new ClienteNotFoundException("Cliente com cpf " + cpf + " não foi encontrado");
        }
        return mapper.toDto(clienteOptional.get());
    }

    public List<ClienteDTO> findAll() {
        List<Cliente> clientes = repository.findAll();
        return mapper.toDto(clientes);
    }

    public Long save(ClienteDTO clienteDTO) {
        Cliente cliente = mapper.toEntity(clienteDTO);
        cliente = repository.save(cliente);
        return cliente.getId();
    }

    public void update(ClienteDTO clienteDTO) {
        Optional<Cliente> clienteOptional = repository.findById(clienteDTO.id());
        if (clienteOptional.isEmpty()) {
            throw new ClienteNotFoundException("Cliente com id " + clienteDTO.id() + " não foi encontrado");
        }
        Cliente cliente = mapper.toEntity(clienteDTO);
        repository.save(cliente);
    }

    public void delete(Long id) {
        Cliente cliente = repository.findById(id).orElseThrow(
                () -> new ClienteNotFoundException("Cliente com id "+ id +" não foi encontrado"));
        repository.delete(cliente);
    }
}
