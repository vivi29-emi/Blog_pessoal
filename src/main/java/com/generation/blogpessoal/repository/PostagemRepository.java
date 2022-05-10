package com.generation.blogpessoal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.generation.blogpessoal.model.Postagem;

@Repository // <-- traz os metodos jpaRepository ele vai filtrar todos os dados da tabela semelhante o select, encaminhando para a tabela postagem é a class e Long a chave primaria
public interface PostagemRepository extends JpaRepository <Postagem,Long> 
{
	public List <Postagem>findAllByTituloContainingIgnoreCase (@Param("titulo") String titulo);

	


}
