package com.example.demo.infrastructure.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.models.entities.Emprestimo;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, UUID> {

}
