package dev.imrob.carrental.repository;

import dev.imrob.carrental.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
