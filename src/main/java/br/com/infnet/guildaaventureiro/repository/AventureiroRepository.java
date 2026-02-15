package br.com.infnet.guildaaventureiro.repository;

import br.com.infnet.guildaaventureiro.dto.AventureiroFiltroRequest;
import br.com.infnet.guildaaventureiro.dto.PagedResponse;
import br.com.infnet.guildaaventureiro.model.Aventureiro;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public interface AventureiroRepository {
    List<Aventureiro> findAll();

    List<Aventureiro> findWithFilter(AventureiroFiltroRequest filtro);

    PagedResponse<Aventureiro> findWithFilter(
            AventureiroFiltroRequest filtro,
            int page,
            int size
    );

    List<Aventureiro> findWithFilter(
            AventureiroFiltroRequest filtro,
            Comparator<Aventureiro> comparator
    );

    PagedResponse<Aventureiro> findWithFilter(
            AventureiroFiltroRequest filtro,
            Comparator<Aventureiro> comparator,
            int page,
            int size
    );

    Optional<Aventureiro> findById(Long id);

    Aventureiro save(Aventureiro aventureiro);
}
