package pl.pjatk.s30899Bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("unit")
class AccountServiceTest {
    @Mock
    private AccountStorage accountStorage;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

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
    void registerAccount_ShouldThrowException_WhenSurnameIsEmpty() {
        // Given
        Account account = new Account("John", "", 100.0);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.registerAccount(account);
        });
        assertEquals("Assignee cannot be null or empty.", exception.getMessage());
        verify(accountStorage, never()).addAccount(account);
    }


}