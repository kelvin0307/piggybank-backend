package com.testing.piggybank;

import com.testing.piggybank.account.AccountResponse;
import com.testing.piggybank.model.Currency;
import com.testing.piggybank.transaction.CreateTransactionRequest;
import com.testing.piggybank.transaction.GetTransactionsResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.math.BigDecimal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PiggyBankApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void TestGetAccountById() {
		// Arrange
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("X-User-Id", "1");

		// Act
		ResponseEntity<AccountResponse> response = restTemplate
				.getForEntity("/api/v1/accounts/1", AccountResponse.class);

		// Assert
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		AccountResponse accountResponse = response.getBody();
		Assertions.assertNotNull(accountResponse);
	}

	@Test
	public void Test_createTransaction_responseOk(){
		CreateTransactionRequest createTransactionRequest = new CreateTransactionRequest();
		createTransactionRequest.setAmount(new BigDecimal(20));
		createTransactionRequest.setCurrency(Currency.EURO);
		createTransactionRequest.setReceiverAccountId(2L);
		createTransactionRequest.setSenderAccountId(1L);
		createTransactionRequest.setDescription("API Transaction");

		HttpEntity<CreateTransactionRequest> request = new HttpEntity<>(createTransactionRequest);
		ResponseEntity<HttpStatus> response = restTemplate.postForEntity("/api/v1/transactions", request, HttpStatus.class);

		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void GetTransactionDataAPI(){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("X-User-Id", "1");

		ResponseEntity<GetTransactionsResponse> response = restTemplate
				.getForEntity("/api/v1/transactions/1?limit=2", GetTransactionsResponse.class);

		Assertions.assertEquals(response.getBody().getTransactions().size(), 2);
	}

}
