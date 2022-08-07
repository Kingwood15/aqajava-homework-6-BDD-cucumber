package ru.netology.domain.steps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.ru.*;
import ru.netology.domain.page.DashboardPage;
import ru.netology.domain.page.LoginPage;
import ru.netology.domain.page.VerificationPage;

public class TransferSteps {
    private static LoginPage loginPage;
    private static VerificationPage verificationPage;
    private static DashboardPage dashboardPage;
    String cardPattern = "5559 0000 0000 000";

    @Пусть("открыта страница с формой авторизации {string}")
    public void openAuthPage(String url) {
        loginPage = Selenide.open(url, LoginPage.class);
    }

    @Когда("пользователь пытается авторизоваться с именем {string} и паролем {string}")
    public void loginWithNameAndPassword(String login, String password) {
        verificationPage = loginPage.validLoginCucumber(login, password);
    }

    @И("пользователь вводит корректный проверочный код 'из смс' {string}")
    public void setValidCode(String verificationCode) {
        dashboardPage = verificationPage.validVerifyCucumber(verificationCode);
    }

    @И("пользователь вводит некорректный проверочный код 'из смс' {string}")
    public void setInvalidCode(String verificationCode) {
        verificationPage.invalidVerifyCucumber(verificationCode);
    }

    @Когда("пользователь переводит {string} рублей с карты с номером {string} на свою {string} карту с главной страницы")
    public void transferMoney(String sum, String cardFrom, String cardTo) {
        cardTo = cardPattern + cardTo;
        dashboardPage.transferMoneyCucumber(cardTo, cardFrom, sum);
    }

    @Тогда("происходит успешная авторизация и пользователь попадает на страницу 'Личный кабинет'")
    public void verifyDashboardPage() {
        dashboardPage.verifyIsDashboardPage();
    }

    @Тогда("появляется ошибка о неверном коде проверки")
    public void failedSendVerifyCode() {
        verificationPage.verifyIsVerificationPage();
    }

    @Тогда("баланс его {string} карты из списка на главной странице должен стать {string} рублей")
    public void verifyTransferMoney(String cardTo, String balance) {
        cardTo = cardPattern + cardTo;
        dashboardPage.verifyTrasferMoney(cardTo, balance);
    }

    @И("вернуть баланс в исходное состояние")
    public void balanceMoney() {
        dashboardPage.shouldBalanceOut();
    }
}
