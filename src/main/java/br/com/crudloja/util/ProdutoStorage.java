package br.com.crudloja.util;

import org.springframework.web.multipart.MultipartFile;

import br.com.crudloja.model.Produto;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@MappedSuperclass
public class ProdutoStorage extends Produto {
    MultipartFile arquivo;
}
