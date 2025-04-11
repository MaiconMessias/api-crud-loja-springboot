package br.com.crudloja.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local")
public class LocalConfig {

    /*@Autowired
    private UserClassRepository userClassRepository;*/

   public String cadastraUsuarioTeste(){
        /*userClassRepository.deleteAll();
        UserClass userClass = new UserClass(null, "usuario 1", "123");
        UserClass userClass2 = new UserClass(null, "usuario 2", "123");
        userClassRepository.saveAll(List.of(userClass, userClass2));*/
        return "ok";
    }

}
