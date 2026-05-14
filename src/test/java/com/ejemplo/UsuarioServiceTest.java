
package com.ejemplo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioService service;

    @Test
    void registrar_usuarioValido_guardaYRetornaUsuario() {

        Usuario nuevoUsuario = new Usuario("ana@mail.com", "Ana");
        Usuario usuarioGuardado = new Usuario(1L, "ana@mail.com", "Ana");

        when(repository.findByEmail("ana@mail.com"))
                .thenReturn(Optional.empty());

        when(repository.save(nuevoUsuario))
                .thenReturn(usuarioGuardado);

        Usuario resultado = service.registrar(nuevoUsuario);

        assertNotNull(resultado.getId());
        assertEquals("ana@mail.com", resultado.getEmail());

        verify(repository, times(1)).save(nuevoUsuario);
        verify(repository, times(1)).findByEmail("ana@mail.com");
    }

    @Test
    void registrar_usuarioConEmailExistente_lanzaExcepcion() {

        Usuario usuarioExistente =
                new Usuario(1L, "ana@mail.com", "Ana");

        Usuario nuevoUsuario =
                new Usuario("ana@mail.com", "Ana");

        when(repository.findByEmail("ana@mail.com"))
                .thenReturn(Optional.of(usuarioExistente));

        assertThrows(IllegalStateException.class,
                () -> service.registrar(nuevoUsuario));

        verify(repository, never()).save(any());
    }

    @Test
    void registrar_usuarioSinEmail_lanzaExcepcion() {

        Usuario usuario =
                new Usuario("", "Ana");

        assertThrows(IllegalArgumentException.class,
                () -> service.registrar(usuario));

        verify(repository, never()).save(any());
    }
}
