package com.generation.blogpessoal.model;


import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity // criando tb
@Table(name="tb_postagens") // tb_postagem
public class Postagem 

{
	
	@Id // primary key (id)
	@GeneratedValue(strategy= GenerationType.IDENTITY)// auto_increment
	private Long id  ; //L é uma classe recebe alguns metodos que te da mais recursos.
	
	@NotBlank(message="O atributo é obrigatório!") // não permite espaço em branco e nem nulo.
	@Size(min=5, max=100,message="O atributo título deve conter 5  e no máximo 100 carácteres")
	private String titulo ;
	
	@NotNull(message="O atributo é obrigatório!") 
	@Size(max=1000,message="O atributo texto deve conter 10  e no máximo 1000 carácteres")
	private String texto ;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	@UpdateTimestamp// pega a data e a hora da ultima atualização com o servidor e atualiza o create atualiza de acordo com o dia que foi criado a postagem.
	private LocalDate data;
	
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Tema tema;
	
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	public Long getId() 
	{
		return id;
	}

	public void setId(Long id) 
	{
		this.id = id;
	}

	public String getTitulo() 
	{
		return titulo;
	}

	public void setTitulo(String titulo) 
	{
		this.titulo = titulo;
	}

	public String getTexto() 
	{
		return texto;
	}

	public void setTexto(String texto)
	{
		this.texto = texto;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}
	

	
	

}
