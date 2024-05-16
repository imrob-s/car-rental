package dev.imrob.carrental.repository;

import dev.imrob.carrental.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}