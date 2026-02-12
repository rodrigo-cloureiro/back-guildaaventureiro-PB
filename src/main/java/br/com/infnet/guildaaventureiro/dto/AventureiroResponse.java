package br.com.infnet.guildaaventureiro.dto;

import br.com.infnet.guildaaventureiro.enums.Classe;
import br.com.infnet.guildaaventureiro.model.Aventureiro;

import java.util.UUID;

public record AventureiroResponse(
        UUID id,
        String nome,
        Classe classe,
        int nivel,
        boolean ativo
) {
    public AventureiroResponse(Aventureiro aventureiro) {
        this(
                aventureiro.getId(),
                aventureiro.getNome(),
                aventureiro.getClasse(),
                aventureiro.getNivel(),
                aventureiro.getAtivo()
        );
    }
}
