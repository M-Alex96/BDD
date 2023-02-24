package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class CardTransactionPage {

    private SelenideElement heading =$(withText("Пополнение карты"));
    private SelenideElement actionTransfer = $("[data-test-id='action-transfer']");
    private SelenideElement amount = $("[data-test-id='amount'] input");
    private SelenideElement from = $("[data-test-id='from'] input");
    private SelenideElement errorNotification = $("[data-test-id='error-notification']");

    public CardTransactionPage() {
        heading.shouldBe(visible);
    }

    public DashboardPage validTransaction(String transactionSum, DataHelper.CardNumber cardNumber) {
        transaction(transactionSum, cardNumber);
        return new DashboardPage();
    }

    public void transaction(String transactionSum, DataHelper.CardNumber cardNumber) {
        amount.setValue(transactionSum);
        from.setValue(cardNumber.getNumber());
        actionTransfer.click();
    }

    public void error (String errorTextMessage) {
        errorNotification.shouldHave(text(errorTextMessage), Duration.ofSeconds(15)).shouldBe(visible);
    }
}
