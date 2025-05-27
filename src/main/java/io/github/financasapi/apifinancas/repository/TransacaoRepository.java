package io.github.financasapi.apifinancas.repository;

import io.github.financasapi.apifinancas.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransacaoRepository extends JpaRepository<Transacao, UUID> {

    List<Transacao> findByTipo(String tipo);

}
