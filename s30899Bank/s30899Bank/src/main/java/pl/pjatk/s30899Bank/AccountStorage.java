package pl.pjatk.s30899Bank;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AccountStorage {
    private final List<Account> accounts = new ArrayList<>();
    private int idCounter = 1;

    public Account addAccount(Account account) {
        account.setId(idCounter++);
        accounts.add(account);
        return account;
    }

    public Optional<Account> getAccountById(int id) {
        return accounts.stream().filter(account -> account.getId() == id).findFirst();
    }

    public Optional<Account> transferOrder(int id, double sum) {
        Optional<Account> account = getAccountById(id);
        account.ifPresent(ac -> {
            ac.setBalance(account.get().getBalance() - sum);
            ac.setLastTransactionStatus(Status.ACCEPTED);
        });
        return account;
    }

    public Optional<Account> paymentToTheAccount(int id, double sum) {
        Optional<Account> account = getAccountById(id);
        account.ifPresent(ac -> {
            ac.setBalance(account.get().getBalance() + sum);
            ac.setLastTransactionStatus(Status.ACCEPTED);
        });
        return account;
    }

}
