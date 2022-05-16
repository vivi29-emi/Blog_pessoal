package com.generation.blogpessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.generation.blogpessoal.model.Usuario;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) // execute nossa classe e localize uma aporta livre para realizar o teste
@TestInstance(TestInstance.Lifecycle.PER_CLASS)// ciclo de vida por classe sendo afer all ou before all.
public class UsuarioRepositoryTest {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll //<-- esse método vai ser executado antes de rodar o teste
	void start(){ //<-- executa o teste do metodo da classe deleteall.
        
        usuarioRepository.deleteAll();
// <-- abaixo são as 5 informações que tenho dentro do método
		usuarioRepository.save(new Usuario(0L, "João da Silva", "joao@email.com.br", "13465278", 
                                           "https://i.imgur.com/FETvs2O.jpg"));
		
		usuarioRepository.save(new Usuario(0L, "Manuela da Silva", "manuela@email.com.br", "13465278", 
                                           "https://i.imgur.com/NtyGneo.jpg"));
		
		usuarioRepository.save(new Usuario(0L, "Adriana da Silva", "adriana@email.com.br", "13465278",
                                           "https://i.imgur.com/mB3VM2N.jpg"));

        usuarioRepository.save(new Usuario(0L, "Paulo Antunes", "paulo@email.com.br", "13465278", 
                                           "https://i.imgur.com/JR7kUFU.jpg"));

	}
	
	@Test
	@DisplayName("Retorna 1 usuario") //<-- retorna usuario
	public void deveRetornarUmUsuario() {

		Optional<Usuario> usuario = usuarioRepository.findByUsuario("joao@email.com.br");
		assertTrue(usuario.get().getUsuario().equals("joao@email.com.br")); //<--  é verdade que o usuario dentro do objeto usario é o fulano, ou seja foi encontrado se sim ele retorna verdadeiro.
	}

	@Test
	@DisplayName("Retorna 3 usuarios") 
	public void deveRetornarTresUsuarios() {

		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Silva");
		assertEquals(3, listaDeUsuarios.size()); // teste de tamanho da lista, a quantidade de conteuso
		assertTrue(listaDeUsuarios.get(0).getNome().equals("João da Silva"));   //<-- verifica quem é da primeira posição  se realmente é a fulano colocado na posição.
		assertTrue(listaDeUsuarios.get(1).getNome().equals("Manuela da Silva"));
		assertTrue(listaDeUsuarios.get(2).getNome().equals("Adriana da Silva"));
		
	}


}
