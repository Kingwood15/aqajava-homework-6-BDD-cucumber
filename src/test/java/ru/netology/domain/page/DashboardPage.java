package ru.netology.domain.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Keys;
import ru.netology.domain.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    private SelenideElement heading = $("[data-test-id='dashboard']");
    private ElementsCollection cards = $$(".list__item div");
    private SelenideElement transferAmount = $("[data-test-id='amount'] input");
    private SelenideElement transferFrom = $("[data-test-id='from'] input");
    private SelenideElement transferButton = $("[data-test-id='action-transfer'].button");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() { //контруктор проверки, что личный кабинет виден
        heading.shouldBe(Condition.visible);
    }

    public void verifyIsDashboardPage() {
        new DashboardPage();
    }

    public void verifyTrasferMoney(String searchCard, String expectedBalance) {
        DataHelper.Card cardTo = DataHelper.searchCardInfo(searchCard);
        //удаление пробелов из ожидаемого результата
        expectedBalance = expectedBalance.replaceAll("\\s+", "");
        Assertions.assertEquals(expectedBalance, String.valueOf(getCardBalance(cardTo.getId())));
    }

    public int getCardBalance(String cardId) {
        // TODO: перебрать все карты и найти по атрибуту data-test-id
        String text = "0";
        for (SelenideElement card : cards) {
            String textSearch = card.attr("data-test-id");
            if (textSearch.equals(cardId)) {
                text = card.text();
                break;
            }
        }
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public int getIdAccountBalance(DataHelper.Card card) {
        return getCardBalance(card.getId());
    }

    public DashboardPage transferMoney(DataHelper.Card cardTo, String cardFrom, int sum) {
        for (SelenideElement card : cards) {
            String text = card.attr("data-test-id");
            if (text.equals(cardTo.getId())) {
                card.$("button.button").click();
                transferAmount.sendKeys(Keys.LEFT_CONTROL + "A");
                transferAmount.sendKeys(Keys.BACK_SPACE);
                transferAmount.setValue(String.valueOf(sum));
                transferFrom.sendKeys(Keys.LEFT_CONTROL + "A");
                transferFrom.sendKeys(Keys.BACK_SPACE);
                transferFrom.setValue(cardFrom);
                transferButton.click();
                break;
            }
        }
        return new DashboardPage();
    }

    public DashboardPage transferMoneyCucumber(String searchCard, String cardFrom, String sum) {
        DataHelper.Card cardTo = DataHelper.searchCardInfo(searchCard);
        for (SelenideElement card : cards) {
            String text = card.attr("data-test-id");
            if (text.equals(cardTo.getId())) {
                card.$("button.button").click();
                transferAmount.sendKeys(Keys.LEFT_CONTROL + "A");
                transferAmount.sendKeys(Keys.BACK_SPACE);
                transferAmount.setValue(sum);
                transferFrom.setValue(cardFrom);
                transferButton.click();
                break;
            }
        }
        return new DashboardPage();
    }

    public void shouldBalanceOut() {
        var authInfo = DataHelper.getAuthInfo();
        DashboardPage dashboardPage = new DashboardPage();
        int beforeBalanceFirstCard = getIdAccountBalance(DataHelper.getFirstCardInfo(authInfo));
        int beforeBalanceSecondCard = getIdAccountBalance(DataHelper.getSecondCardInfo(authInfo));
        int difference;
        if (beforeBalanceFirstCard == beforeBalanceSecondCard) {
            return;
        }
        if (beforeBalanceFirstCard > beforeBalanceSecondCard) {
            difference = beforeBalanceFirstCard - beforeBalanceSecondCard;
            difference = difference / 2;
            transferMoney(
                    DataHelper.getSecondCardInfo(authInfo),
                    DataHelper.getFirstCardInfo(authInfo).getCardNumber(),
                    difference);
        } else {
            difference = beforeBalanceSecondCard - beforeBalanceFirstCard;
            difference = difference / 2;
            transferMoney(
                    DataHelper.getFirstCardInfo(authInfo),
                    DataHelper.getSecondCardInfo(authInfo).getCardNumber(),
                    difference);
        }
    }
}
