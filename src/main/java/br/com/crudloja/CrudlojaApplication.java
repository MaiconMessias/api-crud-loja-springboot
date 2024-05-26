package br.com.crudloja;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CrudlojaApplication  implements CommandLineRunner  {

	public static void main(String[] args) {
		SpringApplication.run(CrudlojaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		System.out.println("Fim");
	}

}
