package io.github.financasapi.apifinancas.controller;

import io.github.financasapi.apifinancas.dto.UsuarioDTO;
import io.github.financasapi.apifinancas.dto.errors.ErrorResposta;
import io.github.financasapi.apifinancas.expections.RegistroDuplicadoExpection;
import io.github.financasapi.apifinancas.model.Usuario;
import io.github.financasapi.apifinancas.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/financas")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService service) {
        this.usuarioService = service;
    }


    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody UsuarioDTO usuario) {
        try {
            var entidade = usuario.mapearUsuario();
            usuarioService.salvar(entidade);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(entidade.getId()).toUri();
            return ResponseEntity.created(location).build();

        } catch(RegistroDuplicadoExpection e){

            var errorDTO = ErrorResposta.conflito(e.getMessage());
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<UsuarioDTO> buscar(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);
        Optional<Usuario> user = usuarioService.buscarPorId(idAutor);
        if(user.isPresent()) {
            Usuario entity = user.get();
            UsuarioDTO userDTO = new UsuarioDTO(entity.getId(), entity.getNome(), entity.getEmail(), entity.getSenha());
            return ResponseEntity.ok(userDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> pesquisaDetalhada(@RequestParam(value = "nome", required = false) String nome) {
        List<Usuario> lista = usuarioService.pesquisa(nome);
        List<UsuarioDTO> listaDTO = lista.stream().map(usuario -> new UsuarioDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getSenha())).collect(Collectors.toList());
        return ResponseEntity.ok(listaDTO);
    }
}
