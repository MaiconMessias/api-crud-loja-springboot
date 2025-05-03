package br.com.crudloja.service.impl;

import br.com.crudloja.dto.ProdutoDTO;
import br.com.crudloja.model.Produto;
import br.com.crudloja.repositorio.ProdutoRepository;
import br.com.crudloja.service.iservice.ProdutoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<Produto> findAll() {
        Iterable<Produto> produtos = produtoRepository.findAll();
        List<Produto> result = new ArrayList<Produto>();
        produtos.forEach(result::add);
        return result;
    }

    @Override
    public Produto findById(Integer id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        return produto.orElse(null);
    }

    @Override
    public Produto create(ProdutoDTO produtoDTO) {
        return produtoRepository.save(mapper.map(produtoDTO, Produto.class));
    }

    @Override
    public Produto update(ProdutoDTO produtoDTO) {
        return produtoRepository.save(mapper.map(produtoDTO, Produto.class));
    }

    @Override
    public void delete(Integer id) {
        produtoRepository.deleteById(id);
    }

    @Override
    public List<Produto> findAll(Integer pagina, Integer itens) {
        return produtoRepository.findAll(PageRequest.of( pagina, itens ));
    }
}
