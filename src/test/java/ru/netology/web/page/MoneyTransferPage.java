package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MoneyTransferPage {
    private SelenideElement amountField = $$("[data-test-id='amount'] input").filter(Condition.visible).first();
    private SelenideElement cardNumberField = $("[data-test-id='from'] .input__control");
    private SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private SelenideElement error = $("[data-test-id = error-notification]");

    public void transferMoney(DataHelper.CardInfo info, int amount) {
        amountField.setValue(String.valueOf(amount));
        cardNumberField.setValue(info.getCardNumber());
        transferButton.click();
    }

    public void successTransfer(DataHelper.CardInfo info, int amount) {
        transferMoney(info, amount);
        error.shouldNotBe(Condition.visible);
    }

    public void errorTransfer(DataHelper.CardInfo info, int amount) {
        transferMoney(info, amount);
        error.shouldBe(Condition.visible);
    }

}
