package br.com.crudloja.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.crudloja.dto.ProdutoRecordDto;
import br.com.crudloja.model.Produto;
import br.com.crudloja.repositorio.ProdutoRepository;
import br.com.crudloja.util.StorageService;
import lombok.NonNull;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "*")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("/listaproduto")
    public  ResponseEntity<List<Produto>> getProdutos() {
        List<Produto> productList = (List<Produto>) produtoRepository.findAll();
        if (!productList.isEmpty()){
            for (Produto produto : productList) {
                Integer id = produto.getId();
                // Hateaos - cria o campo links com dados da requisição especificada
                produto.add(linkTo( methodOn(ProdutoController.class).getProduto(id) ).withSelfRel());
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @GetMapping("/produto/{id}")
        public  ResponseEntity<Object> getProduto(@PathVariable @NonNull Integer id) {
            Optional<Produto> produto = produtoRepository.findById(id);
            if (produto.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
            }
            return ResponseEntity.status(HttpStatus.OK).body(produto.get());
    }


/*    @GetMapping("/comments/{momentId}")
        public  ResponseEntity<List<Comment>> getCommentMomentId(@PathVariable @NonNull Integer momentId) {
        return ResponseEntity.ok(commentsRepository.findAllCommentsMoments(momentId));
    }

*/
    /*@CrossOrigin(origins = "*")
    @PostMapping(path = "/produto/salvar", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> gravarDados(@ModelAttribute @NonNull ProdutoStorage produtoStorage) {
        Produto produto = new Produto();
        produto.setTitulo(produtoStorage.getTitulo());
        produto.setDescricao(produtoStorage.getDescricao());
        produto.setPreco(produtoStorage.getPreco());
        if (produtoStorage.getArquivo() != null){
            try {
                produto.setFoto(produtoStorage.getArquivo().getBytes());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            // Caso não setada uma foto, seta a padrão
            StorageService storageService = new StorageService();
            try {
                produto.setFoto( storageService.gravaFotoPadrao() );
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        return ResponseEntity.ok(produtoRepository.save(produto));
    }*/

   // UTILIZAÇÃO DO TIPO RECORD JAVA
   @CrossOrigin(origins = "*")
   @PostMapping(path = "/produto/salvar", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
   public ResponseEntity<Produto> gravarDados(@ModelAttribute @NonNull ProdutoRecordDto produtoRecordDto) {
       var produto = new Produto();
       BeanUtils.copyProperties(produtoRecordDto, produto);
       if (produtoRecordDto.foto() != null){
           try {
               produto.setFoto(produtoRecordDto.foto().getBytes());
           } catch (IOException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           }
       } else {
           // Caso não setada uma foto, seta a padrão
           StorageService storageService = new StorageService();
           try {
               produto.setFoto( storageService.gravaFotoPadrao() );
           } catch (FileNotFoundException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           }
       }
       return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto));
       
   }

    // TODO ajustar código de atualização para utilizar RECORD
    /*@CrossOrigin(origins = "*")
    @PutMapping(path = "/produto/editar/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> alteraDados(@PathVariable @NonNull Integer id, @ModelAttribute @NonNull ProdutoStorage produtoStorage) {
        Produto produto = new Produto();
        produto.setId(id);
        produto.setTitulo(produtoStorage.getTitulo());
        produto.setDescricao(produtoStorage.getDescricao());
        produto.setPreco(produtoStorage.getPreco());*/
        /* Verifica se a imagem foi alterada e se não foi setada, caso não setada adiciona uma foto padrão.
           Caso alterada procura e seta a anterior.
        */ 
       /* if (produtoStorage.getArquivo() != null){
            try {
                produto.setFoto(produtoStorage.getArquivo().getBytes());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            byte[] fotoAnterior = produtoRepository.findById(id).get().getFoto();
            if (fotoAnterior == null){
                // Se não existir foto anterior seta a padrão
                StorageService storageService = new StorageService();
                try {
                    produto.setFoto( storageService.gravaFotoPadrao() );
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                // Se existir foto anterior seta a mesma 
                produto.setFoto( fotoAnterior );
            }
        }
        
        return ResponseEntity.ok(produtoRepository.save(produto));
    }*/

    // UTILIZAÇÃO DO TIPO RECORD JAVA
    @CrossOrigin(origins = "*")
    @PutMapping(path = "/produto/editar/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Produto> alteraDados(@PathVariable @NonNull Integer id, @ModelAttribute @NonNull ProdutoRecordDto produtoRecordDto) {
        var produto = new Produto();
        BeanUtils.copyProperties(produtoRecordDto, produto);
        // informa o id a ser alterado
        produto.setId(id);

        // seta a imagem escolhida
        if (produtoRecordDto.foto() != null){
            try {
                produto.setFoto(produtoRecordDto.foto().getBytes());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        // caso não tenha sido setada uma foto
        } else {
            var produtoAnterior = produtoRepository.findById(id);
            // seta uma imagem padrão caso não exista foto cadastrada
            if (produtoAnterior.isEmpty()) {
                // Caso não setada uma foto, seta a padrão
                StorageService storageService = new StorageService();
                try {
                    produto.setFoto(storageService.gravaFotoPadrao());
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                // Caso já tenha sido cadastrada uma foto anterior
                produto.setFoto(produtoAnterior.get().getFoto());
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(produto));
    }

    @DeleteMapping("/produto/delete/{id}")
        public  ResponseEntity<?> deleteMoment(@PathVariable @NonNull Integer id) {
        var produtoTemp = produtoRepository.findById(id);
        if (produtoTemp.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        produtoRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Produto excluido com sucesso !");
    }

}
