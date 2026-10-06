package com.delivery.fastorder.repository;

import com.delivery.fastorder.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método para buscar usuario por correo
    Optional<Usuario> findByCorreo(String correo);
}