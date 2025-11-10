/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestao.back.service;

import com.gestao.back.dto.UsuarioRequestDTO;
import com.gestao.back.dto.UsuarioResponseDTO;
import com.gestao.back.model.entities.Usuario;
import com.gestao.back.model.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.gestao.back.dto.LoginRequestDTO;


/**
 *
 * @author Kayqu
 */
@Service
public class UsuarioServiceImpl {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        return new UsuarioResponseDTO(usuario);
    }
    
    @Transactional(readOnly = true)
    public UsuarioResponseDTO login(LoginRequestDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado para o e-mail: " + dto.getEmail()));

        if (!usuario.getAtivo()) {
            throw new RuntimeException("Usuário está inativo"); 
        }
        
        if (!dto.getSenha().equals(usuario.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }
        return new UsuarioResponseDTO(usuario);
    }

    @Transactional
    public UsuarioResponseDTO criarUsuario(UsuarioRequestDTO dto) {
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado"); 
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNomeCompleto(dto.getNomeCompleto());
        novoUsuario.setEmail(dto.getEmail());
        novoUsuario.setPerfil(dto.getPerfil());
        novoUsuario.setAtivo(dto.getAtivo());
        
        novoUsuario.setSenha(dto.getSenha());

        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);
        return new UsuarioResponseDTO(usuarioSalvo);
    }
    
    @Transactional
    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioRequestDTO dto) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        usuarioRepository.findByEmail(dto.getEmail()).ifPresent(usuarioComEmail -> {
            if (!usuarioComEmail.getId().equals(id)) {
                throw new IllegalArgumentException("E-mail já cadastrado para outro usuário");
            }
        });

        usuarioExistente.setNomeCompleto(dto.getNomeCompleto());
        usuarioExistente.setEmail(dto.getEmail());
        usuarioExistente.setPerfil(dto.getPerfil());
        usuarioExistente.setAtivo(dto.getAtivo());

        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            usuarioExistente.setSenha(dto.getSenha());
        }

        Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);
        return new UsuarioResponseDTO(usuarioAtualizado);
    }

    @Transactional
    public void deletarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}