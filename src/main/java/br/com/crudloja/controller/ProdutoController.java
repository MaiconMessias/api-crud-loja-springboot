package br.com.crudloja.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import br.com.crudloja.dto.ProdutoDTO;
import br.com.crudloja.service.iservice.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.crudloja.repositorio.ProdutoRepository;
import br.com.crudloja.util.StorageService;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "*")
public class ProdutoController {

    public static final String ID = "/{id}";
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/listaproduto")
    public  ResponseEntity<List<ProdutoDTO>> getProdutos() {
        return ResponseEntity.ok().body(produtoService.findAll().stream().map(x -> mapper.map(x, ProdutoDTO.class))
                                                      .collect(Collectors.toList()));
    }

    @GetMapping("/produto/{id}")
        public  ResponseEntity<ProdutoDTO> getProduto(@PathVariable @NonNull Integer id) {
            return ResponseEntity.ok().body(mapper.map(produtoService.findById(id), ProdutoDTO.class));
    }

   // UTILIZAÇÃO DO TIPO RECORD JAVA
   @CrossOrigin(origins = "*")
   @PostMapping(path = "/produto/salvar", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
   public ResponseEntity<ProdutoDTO> gravarDados(@ModelAttribute @NonNull ProdutoDTO produtoDto) throws IOException {
       if (produtoDto.getFotoDto() == null){
           // Caso não setada uma foto, seta a padrão
           StorageService storageService = new StorageService();
           try {
               produtoDto.setFoto( storageService.gravaFotoPadrao() );
           } catch (FileNotFoundException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           }
       }
       // seta a foto no campo certo
       produtoDto.setFoto(produtoDto.getFotoDto().getBytes());
       URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(ID)
               .buildAndExpand(produtoService.create(produtoDto).getId()).toUri();
       return ResponseEntity.created(uri).build();
   }

    // UTILIZAÇÃO DO TIPO RECORD JAVA
    @CrossOrigin(origins = "*")
    @PutMapping(path = "/produto/editar/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ProdutoDTO> alteraDados(@PathVariable @NonNull Integer id,
                                                  @ModelAttribute @NonNull ProdutoDTO produtoDTO) {

        // seta a imagem escolhida
        if (produtoDTO.getFotoDto() != null){
            try {
                produtoDTO.setFoto(produtoDTO.getFotoDto().getBytes());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        // caso não tenha sido setada uma foto
        } else {
            var produtoAnterior = produtoService.findById(id);
            // seta uma imagem padrão caso não exista foto cadastrada
            if (produtoAnterior == null) {
                // Caso não setada uma foto, seta a padrão
                StorageService storageService = new StorageService();
                try {
                    produtoDTO.setFoto(storageService.gravaFotoPadrao());
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                // Caso já tenha sido cadastrada uma foto anterior
                produtoDTO.setFoto(produtoAnterior.getFoto());
            }
        }
        
        return ResponseEntity.ok().body(mapper.map(produtoService.update(produtoDTO), ProdutoDTO.class));
    }

    @DeleteMapping("/produto/delete/{id}")
        public  ResponseEntity<ProdutoDTO> deleteMoment(@PathVariable @NonNull Integer id) {

        // tratar Exceção com criação de classe de exceção
        if (produtoService.findById(id) == null)
            return null;
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Requisições de paginação
    @GetMapping("/listaprodutopaginacao/{pagina}/{itens}")
    public  ResponseEntity<List<ProdutoDTO>> getListaProdutoPaginacao(@PathVariable @NonNull Integer pagina,
                                                                      @PathVariable @NonNull Integer itens) {
        return ResponseEntity.ok().body(produtoService.findAll(pagina, itens )
                                       .stream().map(x -> mapper.map(x, ProdutoDTO.class))
                                       .collect(Collectors.toList()));
    }

}
