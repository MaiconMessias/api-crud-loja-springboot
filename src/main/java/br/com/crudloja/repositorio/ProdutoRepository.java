package br.com.crudloja.repositorio;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import br.com.crudloja.model.Produto;

import java.util.List;

public interface ProdutoRepository extends CrudRepository<Produto, Integer> {
    List<Produto> findAll(Pageable pageable);
}
