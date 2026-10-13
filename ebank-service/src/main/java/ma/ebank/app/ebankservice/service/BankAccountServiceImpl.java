package ma.ebank.app.ebankservice.service;

import ma.ebank.app.ebankservice.entities.BankAccount;
import ma.ebank.app.ebankservice.feign.CustomerRestClient;
import ma.ebank.app.ebankservice.model.Customer;
import ma.ebank.app.ebankservice.repository.BankAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BankAccountServiceImpl implements BankAccountService {
    private BankAccountRepository bankAccountRepository;
    private CustomerRestClient customerRestClient;

     public BankAccountServiceImpl(BankAccountRepository bankAccountRepository , CustomerRestClient customerRestClient) {
         this.bankAccountRepository = bankAccountRepository;
         this.customerRestClient = customerRestClient;
    }


    @Override
    public List<BankAccount> getBankAccounts() {
        return bankAccountRepository.findAll();
    }

    @Override
    public List<BankAccount> findByCustomerId(Long customerId) {
        return bankAccountRepository.findByCustomerId(customerId);
    }

    @Override
    public BankAccount getBankAccountById(String bankAccountId) {
          BankAccount bankAccount = bankAccountRepository.findById(bankAccountId)
                .orElseThrow(() -> new RuntimeException("Account not found!") );
           bankAccount.setCustomer(
                   customerRestClient.getCustomerById(bankAccount.getCustomerId()));
        return bankAccount;
    }

    @Override
    public BankAccount saveBankAccount(BankAccount bankAccount) {
         try{
             Customer customer = customerRestClient.getCustomerById(bankAccount.getCustomerId());
             bankAccount.setId(UUID.randomUUID().toString());
             bankAccount.setCreatedAt(new Date());
             return this.bankAccountRepository.save(bankAccount);
         } catch (Exception e){
            throw new RuntimeException(e.getMessage());
         }

    }
}
