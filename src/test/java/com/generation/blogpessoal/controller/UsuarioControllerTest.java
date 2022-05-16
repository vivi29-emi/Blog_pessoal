package com.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.blogpessoal.Service.UsuarioService;
import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.model.UsuarioLogin;
import com.generation.blogpessoal.repository.UsuarioRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //<--Forçamos para repeitar as ordem alfabética
public class UsuarioControllerTest {
	
	
	@Autowired
	private TestRestTemplate testRestTemplate; //<--Cria uma requisição, faz o papel do insomnia, despachando para a classe controladora.pARA CNSMIR A CLASSE DE TERCEIRO USAR A CLASSE DE TAMPLADE

	@Autowired
	private UsuarioService usuarioService;

    @Autowired
	private UsuarioRepository usuarioRepository;
    
    @BeforeAll 
	void start(){ // <-- processo para que o banco de dados seja gerado.

		usuarioRepository.deleteAll(); 	// Apaga todos os registros do banco de dados antes de iniciar os testes
		 
	}
    
    @Test
	@Order(1) // primeiro teste a ser executado
	@DisplayName("Cadastrar Um Usuário")
	public void deveCriarUmUsuario() {
    	// Cria um objeto da Classe Usuario e insere dentro de um Objeto da Classe HttpEntity , semelhante a porta httplocalHost/8080
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(new Usuario(0L, 
			"Paulo Antunes", "paulo_antunes@email.com.br", "13465278", "https://i.imgur.com/JR7kUFU.jpg"));
		
		/**
		 * Cria um Objeto da Classe ResponseEntity (corpoResposta), que receberá a Resposta da Requisição que será 
		 * enviada pelo Objeto da Classe TestRestTemplate.
		 * 
		 * Na requisição HTTP será enviada a URL do recurso (/usuarios/cadastrar), o verbo (POST), a entidade
		 * HTTP criada acima (corpoRequisicao) e a Classe de retornos da Resposta (Usuario).
		 */

		ResponseEntity<Usuario> resposta = testRestTemplate
        .exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class); //exchange metodo responsavel para enviar as requisiçoes

		assertEquals(HttpStatus.CREATED, resposta.getStatusCode()); // se minha requisão for 201 ele vai trazer o retorno como verdadeiro 
		 /*Verifica se o Atributo Nome do Objeto da Classe Usuario retornado no Corpo da Requisição 
		  é igual ao Atributo Nome do Objeto da Classe Usuario Retornado no Corpo da Resposta
		  Se for verdadeiro, o teste passa, senão o teste falha*/
		 
		assertEquals(requisicao.getBody().getNome(), resposta.getBody().getNome());
		assertEquals(requisicao.getBody().getUsuario(), resposta.getBody().getUsuario());
	}
    @Test
	@Order(2)
	@DisplayName("Não deve permitir duplicação do Usuário")
	public void naoDeveDuplicarUsuario() {

		usuarioService.cadastrarUsuario(new Usuario(0L, 
			"Maria da Silva", "maria_silva@email.com.br", "13465278", "https://i.imgur.com/T12NIp9.jpg"));

		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(new Usuario(0L, 
			"Maria da Silva", "maria_silva@email.com.br", "13465278", "https://i.imgur.com/T12NIp9.jpg"));

		ResponseEntity<Usuario> resposta = testRestTemplate
			.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);

		assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
	}
    
    @Test
	@Order(3)
	@DisplayName("Alterar um Usuário")
	public void deveAtualizarUmUsuario() {

		Optional<Usuario> usuarioCreate = usuarioService.cadastrarUsuario(new Usuario(0L,  // recebe o resultado da classe service e cadastrar usuario, para saber o id do usuario criar um outro
			"Juliana Andrews", "juliana_andrews@email.com.br", 
			"juliana123", "https://i.imgur.com/yDRVeK7.jpg"));

		Usuario usuarioUpdate = new Usuario(usuarioCreate.get().getId(), 
			"Juliana Andrews Ramos", "juliana_ramos@email.com.br", 
			"juliana123", "https://i.imgur.com/yDRVeK7.jpg");
		
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(usuarioUpdate);

		ResponseEntity<Usuario> resposta = testRestTemplate
			.withBasicAuth("root", "root") // Usar o root pq não tenho o token.
			.exchange("/usuarios/atualizar", HttpMethod.PUT, requisicao, Usuario.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(usuarioUpdate.getNome(), resposta.getBody().getNome());
		assertEquals(usuarioUpdate.getUsuario(), resposta.getBody().getUsuario());
	}
    
    @Test
	@Order(4)
	@DisplayName("Listar todos os Usuários")
	public void deveMostrarTodosUsuarios() {

		usuarioService.cadastrarUsuario(new Usuario(0L, 
			"Sabrina Sanches", "sabrina_sanches@email.com.br", 
			"sabrina123", "https://i.imgur.com/5M2p5Wb.jpg"));
		
		usuarioService.cadastrarUsuario(new Usuario(0L, 
			"Ricardo Marques", "ricardo_marques@email.com.br", 
			"ricardo123", "https://i.imgur.com/Sk5SjWE.jpg"));

		ResponseEntity<String> resposta = testRestTemplate
			.withBasicAuth("root", "root")
			.exchange("/usuarios/all", HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
    
    // Teste logar Usuario
    @Test
   	@Order(5)
   	@DisplayName("Deve logar o usuario")
   	public void deveLogarUmUsuario() {
    	
    HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(new Usuario(0L, //Semelhante ao Json faz o filtro do cadastro
    			"Maria da Silva", "maria_silva@email.com.br", "13465278", "https://i.imgur.com/T12NIp9.jpg"));
    		
    	ResponseEntity<UsuarioLogin> resposta = testRestTemplate
    	.exchange("/usuarios/logar", HttpMethod.POST,requisicao, UsuarioLogin.class);
    	
    	assertEquals(HttpStatus.OK, resposta.getStatusCode());
    
    	
    }
    //Filtrar por ID
    @Test
   	@Order(5)
   	@DisplayName("Deve buscar o Usuario")
   	public void deveBuscarPorId() {
    	
    	Optional<Usuario> buscaUsuario = usuarioService.cadastrarUsuario(new Usuario(0L,  // recebe o resultado da classe service e cadastrar usuario, para saber o id do usuario criar um outro
    			"Juliana Andrews", "juliana_andrews@email.com.br", 
    			"juliana123", "https://i.imgur.com/yDRVeK7.jpg"));
    	
    	ResponseEntity<String> resposta = testRestTemplate
    			.withBasicAuth("root","root")
    	    	.exchange("/usuarios/"+buscaUsuario.get().getId(), HttpMethod.GET,null, String.class);
    	    	
    	    	assertEquals(HttpStatus.OK, resposta.getStatusCode());
    	}
}