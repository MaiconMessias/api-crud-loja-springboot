package br.com.crudloja.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {
    private Integer id;
    private String titulo;
    private String descricao;
    private Double preco;
    private byte[] foto;
    private MultipartFile fotoDto;
}
