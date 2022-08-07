package ru.netology.domain.steps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.ru.*;
import org.junit.jupiter.api.Assertions;
import ru.netology.domain.data.DataHelper;
import ru.netology.domain.page.DashboardPage;
import ru.netology.domain.page.LoginPage;
import ru.netology.domain.page.TransferPage;
import ru.netology.domain.page.VerificationPage;

public class TransferSteps {
    private static LoginPage loginPage;
    private static VerificationPage verificationPage;
    private static DashboardPage dashboardPage;
    private static TransferPage transferPage;
    String cardPattern = "5559 0000 0000 000";

    @Пусть("открыта страница с формой авторизации {string}")
    public void openAuthPage(String url) {
        loginPage = Selenide.open(url, LoginPage.class);
    }

    @Когда("пользователь пытается авторизоваться с именем {string} и паролем {string}")
    public void loginWithNameAndPassword(String login, String password) {
        DataHelper.AuthInfo cucumberUser = new DataHelper.AuthInfo(login, password);
        verificationPage = loginPage.validLogin(cucumberUser);
    }

    @И("пользователь вводит корректный проверочный код 'из смс' {string}")
    public void setValidCode(String verificationCode) {
        DataHelper.VerificationCode cucumberVerificationCode = new DataHelper.VerificationCode(verificationCode);
        dashboardPage = verificationPage.validVerify(cucumberVerificationCode);
    }

    @И("пользователь вводит некорректный проверочный код 'из смс' {string}")
    public void setInvalidCode(String verificationCode) {
        DataHelper.VerificationCode cucumberVerificationCode = new DataHelper.VerificationCode(verificationCode);
        verificationPage.invalidVerify(cucumberVerificationCode);
    }

    @Когда("пользователь переводит {string} рублей с карты с номером {string} на свою {string} карту с главной страницы")
    public void transferMoney(String sum, String cardFrom, String cardTo) {
        cardTo = cardPattern + cardTo;
        DataHelper.Card cucumberCard = DataHelper.searchCardInfo(cardTo);
        transferPage = dashboardPage.replenishCard(cucumberCard);
        dashboardPage = transferPage.transferMoney(cardFrom, sum);
    }

    @Тогда("происходит успешная авторизация и пользователь попадает на страницу 'Личный кабинет'")
    public void verifyDashboardPage() {
        new DashboardPage();
    }

    @Тогда("появляется ошибка о неверном коде проверки")
    public void failedSendVerifyCode() {
        verificationPage.errorVerify();
    }

    @Тогда("баланс его {string} карты из списка на главной странице должен стать {string} рублей")
    public void verifyTransferMoney(String cardTo, String expected) {
        cardTo = cardPattern + cardTo;
        DataHelper.Card cucumberCard = DataHelper.searchCardInfo(cardTo);
        int actual = dashboardPage.getIdAccountBalance(cucumberCard);
        //удаление пробелов из ожидаемого результата
        expected = expected.replaceAll("\\s+", "");

        Assertions.assertEquals(expected, String.valueOf(actual));
    }

    @И("вернуть баланс в исходное состояние")
    public void balanceMoney() {
        verifyDashboardPage();
        DataHelper.shouldBalanceOut();
    }
}
