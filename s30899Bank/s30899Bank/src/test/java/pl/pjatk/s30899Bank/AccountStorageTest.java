package pl.pjatk.s30899Bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("unit")
class AccountStorageTest {

    @InjectMocks
    private AccountStorage accountStorage;

    @Mock
    private Account account;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addAccount_ShouldAddAccountToStorage() {
        // Given
        when(account.getId()).thenReturn(1);
        when(account.getBalance()).thenReturn(100.0);

        // When
        Account addedAccount = accountStorage.addAccount(account);

        // Then
        assertNotNull(addedAccount);
        assertEquals(1, addedAccount.getId());
        assertEquals(100.0, addedAccount.getBalance());
        verify(account, times(1)).setId(1);
    }

    @Test
    void getAccountById_ShouldReturnAccount_WhenAccountExists() {
        // Given
        when(account.getId()).thenReturn(1);
        accountStorage.addAccount(account);

        // When
        Optional<Account> foundAccount = accountStorage.getAccountById(1);

        // Then
        assertTrue(foundAccount.isPresent());
        assertEquals(1, foundAccount.get().getId());
    }

    @Test
    void getAccountById_ShouldReturnEmptyOptional_WhenAccountDoesNotExist() {
        // When
        Optional<Account> foundAccount = accountStorage.getAccountById(999);

        // Then
        assertFalse(foundAccount.isPresent());
    }

}