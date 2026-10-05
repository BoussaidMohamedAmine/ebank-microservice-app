package ma.ebank.app.ebankservice.service;

import ma.ebank.app.ebankservice.entities.BankAccount;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BankAccountService {
    public List<BankAccount> getBankAccounts();
    public List<BankAccount> findByCustomerId(Long customerId);
    public BankAccount getBankAccountById(String bankAccountId);
    public BankAccount saveBankAccount(BankAccount bankAccount);
}
