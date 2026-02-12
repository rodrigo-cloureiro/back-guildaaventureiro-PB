package br.com.infnet.guildaaventureiro.controller;

import br.com.infnet.guildaaventureiro.dto.AventureiroCreate;
import br.com.infnet.guildaaventureiro.dto.AventureiroResponse;
import br.com.infnet.guildaaventureiro.mapper.AventureiroMapper;
import br.com.infnet.guildaaventureiro.model.Aventureiro;
import br.com.infnet.guildaaventureiro.service.AventureiroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/aventureiro")
public class AventureiroController {
    private final AventureiroMapper aventureiroMapper;
    private final AventureiroService aventureiroService;

    // =====================
    // Registrar Aventureiro
    // =====================
    @PostMapping(value = "")
    public ResponseEntity<AventureiroResponse> registrarAventureiro(@RequestBody @Valid AventureiroCreate dto) {
        Aventureiro aventureiro = aventureiroMapper.toEntity(dto);
        Aventureiro salvo = aventureiroService.criar(aventureiro);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(aventureiroMapper.toResponse(salvo));
    }
}
