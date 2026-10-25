package ma.ebank.app.ebankservice.service;

import ma.ebank.app.ebankservice.entities.BankAccount;
import ma.ebank.app.ebankservice.feign.CustomerRestClient;
import ma.ebank.app.ebankservice.model.Customer;
import ma.ebank.app.ebankservice.repository.BankAccountRepository;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
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
    @McpTool(description = "get all Bank account")
    public List<BankAccount> getBankAccounts() {
        return bankAccountRepository.findAll();
    }

    @Override
    @McpTool(description = "")
    public List<BankAccount> findByCustomerId(Long customerId) {
        return bankAccountRepository.findByCustomerId(customerId);
    }

    @Override
    @McpTool(description = "Get a Bank account by id ")
    public BankAccount getBankAccountById(@McpToolParam(description = "bank account id") String bankAccountId) {
          BankAccount bankAccount = bankAccountRepository.findById(bankAccountId)
                .orElseThrow(() -> new RuntimeException("Account not found!") );
           bankAccount.setCustomer(
                   customerRestClient.getCustomerById(bankAccount.getCustomerId()));
        return bankAccount;
    }

    @Override
    @McpTool(description = "save Bank Account")
    public BankAccount saveBankAccount(@McpToolParam(description = "bank account to save (balance , type , customer id )") BankAccount bankAccount) {
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
