package br.com.crudloja.service;

import br.com.crudloja.model.Produto;

import java.util.List;

public interface ProdutoService {
    List<Produto> findAll();
}
