package pl.pjatk.s30899Bank;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountStorage accountStorage;

    public AccountService(AccountStorage accountStorage) {
        this.accountStorage = accountStorage;
    }

    public Account registerAccount(Account account) {
        if (account.getName() == null || account.getName().isEmpty()) {
            throw new IllegalArgumentException("Reporter cannot be null or empty.");
        }
        if (account.getSurname() == null || account.getSurname().isEmpty()) {
            throw new IllegalArgumentException("Assignee cannot be null or empty.");
        }
        if (account.getBalance() == 0.0) {
            throw new IllegalArgumentException("Status cannot be null.");
        }
        return accountStorage.addAccount(account);
    }

    public Optional<Account> getAccountById(int id) {
        Optional<Account> wantedAccount = accountStorage.getAccountById(id);
        if (wantedAccount.isPresent()) {
            throw new IllegalArgumentException("Payment cannot be null or empty.");
        }
        return wantedAccount;
    }

    public Optional<Account> transferOrder(int id, double deposit) {
        if (deposit == 0.0) {
            throw new IllegalArgumentException("Payment cannot be null or empty.");
        }
        Optional<Account> account = accountStorage.getAccountById(id);
        if (account.isEmpty()) {
            account.ifPresent(ac -> ac.setLastTransactionStatus(Status.DECLINED));
            throw new IllegalArgumentException("Account with ID " + id + " not found.");
        } else if (deposit < 0.0) {
            account.ifPresent(ac -> ac.setLastTransactionStatus(Status.DECLINED));
            throw new IllegalArgumentException("Amount cannot be negative.");
        } else if (deposit > account.get().getBalance()) {
            account.ifPresent(ac -> ac.setLastTransactionStatus(Status.DECLINED));
            throw new IllegalArgumentException("Amount cannot be greater than balance.");
        }
        return accountStorage.transferOrder(id, deposit);
    }

    public Optional<Account> paymentToTheAccount(int id, double payment) {
        if (payment == 0.0) {
            throw new IllegalArgumentException("Payment cannot be null or empty.");
        }
        Optional<Account> account = accountStorage.getAccountById(id);
        if (account.isEmpty()) {
            account.ifPresent(ac -> ac.setLastTransactionStatus(Status.DECLINED));
            throw new IllegalArgumentException("Account with ID " + id + " not found.");
        } else if (payment < 0.0) {
            account.ifPresent(ac -> ac.setLastTransactionStatus(Status.DECLINED));
            throw new IllegalArgumentException("Payment cannot be negative.");
        }
        return accountStorage.paymentToTheAccount(id, payment);
    }

    public void printAccountDetails(int id) {
        Optional<Account> account = accountStorage.getAccountById(id);
        account.ifPresentOrElse(
                a -> System.out.println("Account ID: " + a.getId() + ", Name: " + a.getName() + ", Surname: " + a.getSurname() + ", Balance: " + a.getBalance()),
                () -> System.out.println("Account with ID " + id + " not found.")
        );
    }
}
