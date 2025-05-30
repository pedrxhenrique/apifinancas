package io.github.financasapi.apifinancas.controller;

import io.github.financasapi.apifinancas.dto.CategoriaDTO;
import io.github.financasapi.apifinancas.model.Categoria;
import io.github.financasapi.apifinancas.service.CategoriaService;
import jakarta.validation.Valid;
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
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CategoriaDTO categoria) {
        var entidade = categoria.mapearCategoria();
        categoriaService.salvar(entidade);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(entidade.getId()).toUri();
        return ResponseEntity.created(location).body(entidade);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> buscar(@PathVariable("id") String id) {
        var idCategoria = UUID.fromString(id);
        Optional<Categoria> user = categoriaService.buscarPorId(idCategoria);
        if (user.isPresent()) {
            Categoria entity = user.get();
            CategoriaDTO categoriaDTO = new CategoriaDTO(entity.getId(), entity.getNome(), entity.getDescricao(), entity.getId());
            return ResponseEntity.ok(categoriaDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> pesquisarDetalhada(@RequestParam(value = "nome", required = false) String nome) {
        List<Categoria> lista = categoriaService.pesquisa(nome);
        List<CategoriaDTO> listaDTO = lista.stream().map(categoria -> new CategoriaDTO(categoria.getId(), categoria.getNome(), categoria.getDescricao(), categoria.getId())).collect(Collectors.toList());
        return ResponseEntity.ok(listaDTO);
    }
}
