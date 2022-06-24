package com.generation.blogpessoal.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;

@Service
public class PostagemService {
	
	@Autowired
	private PostagemRepository postagemRepository;
	
	//Método Curtir -> Soma 1 ao atributo curtir
	
	public Optional<Postagem> curtir(Long id) {
		
		if(postagemRepository.existsById(id)) {
			
			Postagem postagem = postagemRepository.findById(id).get();
			
			postagem.setCurtir(postagem.getCurtir() + 1);
			
			 return Optional.of(postagemRepository.save(postagem));
				
		}
		
		return Optional.empty();
	}

}
