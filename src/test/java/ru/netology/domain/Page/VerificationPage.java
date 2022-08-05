package ru.netology.domain.Page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.domain.Data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {

    private SelenideElement verificationField = $("[data-test-id='code'] input");
    private SelenideElement verificationButton = $("button.button");

    public DashboardPage validVerify(DataHelper.VerificationCode info) {
        verificationField.setValue(info.getCode());
        verificationButton.click();
        return new DashboardPage();
    }
}
