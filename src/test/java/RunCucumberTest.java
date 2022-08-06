import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "summary"},
        features = {"src/test/resources/features"},
        glue = {"ru.netology.domain.Steps"})
public class RunCucumberTest {


    /*@AfterEach
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
    }*/
}
