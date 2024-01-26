package com.testing.piggybank;

import com.testing.piggybank.account.AccountRepository;
import com.testing.piggybank.account.AccountService;
import com.testing.piggybank.model.Account;
import com.testing.piggybank.model.Direction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
class AccountServiceTests {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void testGetAccountById() {
        // Arrange
        long accountId = 1L;
        Account account = new Account();
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        // Act
        Optional<Account> result = accountService.getAccount(accountId);

        // Assert
        Assertions.assertEquals(Optional.of(account), result);
    }

    @Test
    void testGetAccountsByUserId() {
        // Arrange
        long userId = 1L;
        List<Account> accounts = List.of(new Account(), new Account());
        when(accountRepository.findAllByUserId(userId)).thenReturn(accounts);

        // Act
        List<Account> result = accountService.getAccountsByUserId(userId);

        // Assert
        Assertions.assertEquals(accounts, result);
    }

    @Test
    void testUpdateBalance() {
        // Arrange
        long accountId = 1L;
        BigDecimal amount = new BigDecimal("100.00");
        Direction direction = Direction.CREDIT;

        Account account = new Account();
        account.setBalance(new BigDecimal("500.00"));

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(Mockito.any(Account.class))).thenReturn(account);

        // Act
        accountService.updateBalance(accountId, amount, direction);

        // Assert
        Assertions.assertEquals(new BigDecimal("400.00"), account.getBalance());
    }
}
