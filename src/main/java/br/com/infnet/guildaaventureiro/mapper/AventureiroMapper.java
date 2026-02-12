package br.com.infnet.guildaaventureiro.mapper;

import br.com.infnet.guildaaventureiro.dto.AventureiroCreate;
import br.com.infnet.guildaaventureiro.dto.AventureiroResponse;
import br.com.infnet.guildaaventureiro.model.Aventureiro;
import org.springframework.stereotype.Component;

@Component
public class AventureiroMapper {
    public Aventureiro toEntity(AventureiroCreate dto) {
        return new Aventureiro(
                dto.nome(),
                dto.classe(),
                dto.nivel()
        );
    }

    public AventureiroResponse toResponse(Aventureiro aventureiro) {
        return new AventureiroResponse(
                aventureiro.getId(),
                aventureiro.getNome(),
                aventureiro.getClasse(),
                aventureiro.getNivel(),
                aventureiro.getAtivo()
        );
    }
}
