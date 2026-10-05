package ma.ebank.app.ebankservice.controllers;


import ma.ebank.app.ebankservice.entities.BankAccount;
import ma.ebank.app.ebankservice.repository.BankAccountRepository;
import ma.ebank.app.ebankservice.service.BankAccountService;
import ma.ebank.app.ebankservice.service.BankAccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EbankRestController {

    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping("/accounts")
    public List<BankAccount> getBankAccounts() {
        return this.bankAccountService.getBankAccounts();
    }

    @GetMapping("/accounts/{id}")
    public BankAccount getBankAccountById(@PathVariable String id) {
        return this.bankAccountService.getBankAccountById(id);
    }

    @GetMapping("/accounts/customer/{id}")
    public List<BankAccount> getBankAccountByCustomerId(@PathVariable Long customerId) {
        return this.bankAccountService.findByCustomerId(customerId);
    }

    @PostMapping("/accounts")
    public BankAccount saveBankAccount(@RequestBody BankAccount bankAccount) {
        return this.bankAccountService.saveBankAccount(bankAccount);
    }

}
