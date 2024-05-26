package br.com.crudloja.dto;

import org.springframework.web.multipart.MultipartFile;

public record ProdutoRecordDto(String titulo, String descricao, Double preco, MultipartFile foto) {
}