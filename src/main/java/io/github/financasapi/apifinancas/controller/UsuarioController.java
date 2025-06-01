package io.github.financasapi.apifinancas.controller;

import io.github.financasapi.apifinancas.dto.UsuarioDTO;
import io.github.financasapi.apifinancas.dto.UsuarioResponseDTO;
import io.github.financasapi.apifinancas.dto.errors.ErrorResposta;
import io.github.financasapi.apifinancas.expections.OperacaoNaoPermitidaException;
import io.github.financasapi.apifinancas.expections.RegistroDuplicadoExpection;
import io.github.financasapi.apifinancas.model.Transacao;
import io.github.financasapi.apifinancas.model.Usuario;
import io.github.financasapi.apifinancas.service.RelatorioService;
import io.github.financasapi.apifinancas.service.TransacaoService;
import io.github.financasapi.apifinancas.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final TransacaoService transacaoService;
    private final RelatorioService relatorioService;

    public UsuarioController(UsuarioService service, TransacaoService transacaoService, RelatorioService relatorioService) {
        this.usuarioService = service;
        this.transacaoService = transacaoService;
        this.relatorioService = relatorioService;
    }


    @PostMapping
    public ResponseEntity<Object> salvarUsuario(@RequestBody @Valid UsuarioDTO usuario) {
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
    public ResponseEntity<Object> pesquisaUsuario(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);
        Optional<Usuario> user = usuarioService.buscarPorId(idAutor);
        if (user.isPresent()) {
            Usuario entity = user.get();
            UsuarioResponseDTO userDTO = new UsuarioResponseDTO(entity.getId(), entity.getNome(), entity.getEmail());
            return ResponseEntity.ok(userDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResposta.naoEncontrado("Usuário não encontrado pelo ID"));
    }

    @GetMapping
    public ResponseEntity<?> pesquisaDetalhadaUsuario(@RequestParam(value = "nome", required = false) String nome) {
        List<Usuario> lista = usuarioService.pesquisa(nome);
        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResposta.naoEncontrado("O usuário não foi encontrado em nossa base."));
        }
        List<UsuarioResponseDTO> listaDTO = lista.stream().map(usuario -> new UsuarioResponseDTO(usuario.getId(), usuario.getNome(),
                usuario.getEmail())).collect(Collectors.toList());
        return ResponseEntity.ok(listaDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> removerUsuario(@PathVariable("id") String id) {
        try {
            var idUsuario = UUID.fromString(id);
            Optional<Usuario> user = usuarioService.buscarPorId(idUsuario);
            if (user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResposta.naoEncontrado("O id não foi encontrado em nossa base"));
            }
            usuarioService.deletar(user.get().getId());
            return ResponseEntity.noContent().build();
        } catch (OperacaoNaoPermitidaException ex) {
            var errorResposta = ErrorResposta.respostaPadrao(ex.getMessage());
            return ResponseEntity.status(errorResposta.status()).body(errorResposta);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizarUsuario(@PathVariable("id") String id, @RequestBody @Valid UsuarioDTO usuario) {
        var idUsario = UUID.fromString(id);
        Optional<Usuario> user = usuarioService.buscarPorId(idUsario);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResposta.naoEncontrado("O usuário não existe."));
        }
        var entityUser = user.get();
        entityUser.setNome(usuario.nome());
        entityUser.setEmail(usuario.email());
        entityUser.setSenha(usuario.senha());

        usuarioService.salvar(entityUser);
        return ResponseEntity.ok().build();
    }
}
