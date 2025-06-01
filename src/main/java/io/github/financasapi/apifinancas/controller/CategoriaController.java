package io.github.financasapi.apifinancas.controller;

import io.github.financasapi.apifinancas.dto.CategoriaDTO;
import io.github.financasapi.apifinancas.dto.CategoriaResponseDTO;
import io.github.financasapi.apifinancas.dto.errors.ErrorResposta;
import io.github.financasapi.apifinancas.exceptions.OperacaoNaoPermitidaException;
import io.github.financasapi.apifinancas.model.Categoria;
import io.github.financasapi.apifinancas.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> salvarCategoria(@RequestBody @Valid CategoriaDTO categoria) {
        Categoria entidade = categoriaService.salvar(categoria.mapearCategoria());
        return ResponseEntity.ok(CategoriaResponseDTO.mapearResponseCategoria(entidade));
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> pesquisarCategoria(@PathVariable("id") String id) {
        var idCategoria = UUID.fromString(id);
        Optional<Categoria> user = categoriaService.buscarPorId(idCategoria);
        if (user.isPresent()) {
            Categoria entity = user.get();
            CategoriaResponseDTO categoriaDTO = new CategoriaResponseDTO(entity.getId(), entity.getNome(), entity.getDescricao(), entity.getUsuario().getId(), entity.getUsuario().getNome(), entity.getUsuario().getEmail());
            return ResponseEntity.ok(categoriaDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResposta.naoEncontrado("A categoria informada não foi encontrada."));
    }

    @GetMapping
    public ResponseEntity<?> pesquisaDetalhadaCategoria(@RequestParam(value = "nome", required = false) String nome) {
        List<Categoria> lista = categoriaService.pesquisa(nome);
        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResposta.naoEncontrado("A transação não foi encontrada em nossa base."));
        }
        List<CategoriaResponseDTO> listaDTO = lista.stream().map(categoria -> new CategoriaResponseDTO(categoria.getId(), categoria.getNome(), categoria.getDescricao(), categoria.getUsuario().getId(), categoria.getUsuario().getNome(), categoria.getUsuario().getEmail())).collect(Collectors.toList());
        return ResponseEntity.ok(listaDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizarCategoria(@PathVariable("id") String id, @RequestBody @Valid CategoriaDTO categoria) {
        var idCategoria = UUID.fromString(id);
        Optional<Categoria> categoriaOptional = categoriaService.buscarPorId(idCategoria);
        if (categoriaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResposta.naoEncontrado("A categoria informada não existe."));
        }
        var entityCategoria = categoriaOptional.get();
        entityCategoria.setDescricao(categoria.descricao());
        entityCategoria.setNome(categoria.nome());

        categoriaService.atualizar(idCategoria, entityCategoria);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> removerCategoria(@PathVariable("id") String id) {
        try {
            var idCategoria = UUID.fromString(id);
            Optional<Categoria> categoriaOptional = categoriaService.buscarPorId(idCategoria);
            if (categoriaOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResposta.naoEncontrado("O id da categoria não existe"));
            }
            categoriaService.deletar(idCategoria);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResposta.conflito("Não é possível deletar a categoria porque ela está em uso por uma ou mais transações."));
        } catch (OperacaoNaoPermitidaException ex) {
            var errorResposta = ErrorResposta.respostaPadrao(ex.getMessage());
            return ResponseEntity.status(errorResposta.status()).body(errorResposta);
        }
    }
}
