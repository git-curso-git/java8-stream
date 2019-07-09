package hello;

import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
	
	final int MIN_SIZE = 50;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunnerBeanWithVeryLargeNameWithLengthMaxThan50(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            
            int totGTsize = 0;
            for (String beanName : beanNames) {
            	int beanLength = beanName.length();

            	if(beanLength > MIN_SIZE) {
            		totGTsize ++;
            	}
                System.out.format(
                		"%6d %4s %4s %s\n", 
                		beanLength, 
                		(beanLength > MIN_SIZE?" SI ":"    "),
                		(beanLength > MIN_SIZE?String.valueOf(totGTsize):"    "),
                		beanName
                );
            }

            int totBeans = beanNames.length;
            System.out.format("Beans : %05d/%d\n", totBeans, totGTsize);
            
            System.out.println();
            System.out.println("Calcular usando stream");
            int totBeansStream = (int) Arrays.asList(beanNames)
            	.stream()
            	.filter((it) -> it.length() > MIN_SIZE)
            	.count();
            
            System.out.format(">%d hay : %d\n", MIN_SIZE, totBeansStream);
            
            System.out.println();
            System.out.println("Calcular usando stream, con reduction mediante refMethod");
            int totBeansStreamRM = (int) Arrays.asList(beanNames)
            	.parallelStream()
            	.mapToInt(String::length)
            	.filter((it) -> it > MIN_SIZE)
            	.count();
            
            System.out.format(">%d hay : %d", MIN_SIZE, totBeansStreamRM);
            
            System.out.println();
            System.out.println("Obtener los valores en un array");
            int[] listaSize = Arrays.asList(beanNames)
                	.parallelStream()
                	.mapToInt(String::length)
                	.filter((it) -> it > MIN_SIZE)
                	.sorted()
                	.toArray();
            
            System.out.println();
            System.out.println("Elementos : ");
            for(int s : listaSize) {
            	System.out.print(s + ", ");
            }
            System.out.println();
        };
    }

}
