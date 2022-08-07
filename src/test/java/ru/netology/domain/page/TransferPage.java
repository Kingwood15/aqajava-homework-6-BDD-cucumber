package ru.netology.domain.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement heading = $("[data-test-id='dashboard']");
    private SelenideElement transferAmount = $("[data-test-id='amount'] input");
    private SelenideElement transferFrom = $("[data-test-id='from'] input");
    private SelenideElement transferButton = $("[data-test-id='action-transfer'].button");
    private SelenideElement errorMassage = $("[data-test-id='error-notification'] .notification__content");

    public TransferPage() {
        heading.shouldBe(Condition.visible);
    }

    public TransferPage transferPageErrorMassage() {
        errorMassage.shouldHave(exactText("Ошибка!"))
                .shouldBe(Condition.visible);
        return new TransferPage();
    }

    public DashboardPage transferMoney(String cardFrom, String sum) {
        transferAmount.sendKeys(Keys.LEFT_CONTROL + "A");
        transferAmount.sendKeys(Keys.BACK_SPACE);
        transferAmount.setValue(sum);
        transferFrom.sendKeys(Keys.LEFT_CONTROL + "A");
        transferFrom.sendKeys(Keys.BACK_SPACE);
        transferFrom.setValue(cardFrom);
        transferButton.click();
        return new DashboardPage();
    }
}