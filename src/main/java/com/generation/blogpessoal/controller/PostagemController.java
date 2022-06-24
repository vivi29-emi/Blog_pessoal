package com.generation.blogpessoal.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.Service.PostagemService;
import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;



@RestController
@RequestMapping("/postagens")    //<-- recebe a aquisição acinado dentro dos parentes.
@CrossOrigin(origins="*",allowedHeaders="*")     // <-- *aceitar aquisição de qualquer lugar, se quiser restringir colocar o link do endereço no lugar do * alloe... =cabeçalho para usuario receber o token, faz parte da segurança.
public class PostagemController 
{
   @Autowired 	// <-- injeção de dependencia = transferencia de responsabilidade de instancia para o spring e o JPA sendo ele responsável pela a criação.
   private PostagemRepository postagemRepository;    //<-- Objeto da interface postagemRepository.
   
   @Autowired
	private PostagemService postagemService;
   
   @GetMapping
   public ResponseEntity <List<Postagem>> getALL(){
	   return ResponseEntity.ok(postagemRepository.findAll());
	   //= select * from tb_...
   }
   
   
// função lambida = select * from tb_postagens where id=id
   @GetMapping("/{id}")      // <--Metodo lambida, filtra o id especifico, se acha o valor ele retorna caso não envia uma outra resposta de erro.
    public ResponseEntity <Postagem> geybyid(@PathVariable Long id){     // <-- Quando queremos passar um valor pela URI (URL),e recepcionamos o valor através do @PathVariable.
	return postagemRepository.findById(id) // <-- busca o id
			.map(resposta -> ResponseEntity.ok(resposta))    //<-- se achar ele vai imprimir a resposta.
			.orElse(ResponseEntity.notFound().build());     //<-- caso não encontre ele vai imprimir a resposta Not body return...
   }
   
   // buscar a data final e inicial
   @GetMapping("/data_inicial/{inicio}/data_final/{fim}")
	public ResponseEntity<List<Postagem>> getByDataIntervalo(@PathVariable String inicio, @PathVariable String fim){
		
		LocalDate data_start = LocalDate.parse(inicio, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate data_end = LocalDate.parse(fim, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		return ResponseEntity.ok(postagemRepository.findByDataBetween(data_start,data_end));
	}
   
   
   
// função lambida = select * from tb_postagens where titulo like %titulo%

  @GetMapping ("/titulo/{titulo}")//<-- Filtra os dados pelo  o  titulo
   public ResponseEntity <List<Postagem>> getByTitulo(@PathVariable String titulo){ // <-- buscando titulo
	   return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
	   
	 
   }
  
  
   @PostMapping     // <--Preparando a tabela para receber também no front validando os dados.

   public ResponseEntity <Postagem> PostPostagem(@Valid @RequestBody Postagem postagem)	   // <-- Adicionar a tabela os dados e validando
   {
	   return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));	// <-- Para criação utilizado o Status ..e para salvar no @RequestBody no banco de dados utiliza (postagemRepository.save(postagem)
   }
   
   
   //
   
   
   @PutMapping    // <--Atualizando os dados.
   public ResponseEntity <Postagem> PutPostagem(@Valid @RequestBody Postagem postagem)	   // <-- Adicionar a tabela os dados e validando
   {
	   return postagemRepository.findById(postagem.getId())
	   .map(resposta ->ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem)))
	   .orElse(ResponseEntity.notFound().build());
	   // Analisando  antes de atualizar se existe outro id, caso não irá retornar com o cod 204.
	   
   }
   
   
     // Desafio a fazer verificar se existe o id 4, devolver o status 204 NOT CONTENT
   @DeleteMapping("/{id}")      //<-- Deleta os dados.
   @ResponseStatus(HttpStatus.NO_CONTENT)       // <-- Traz o status sem conteúdo
	public void deletePostagem(@Valid@PathVariable Long id)
   {
		Optional <Postagem>postagem=postagemRepository.findById(id);
		if (postagem.isEmpty())      // <-- Analisa se é vazio
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);     // <-- caso não encontre o valor devolverá como not content cod 204.
			
			postagemRepository.deleteById(id);    // <-- Chama  o método  postagemRepository.
			  
		}
   
   
	@PutMapping("/curtir/{id}")
	public ResponseEntity<Postagem> curtirProdutoId (@PathVariable Long id){
		
		return postagemService.curtir(id)
			.map(resposta-> ResponseEntity.ok(resposta))
			.orElse(ResponseEntity.badRequest().build());
	
	}
   
		
		   

	}
   
   
   
	

