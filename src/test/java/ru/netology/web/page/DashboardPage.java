package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    public SelenideElement card1 = $$(".list__item div").first();
    public SelenideElement card2 = $$(".list__item div").last();
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public SelenideElement cardButton1 = $$("[data-test-id='action-deposit']").first();
    public SelenideElement cardButton2 = $$("[data-test-id='action-deposit']").last();

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getCardBalance(SelenideElement card) {
        val text = card.text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public MoneyTransferPage chooseCardButton(SelenideElement cardButton) {
        cardButton.click();
        return new MoneyTransferPage();
    }
}