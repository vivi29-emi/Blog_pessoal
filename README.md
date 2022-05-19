# Back-end 

<h2>Processo da implementação das camadas para a segurança, testes e o deploy<\h2>

- [x] Criei a Camada Model, repository, controler, service das Tabela Temas,Postagens,Usuario.
- [x] Criei a camada Usuario Login.
- [x] Editado o Pom.xml, com as dependências para a segurança do projeto e para o deploy.
- [x] Criei a Camada de Security,BasicSecurityConfig,UserDetailsServiceImpl,UserDetailsServiceImpl.
- [x] Crie as camadas controller e repository teste JUnit.
- [x] Configurado os application.properties e adicionado as applications Dev.properties e prod.properties na pasta src/main/resources para o deploy no heroku.
- [x] Criei a camada swaggerConfiguration para o deploy no heroku. 
- [x] Executei os testes no Insomnia tanto em Dev como em Prod.             

 
 


<br>
<h3 align="center">
DER: Modelo de Entidade-Relacionamento nessa Task:
  
```mermaid
classDiagram
class Tema {
  - id : Long
  - nome : String
  - descricao : String 
  - postagem : List ~Postagem~
  + getAll()
  + getById(Long id)
  + getByDescricao(String descricao)
  + postTema(Tema tema)
  + putTema(Tema tema)
  + deleteTema(Long id)
}
class Postagem {
  - id : Long
  - titulo : String
  - texto: String
  - midia: String
  - data: LocalDate
  - tema : Tema
  - usuario : Usuario
  + getAll()
  + getById(Long id)
  + getByTitulo(String titulo)
  + getByDataIntervalo (String inicio, String fim)
  + postPostagem(Postagem postagem)
  + putPostagem(Postagem postagem)
  + deleteTema(Long id)
}
class Usuario {
  - id : Long
  - nome : String
  - usuario : String
  - senha : String
  - foto : String
  - postagem : List ~Postagem~
  + getAll()
  + getById(Long id)
  + autenticarUsuario(UsuarioLogin usuarioLogin)
  + cadastrarUsuario(Usuario usuario)
  + atualizarUsuario(Usuario usuario)
}
class UsuarioLogin{
  - id : Long
  - nome : String
  - usuario : String
  - senha : String
  - foto : String
  - token : String
}
Tema --> Postagem
Usuario --> Postagem
```  
