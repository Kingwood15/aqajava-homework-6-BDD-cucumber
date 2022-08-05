package ru.netology.domain.Test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.domain.Data.DataHelper;
import ru.netology.domain.Page.DashboardPage;
import ru.netology.domain.Page.LoginPage;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class MoneyTransferTest {
    SelenideElement errorMassage = $("[data-test-id='error-notification'] .notification__content");

    @BeforeEach
    void shouldStart() {
        open("http://localhost:9999/");
    }

    @AfterEach
    void shouldBalanceOut() {
        var authInfo = DataHelper.getAuthInfo();
        DashboardPage dashboardPage = new DashboardPage();
        int beforeBalanceFirstCard = dashboardPage.getIdAccountBalance(DataHelper.getFirstCardInfo(authInfo));
        int beforeBalanceSecondCard = dashboardPage.getIdAccountBalance(DataHelper.getSecondCardInfo(authInfo));
        int difference;
        if (beforeBalanceFirstCard == beforeBalanceSecondCard) {
            return;
        }
        if (beforeBalanceFirstCard > beforeBalanceSecondCard) {
            difference = beforeBalanceFirstCard - beforeBalanceSecondCard;
            difference = difference / 2;
            dashboardPage.transferMoney(
                    DataHelper.getSecondCardInfo(authInfo),
                    DataHelper.getFirstCardInfo(authInfo).getCardNumber(),
                    difference);
        } else {
            difference = beforeBalanceSecondCard - beforeBalanceFirstCard;
            difference = difference / 2;
            dashboardPage.transferMoney(
                    DataHelper.getFirstCardInfo(authInfo),
                    DataHelper.getSecondCardInfo(authInfo).getCardNumber(),
                    difference);
        }
    }

    @Test
    void shouldTransferMoneyToFirstCardFromSecondCardTest() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);

        int beforeBalanceFirstCard = dashboardPage.getIdAccountBalance(DataHelper.getFirstCardInfo(authInfo));
        int beforeBalanceSecondCard = dashboardPage.getIdAccountBalance(DataHelper.getSecondCardInfo(authInfo));
        dashboardPage.transferMoney(
                DataHelper.getFirstCardInfo(authInfo),
                DataHelper.getSecondCardInfo(authInfo).getCardNumber(),
                100);
        int afterBalanceFirstCard = dashboardPage.getIdAccountBalance(DataHelper.getFirstCardInfo(authInfo));
        int afterBalanceSecondCard = dashboardPage.getIdAccountBalance(DataHelper.getSecondCardInfo(authInfo));

        Assertions.assertEquals(beforeBalanceFirstCard + 100, afterBalanceFirstCard);
        Assertions.assertEquals(beforeBalanceSecondCard - 100, afterBalanceSecondCard);
    }

    @Test
    void shouldTransferMoneyToSecondCardFromFirstCardTest() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);

        int beforeBalanceFirstCard = dashboardPage.getIdAccountBalance(DataHelper.getFirstCardInfo(authInfo));
        int beforeBalanceSecondCard = dashboardPage.getIdAccountBalance(DataHelper.getSecondCardInfo(authInfo));
        dashboardPage.transferMoney(
                DataHelper.getSecondCardInfo(authInfo),
                DataHelper.getFirstCardInfo(authInfo).getCardNumber(),
                100);
        int afterBalanceFirstCard = dashboardPage.getIdAccountBalance(DataHelper.getFirstCardInfo(authInfo));
        int afterBalanceSecondCard = dashboardPage.getIdAccountBalance(DataHelper.getSecondCardInfo(authInfo));

        Assertions.assertEquals(beforeBalanceFirstCard - 100, afterBalanceFirstCard);
        Assertions.assertEquals(beforeBalanceSecondCard + 100, afterBalanceSecondCard);
    }

    @Test
    void shouldTransferMoneyToSecondCardFromFirstCardZeroTest() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);

        int beforeBalanceFirstCard = dashboardPage.getIdAccountBalance(DataHelper.getFirstCardInfo(authInfo));
        int beforeBalanceSecondCard = dashboardPage.getIdAccountBalance(DataHelper.getSecondCardInfo(authInfo));
        dashboardPage.transferMoney(
                DataHelper.getSecondCardInfo(authInfo),
                DataHelper.getFirstCardInfo(authInfo).getCardNumber(),
                0);
        int afterBalanceFirstCard = dashboardPage.getIdAccountBalance(DataHelper.getFirstCardInfo(authInfo));
        int afterBalanceSecondCard = dashboardPage.getIdAccountBalance(DataHelper.getSecondCardInfo(authInfo));

        Assertions.assertEquals(beforeBalanceFirstCard, afterBalanceFirstCard);
        Assertions.assertEquals(beforeBalanceSecondCard, afterBalanceSecondCard);
    }

    @Test
    void shouldTransferMoneyToSecondCardFromFirstCardNegativeSumTest() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);

        int beforeBalanceFirstCard = dashboardPage.getIdAccountBalance(DataHelper.getFirstCardInfo(authInfo));
        int beforeBalanceSecondCard = dashboardPage.getIdAccountBalance(DataHelper.getSecondCardInfo(authInfo));
        dashboardPage.transferMoney(
                DataHelper.getSecondCardInfo(authInfo),
                DataHelper.getFirstCardInfo(authInfo).getCardNumber(),
                -100);
        int afterBalanceFirstCard = dashboardPage.getIdAccountBalance(DataHelper.getFirstCardInfo(authInfo));
        int afterBalanceSecondCard = dashboardPage.getIdAccountBalance(DataHelper.getSecondCardInfo(authInfo));

        Assertions.assertEquals(beforeBalanceFirstCard - 100, afterBalanceFirstCard);
        Assertions.assertEquals(beforeBalanceSecondCard + 100, afterBalanceSecondCard);
    }

    @Test
    void shouldTransferMoneyToFirstCardFromSecondCardHugeSumTest() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);

        dashboardPage.transferMoney(
                DataHelper.getFirstCardInfo(authInfo),
                DataHelper.getSecondCardInfo(authInfo).getCardNumber(),
                15000);

        errorMassage.shouldHave(exactText("Ошибка!"))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldTransferMoneyToSecondCardFromFirstCardHugeSum2Test() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);

        dashboardPage.transferMoney(
                DataHelper.getFirstCardInfo(authInfo),
                DataHelper.getSecondCardInfo(authInfo).getCardNumber(),
                15000);

        errorMassage.shouldHave(exactText("Ошибка!"))
                .shouldBe(Condition.visible);
    }
}
