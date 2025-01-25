package pl.pjatk.s30899Bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
public class S30899BankApplication {

	private final AccountService accountService;

	public S30899BankApplication(AccountService accountService) {
		this.accountService = accountService;
		startApplication();
	}

	public void	startApplication() {

		Account account1 = new Account("Jan", "Nowak", 1000.0);
		Account account2 = new Account("Adam", "Kowalski", 300.0);

		accountService.registerAccount(account1);
		accountService.registerAccount(account2);

		Optional<Account> retrievedAccount = accountService.getAccountById(1);
		retrievedAccount.ifPresent(account -> {
			accountService.transferOrder(1,500.0);
			System.out.println("A transfer order has been issued for the account with ID " + account.getId() +" the new balance is " + account.getBalance());
		});

		Optional<Account> updateAccount = accountService.paymentToTheAccount(1, 2000.00);;
		updateAccount.ifPresent(account -> {
			System.out.println("Payment status " +account.getLastTransactionStatus() + " new account balance " + account.getBalance());
		});

	}

	public static void main(String[] args) {
		SpringApplication.run(S30899BankApplication.class, args);
	}

}
