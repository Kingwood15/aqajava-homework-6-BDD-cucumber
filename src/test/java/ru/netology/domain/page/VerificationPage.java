package ru.netology.domain.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.domain.data.DataHelper;

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

    public void invalidVerify(DataHelper.VerificationCode info) {
        verificationField.setValue(info.getCode());
        verificationButton.click();
    }

    public void errorVerify() {
        errorMassage.shouldHave(exactText("Ошибка! Неверно указан код! Попробуйте ещё раз."))
                .shouldBe(Condition.visible);
    }
}
