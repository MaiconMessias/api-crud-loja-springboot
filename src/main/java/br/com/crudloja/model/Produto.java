package br.com.crudloja.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Entity
@Data
@Builder
@NoArgsConstructor  // Gera o construtor vazio (exigido por frameworks como Hibernate/Jackson)
@AllArgsConstructor // Gera o construtor com todos os campos (exigido pelo @Builder)
@EqualsAndHashCode(callSuper = false) // Remove o aviso de atenção do @Data@Table(name = "produto")
public class Produto extends RepresentationModel<Produto> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer id;
    private String titulo;
    private String descricao;
    private Double preco;
    private byte[] foto;
}
