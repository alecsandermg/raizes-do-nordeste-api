package com.raizesdonordeste.application.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.raizesdonordeste.api.dto.auth.AuthResponse;
import com.raizesdonordeste.api.dto.auth.LoginRequest;
import com.raizesdonordeste.api.dto.auth.RegisterRequest;
import com.raizesdonordeste.domain.entity.Usuario;
import com.raizesdonordeste.domain.enums.Role;
import com.raizesdonordeste.infrastructure.repository.UsuarioRepository;
import com.raizesdonordeste.infrastructure.security.JwtService;
import com.raizesdonordeste.application.service.AuditService;


@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();
    
    private final JwtService jwtService;
    private final AuditService auditService;

    public AuthService(UsuarioRepository usuarioRepository, JwtService JwtService, JwtService jwtService, AuditService auditService) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
        this.auditService = auditService;
    }
    
    public AuthResponse login(LoginRequest request) {

        Usuario usuario = usuarioRepository
                .findByEmail(request.getEmail())
                .orElseThrow();
                
        if (usuario == null) {

            auditService.registrar(
                    "LOGIN",
                    "USUARIO_NAO_ENCONTRADO",
                    request.getEmail());

            throw new RuntimeException(
                    "Usuário não encontrado");
                }
                        

        boolean senhaValida =
                passwordEncoder.matches(
                        request.getSenha(),
                        usuario.getSenhaHash());

        if (!senhaValida) {
        	
            auditService.registrar(
                    "LOGIN",
                    "FALHA",
                    usuario.getEmail());
            
            throw new RuntimeException("Senha inválida");
            
        }

        String token =
                jwtService.generateToken(
                        usuario.getEmail(),
                        usuario.getRole().name());
        
        auditService.registrar(
                "LOGIN",
                "SUCESSO",
                usuario.getEmail());

        return new AuthResponse(token);
    }

    public void register(RegisterRequest request) {

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("E-mail já cadastrado.");
        }

        Usuario usuario = Usuario.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .senhaHash(passwordEncoder.encode(request.getSenha()))
                .telefone(request.getTelefone())
                .consentimentoLGPD(
                	    request.getConsentimentoLGPD() != null
                	        ? request.getConsentimentoLGPD()
                	        : false
                	)
                .role(Role.CLIENTE)
                .ativo(true)
                .build();

        usuarioRepository.save(usuario);
    }
}