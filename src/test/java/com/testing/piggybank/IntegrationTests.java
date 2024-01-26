package com.testing.piggybank;

import com.testing.piggybank.account.AccountController;
import com.testing.piggybank.account.AccountRepository;
import com.testing.piggybank.account.AccountResponse;
import com.testing.piggybank.account.GetAccountsResponse;
import com.testing.piggybank.model.Account;
import com.testing.piggybank.transaction.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class IntegrationTests {
    @Autowired
    private AccountRepository accountRepository;

    @Spy
    AccountRepository spyAccountRepository;

    @BeforeEach
    void beforeEach(){
        List<Account> result = accountRepository.findAllByUserId(1);

        Account firstResult = result.get(0);

        firstResult.setBalance(new BigDecimal(1999));
        firstResult.setName("Rekening van Melvin");
    }

    @Test
    public void SetAccountBalance(){
        List<Account> result = accountRepository.findAllByUserId(1);

        Account firstResult = result.get(0);

        firstResult.setBalance(new BigDecimal(2000));

        Assertions.assertEquals(new BigDecimal(2000), firstResult.getBalance());
    }

    @Test
    public void SetAccountNames(){
        List<Account> result = accountRepository.findAllByUserId(1);

        Account firstResult = result.get(0);

        firstResult.setName("Extra Rekening van Melvin");

        Assertions.assertEquals("Extra Rekening van Melvin", firstResult.getName());
    }

    @Test
    public void FindAllAccountsByUserId() {
        long userId = 1L;

        List<Account> accounts = accountRepository.findAllByUserId(userId);

        Assertions.assertFalse(accounts.isEmpty());

        for (Account account : accounts) {
            Assertions.assertEquals(userId, account.getUserId());
        }
    }


}