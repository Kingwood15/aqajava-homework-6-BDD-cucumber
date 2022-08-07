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
    private ElementsCollection cardLine = $$("body .list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private final String searchAttribute = "data-test-id";

    public DashboardPage() {
        heading.shouldBe(Condition.visible);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public int getIdAccountBalance(DataHelper.Card selectCard) {
        String text = "0";
        for (SelenideElement searchCard : cardLine) {
            String searchDeleteValue = searchCard.getAttribute(searchAttribute);
            if (searchDeleteValue.equals(selectCard.getId())) {
                text = searchCard.text();
            }
        }
        return extractBalance(text);
    }

    public TransferPage replenishCard(DataHelper.Card cardTo) {
        for (SelenideElement searchCard : cardLine) {
            String searchDeleteValue = searchCard.getAttribute(searchAttribute);
            if (searchDeleteValue.equals(cardTo.getId())) {
                searchCard.$(" [" + searchAttribute + "]").click();
                break;
            }
        }
        return new TransferPage();
    }


    // добавленный метод
    /*public DashboardPage transferMoneyCucumber(String searchCard, String cardFrom, String sum) {
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
    }*/
}
