package br.com.crudloja.service.impl;

import br.com.crudloja.model.Produto;
import br.com.crudloja.repositorio.ProdutoRepository;
import br.com.crudloja.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    @Override
    public List<Produto> findAll() {
        Iterable<Produto> produtos = produtoRepository.findAll();
        List<Produto> result = new ArrayList<Produto>();
        produtos.forEach(result::add);
        return result;
    }
}
