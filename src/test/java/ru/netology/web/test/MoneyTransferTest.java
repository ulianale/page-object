package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MoneyTransferTest {
    int amount;
    int balanceBefore1;
    int balanceBefore2;
    DashboardPage dashboardPage;

    @BeforeEach
    void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
        balanceBefore1 = dashboardPage.getCardBalance(dashboardPage.card1);
        balanceBefore2 = dashboardPage.getCardBalance(dashboardPage.card2);
    }

    @Order(1)
    @Test
    void shouldTransferMoneyToFirstCard() { // from 2 to 1 card
        amount = balanceBefore2 - 1;
        dashboardPage.chooseCardButton(dashboardPage.cardButton1)
                .successTransfer(DataHelper.getCardNumber2(), amount);
        int balanceAfter1 = dashboardPage.getCardBalance(dashboardPage.card1);
        int balanceAfter2 = dashboardPage.getCardBalance(dashboardPage.card2);
        Assertions.assertEquals(balanceBefore1 + amount, balanceAfter1);
        Assertions.assertEquals(balanceBefore2 - amount, balanceAfter2);
    }

    @Order(2)
    @Test
    void shouldTransferMoneyToSecondCard() { // from 1 to 2
        amount = balanceBefore1 - 1;
        dashboardPage.chooseCardButton(dashboardPage.cardButton2)
                .successTransfer(DataHelper.getCardNumber1(), amount);
        int balanceAfter1 = dashboardPage.getCardBalance(dashboardPage.card1);
        int balanceAfter2 = dashboardPage.getCardBalance(dashboardPage.card2);
        Assertions.assertEquals(balanceBefore1 - amount, balanceAfter1);
        Assertions.assertEquals(balanceBefore2 + amount, balanceAfter2);
    }

    @Order(3)
    @Test
    void shouldTransferAllMoneyToFirstCard() { // from 2 to 1 card
        amount = balanceBefore2;
        dashboardPage.chooseCardButton(dashboardPage.cardButton1)
                .successTransfer(DataHelper.getCardNumber2(), amount);
        int balanceAfter1 = dashboardPage.getCardBalance(dashboardPage.card1);
        int balanceAfter2 = dashboardPage.getCardBalance(dashboardPage.card2);
        Assertions.assertEquals(balanceBefore1 + amount, balanceAfter1);
        Assertions.assertEquals(balanceBefore2 - amount, balanceAfter2);
    }

    @Order(4)
    @Test
    void shouldTransferAllMoneyToSecondCard() { // from 1 to 2
        amount = balanceBefore1;
        dashboardPage.chooseCardButton(dashboardPage.cardButton2)
                .successTransfer(DataHelper.getCardNumber1(), amount);
        int balanceAfter1 = dashboardPage.getCardBalance(dashboardPage.card1);
        int balanceAfter2 = dashboardPage.getCardBalance(dashboardPage.card2);
        Assertions.assertEquals(balanceBefore1 - amount, balanceAfter1);
        Assertions.assertEquals(balanceBefore2 + amount, balanceAfter2);
    }

    @Order(5)
    @Test
    void shouldErrorTransferMoreThenAvailableToFirstCard() { // from 2 to 1 error
        amount = balanceBefore2 + 1;
        dashboardPage.chooseCardButton(dashboardPage.cardButton1)
                .errorTransfer(DataHelper.getCardNumber2(), amount);
    }

    @Order(6)
    @Test
    void shouldErrorTransferMoreThenAvailableToSecondCard() { // from 1 to 2 error
        amount = balanceBefore1 + 1;
        dashboardPage.chooseCardButton(dashboardPage.cardButton2)
                .errorTransfer(DataHelper.getCardNumber1(), amount);
    }
}

