
package com.ejemplo;

public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario registrar(Usuario usuario) {

        if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email requerido");
        }

        if (repository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new IllegalStateException("Email ya registrado");
        }

        return repository.save(usuario);
    }
}
