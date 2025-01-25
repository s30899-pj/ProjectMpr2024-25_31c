package pl.pjatk.s30899Bank;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("integration")
@SpringBootTest
class AccountServiceTestIT {
    @Autowired
    private AccountService accountService;

    @MockBean
    private AccountStorage accountStorage;

    @Test
    void registerAccount_ShouldAddAccount_WhenValidDataProvided() {
        // Given
        Account account = new Account("John", "Doe", 100.0);
        when(accountStorage.addAccount(account)).thenReturn(account);

        // When
        Account registeredAccount = accountService.registerAccount(account);

        // Then
        assertNotNull(registeredAccount);
        assertEquals("John", registeredAccount.getName());
        assertEquals("Doe", registeredAccount.getSurname());
        assertEquals(100.0, registeredAccount.getBalance());
        verify(accountStorage, times(1)).addAccount(account);
    }

    @Test
    void registerAccount_ShouldThrowException_WhenNameIsEmpty() {
        // Given
        Account account = new Account("", "Doe", 100.0);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.registerAccount(account);
        });
        assertEquals("Reporter cannot be null or empty.", exception.getMessage());
        verify(accountStorage, never()).addAccount(account);
    }

    @Test
    void getAccountById_ShouldReturnAccount_WhenAccountExists() {
        // Given
        Account account = new Account("John", "Doe", 100.0);
        when(accountStorage.getAccountById(1)).thenReturn(Optional.of(account));

        // When
        Optional<Account> foundAccount = accountService.getAccountById(1);

        // Then
        assertTrue(foundAccount.isPresent());
        assertEquals("John", foundAccount.get().getName());
        assertEquals("Doe", foundAccount.get().getSurname());
        assertEquals(100.0, foundAccount.get().getBalance());
        verify(accountStorage, times(1)).getAccountById(1);
    }

    @Test
    void getAccountById_ShouldThrowException_WhenAccountDoesNotExist() {
        // Given
        when(accountStorage.getAccountById(999)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.getAccountById(999);
        });
        assertEquals("Payment cannot be null or empty.", exception.getMessage());
        verify(accountStorage, times(1)).getAccountById(999);
    }

}