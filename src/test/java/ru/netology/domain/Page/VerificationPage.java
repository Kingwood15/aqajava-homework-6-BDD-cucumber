package ru.netology.domain.Page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.domain.Data.DataHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {

    private SelenideElement verificationField = $("[data-test-id='code'] input");
    private SelenideElement verificationButton = $("button.button");
    private SelenideElement errorMassage = $("[data-test-id='error-notification'] .notification__content");

    public DashboardPage validVerify(DataHelper.VerificationCode info) {
        verificationField.setValue(info.getCode());
        verificationButton.click();
        return new DashboardPage();
    }

    public DashboardPage validVerifyCucumber(String verificationCode) {
        verificationField.setValue(verificationCode);
        verificationButton.click();
        return new DashboardPage();
    }

    public void invalidVerifyCucumber(String verificationCode) {
        verificationField.setValue(verificationCode);
        verificationButton.click();
    }

    public void verifyIsVerificationPage(){
        errorMassage.shouldHave(exactText("Ошибка! Неверно указан код! Попробуйте ещё раз."))
                .shouldBe(Condition.visible);
    }
}
