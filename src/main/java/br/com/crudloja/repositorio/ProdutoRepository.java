package br.com.crudloja.repositorio;

import org.springframework.data.repository.CrudRepository;

import br.com.crudloja.model.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Integer> {

}
