package br.com.crudloja.service;

import br.com.crudloja.dto.ProdutoDTO;
import br.com.crudloja.model.Produto;

import java.util.List;

public interface ProdutoService {
    List<Produto> findAll();
    Produto findById(Integer id);
    Produto create(ProdutoDTO produtoDTO);
    Produto update(ProdutoDTO produtoDTO);
    void delete(Integer id);
}
