package cn.edu.pku.zyq.csv2ont;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAsync
@EnableTransactionManagement
public class Csv2ontApplication {

    public static void main(String[] args) {
        SpringApplication.run(Csv2ontApplication.class, args);
    }

}
