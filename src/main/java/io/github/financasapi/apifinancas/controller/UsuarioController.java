package io.github.financasapi.apifinancas.controller;

import io.github.financasapi.apifinancas.dto.UsuarioDTO;
import io.github.financasapi.apifinancas.dto.UsuarioResponseDTO;
import io.github.financasapi.apifinancas.dto.errors.ErrorResposta;
import io.github.financasapi.apifinancas.expections.RegistroDuplicadoExpection;
import io.github.financasapi.apifinancas.model.Usuario;
import io.github.financasapi.apifinancas.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/financas")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService service) {
        this.usuarioService = service;
    }


    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid UsuarioDTO usuario) {
        try {
            var entidade = usuario.mapearUsuario();
            usuarioService.salvar(entidade);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(entidade.getId()).toUri();
            return ResponseEntity.created(location).build();

        } catch (RegistroDuplicadoExpection e) {

            var errorDTO = ErrorResposta.conflito(e.getMessage());
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<UsuarioResponseDTO> pesquisa(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);
        Optional<Usuario> user = usuarioService.buscarPorId(idAutor);
        if (user.isPresent()) {
            Usuario entity = user.get();
            UsuarioResponseDTO userDTO = new UsuarioResponseDTO(entity.getId(), entity.getNome(), entity.getEmail());
            return ResponseEntity.ok(userDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<?> pesquisaDetalhada(@RequestParam(value = "nome", required = false) String nome) {
        List<Usuario> lista = usuarioService.pesquisa(nome);
        if (lista.isEmpty()) {
            Map<String, String> mensagem = new HashMap<>();
            mensagem.put("message", "Nenhum usuário encontrado com o nome informado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagem);
        }
        List<UsuarioResponseDTO> listaDTO = lista.stream().map(usuario -> new UsuarioResponseDTO(usuario.getId(), usuario.getNome(),
                usuario.getEmail())).collect(Collectors.toList());
        return ResponseEntity.ok(listaDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> remover(@PathVariable("id") String id) {
        var idUsuario = UUID.fromString(id);
        Optional<Usuario> user = usuarioService.buscarPorId(idUsuario);
        usuarioService.deletar(user.get().getId());
        return ResponseEntity.noContent().build();
    }
}
