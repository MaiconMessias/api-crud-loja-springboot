package br.com.crudloja.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {
    private Integer id;
    private String titulo;
    private String descricao;
    private Double preco;
    private byte[] foto;
}
