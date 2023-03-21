package com.bank.client;

import com.bank.models.Balance;
import com.bank.models.BalanceCheckRequest;
import com.bank.models.BankServiceGrpc;
import com.bank.models.WithdrawRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankClientTest {

    private BankServiceGrpc.BankServiceBlockingStub blockingStub;
    @BeforeAll
    public void setup() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();
        blockingStub = BankServiceGrpc.newBlockingStub(channel);
    }

    @Test
    public void balanceTest() {

        Balance balance = blockingStub.getBalance(
                BalanceCheckRequest.newBuilder().setAccountNumber(7).build()
        );

        System.out.println(balance);
    }

    @Test
    public void withdrawTest() {
        WithdrawRequest request = WithdrawRequest.newBuilder().setAccountNumber(7).setAmount(40).build();
        blockingStub.withdraw(request).forEachRemaining(
                money -> System.out.println("Received : " + money.getValue())
        );
    }


}
