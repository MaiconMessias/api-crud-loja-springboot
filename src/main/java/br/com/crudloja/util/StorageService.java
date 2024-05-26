package br.com.crudloja.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;


public class StorageService {

    private String nomeArquivo = "imgPadrao.jpg";

    // para registros sem imagem é gravado um arquivo padrão
    public byte[] gravaFotoPadrao() throws FileNotFoundException{
        Path currentDirectoryPath = FileSystems.getDefault().getPath("");
	    String currentDirectoryName = currentDirectoryPath.toAbsolutePath().toString();
        File d = new File(currentDirectoryName + "\\assets\\images\\" + nomeArquivo);

        // converte do tipo file para o tipo bytes array
        FileInputStream fis = new FileInputStream(d);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
        for (int readNum; (readNum = fis.read(buf)) != -1;) {
            bos.write(buf, 0, readNum); //no doubt here is 0
        }
        } catch (IOException ex) {
            System.out.println("Erro");
        }

        byte[] arquivo = bos.toByteArray();
        return arquivo;
    } 

}
