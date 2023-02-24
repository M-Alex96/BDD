package ru.netology.web.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.web.data.DataHelper.*;

class MoneyTransferTest {

    @Test
    void shouldTransferMoneyBetweenCards() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var Card1 = getFirstCardNumber();
        var Card2 = getSecondCardNumber();
        var Card1Balance = dashboardPage.getCardBalance(Card1);
        var Card2Balance = dashboardPage.getCardBalance(Card2);
        var amountToTransfer = ValidAmount(Card1Balance);
        var expectedCard1Balance = Card1Balance - amountToTransfer;
        var expectedCard2Balance = Card2Balance + amountToTransfer;
        var CardTransactionPage = dashboardPage.CardTransfer(Card2);
        dashboardPage = CardTransactionPage.validTransaction(String.valueOf(amountToTransfer), Card1);
        var actualCard1Balance = dashboardPage.getCardBalance(Card1);
        var actualCard2Balance = dashboardPage.getCardBalance(Card2);
        Assertions.assertEquals(expectedCard1Balance, actualCard1Balance);
        Assertions.assertEquals(expectedCard2Balance, actualCard2Balance);
    }

    @Test
    void shouldFailTransactionIfTransferAmountIsOverTheActualBalance() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var Card1 = getFirstCardNumber();
        var Card2 = getSecondCardNumber();
        var Card1Balance = dashboardPage.getCardBalance(Card1);
        var Card2Balance = dashboardPage.getCardBalance(Card2);
        var amountToTransfer = InvalidAmount(Card2Balance);
        var transactionPage = dashboardPage.CardTransfer(Card1);
        transactionPage.transaction(String.valueOf(amountToTransfer), Card2);
        transactionPage.error("Ошибка! На балансе недостаточно денежных средств");
        var actualCard1Balance = dashboardPage.getCardBalance(Card1);
        var actualCard2Balance = dashboardPage.getCardBalance(Card2);
        Assertions.assertEquals(Card1Balance, actualCard1Balance);
        Assertions.assertEquals(Card2Balance, actualCard2Balance);
    }
}



