package ru.netology.domain.Steps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.ru.*;
import io.cucumber.java.tr.Ve;
import ru.netology.domain.Page.DashboardPage;
import ru.netology.domain.Page.LoginPage;
import ru.netology.domain.Page.VerificationPage;

public class TestSteps {
    private static LoginPage loginPage;
    private static VerificationPage verificationPage;
    private static DashboardPage dashboardPage;

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

    @Тогда("происходит успешная авторизация и пользователь попадает на страницу 'Личный кабинет'")
    public void verifyDashboardPage() {
        dashboardPage.verifyIsDashboardPage();
    }

    @Тогда("появляется ошибка о неверном коде проверки")
    public void failedSendVerifyCode() {
        verificationPage.verifyIsVerificationPage();
    }
}
