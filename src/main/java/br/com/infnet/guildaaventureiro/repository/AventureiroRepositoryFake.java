package br.com.infnet.guildaaventureiro.repository;

import br.com.infnet.guildaaventureiro.dto.AventureiroFiltroRequest;
import br.com.infnet.guildaaventureiro.dto.PagedResponse;
import br.com.infnet.guildaaventureiro.enums.Classe;
import br.com.infnet.guildaaventureiro.model.Aventureiro;
import br.com.infnet.guildaaventureiro.util.Paginator;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Stream;

@Repository
public class AventureiroRepositoryFake implements AventureiroRepository {
    private Long sequence = 0L;
    private final List<Aventureiro> banco = new ArrayList<>();

    public AventureiroRepositoryFake() {
        inicializar();
    }

    @Override
    public List<Aventureiro> findAll() {
        return List.copyOf(banco);
    }

    @Override
    public List<Aventureiro> findWithFilter(AventureiroFiltroRequest filtro) {
        return findWithFilter(filtro, null);
    }

    @Override
    public PagedResponse<Aventureiro> findWithFilter(
            AventureiroFiltroRequest filtro,
            int page,
            int size
    ) {
        List<Aventureiro> filtrado = findWithFilter(filtro);
        return Paginator.paginate(filtrado, page, size);
    }

    @Override
    public List<Aventureiro> findWithFilter(
            AventureiroFiltroRequest filtro,
            Comparator<Aventureiro> comparator
    ) {
        Stream<Aventureiro> stream = List.copyOf(banco).stream()
                .filter(a -> possuiFiltro(a.getClasse(), filtro.classe()))
                .filter(a -> possuiFiltro(a.getAtivo(), filtro.ativo()))
                .filter(a -> possuiFiltroNivelMinimo(a, filtro));

        if (comparator != null)
            stream = stream.sorted(comparator);

        return stream.toList();
    }

    @Override
    public PagedResponse<Aventureiro> findWithFilter(
            AventureiroFiltroRequest filtro,
            Comparator<Aventureiro> comparator,
            int page,
            int size
    ) {
        List<Aventureiro> filtrado = findWithFilter(filtro, comparator);
        return Paginator.paginate(filtrado, page, size);
    }

    @Override
    public Optional<Aventureiro> findById(Long id) {
        return banco.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst();
    }

    @Override
    public Aventureiro save(Aventureiro aventureiro) {
        if (aventureiro.getId() == null) {
            aventureiro.setId(++sequence);
            banco.add(aventureiro);
        }
        return aventureiro;
    }

    private void inicializar() {
        Random random = new Random();

        List<String> nomesPrefixos = Arrays.asList("Ragnar", "Elara", "Thrain", "Lyra",
                "Kael", "Cedric", "Morganna", "Valerius");
        List<String> nomesSufixos = Arrays.asList("o Bravo", "de Prata", "das Sombras",
                "o Sábio", "de Ferro", "Luz do Sol");
        List<String> classes = Arrays.stream(Classe.values()).map(Enum::name).toList();

        for (int i = 0; i < 100; i++) {
            String nome = nomesPrefixos.get(random.nextInt(nomesPrefixos.size()));
            String sufixo = nomesSufixos.get(random.nextInt(nomesSufixos.size()));
            String classe = classes.get(random.nextInt(classes.size()));
            int nivel = random.nextInt(100) + 1;

            Aventureiro aventureiro = new Aventureiro(
                    String.format("%s %s", nome, sufixo),
                    Classe.valueOf(classe),
                    nivel
            );
            aventureiro.setId(++sequence);
            banco.add(aventureiro);
        }
    }

    private <T> boolean possuiFiltro(T objeto, T filtro) {
        return filtro == null || objeto.equals(filtro);
    }

    private boolean possuiFiltroNivelMinimo(Aventureiro aventureiro, AventureiroFiltroRequest filtro) {
        return filtro.nivelMinimo() == null || aventureiro.getNivel() >= filtro.nivelMinimo();
    }
}
