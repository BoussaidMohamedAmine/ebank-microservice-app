package ma.ebank.app.ebankservice.service;

import ma.ebank.app.ebankservice.entities.BankAccount;
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

     public BankAccountServiceImpl(BankAccountRepository bankAccountRepository) {
         this.bankAccountRepository = bankAccountRepository;
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
        return this.bankAccountRepository.findById(bankAccountId)
                .orElseThrow(() -> new RuntimeException("Account not found!") );
    }

    @Override
    public BankAccount saveBankAccount(BankAccount bankAccount) {
         bankAccount.setId(UUID.randomUUID().toString());
         bankAccount.setCreatedAt(new Date());
        return this.bankAccountRepository.save(bankAccount);
    }
}
