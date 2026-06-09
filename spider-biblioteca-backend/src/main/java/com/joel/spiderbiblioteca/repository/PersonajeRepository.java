package com.joel.spiderbiblioteca.repository;

import com.joel.spiderbiblioteca.model.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonajeRepository extends JpaRepository<Personaje, Long> {

    List<Personaje> findByNombreContainingIgnoreCase(String nombre);

    List<Personaje> findByTipoIgnoreCase(String tipo);
}
