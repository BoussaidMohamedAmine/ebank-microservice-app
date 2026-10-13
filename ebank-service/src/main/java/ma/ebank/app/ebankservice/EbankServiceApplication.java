package ma.ebank.app.ebankservice;

import ma.ebank.app.ebankservice.entities.BankAccount;
import ma.ebank.app.ebankservice.repository.BankAccountRepository;
import ma.ebank.app.ebankservice.service.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class EbankServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankServiceApplication.class, args);
    }


    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService) {
        return args -> {
            for (int i = 1; i <= 3; i++) {
                for (int j = 1; j <5; j++) {
                    bankAccountService.saveBankAccount(BankAccount.builder()
                                .type(Math.random()>0.5 ? "CURRENT-ACCOUNT" : "SAVING-ACCOUNT" )
                                .balance(1000+ Math.random()*60000)
                                .customerId((long) i)
                                .build());
                }
            }
        };
    }
}
