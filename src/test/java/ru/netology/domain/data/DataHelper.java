package ru.netology.domain.data;

import lombok.Value;
import ru.netology.domain.page.DashboardPage;

public class DataHelper {

    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    @Value
    public static class Card {
        private String id;
        private String cardNumber;
    }

    public static Card getFirstCardInfo(AuthInfo authInfo) {
        return new Card("92df3f1c-a033-48e6-8390-206f6b1f56c0", "5559 0000 0000 0001");
    }

    public static Card getSecondCardInfo(AuthInfo authInfo) {
        return new Card("0f3f5c2a-249e-4c3d-8287-09f7a039391d", "5559 0000 0000 0002");
    }

    //добавленный метод
    public static Card searchCardInfo(String cardNumber) {
        AuthInfo authInfo = getAuthInfo();
        DataHelper.Card searchCard = new Card("0", "0");
        if (getFirstCardInfo(authInfo).cardNumber.equals(cardNumber)) {
            searchCard = getFirstCardInfo(authInfo);
        }
        if (getSecondCardInfo(authInfo).cardNumber.equals(cardNumber)) {
            searchCard = getFirstCardInfo(authInfo);
        }
        return searchCard;
    }

    public static void shouldBalanceOut() {
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
            var transferPage = dashboardPage.replenishCard(DataHelper.getSecondCardInfo(authInfo));
            transferPage.transferMoney(
                    DataHelper.getFirstCardInfo(authInfo).getCardNumber(),
                    String.valueOf(difference));
        } else {
            difference = beforeBalanceSecondCard - beforeBalanceFirstCard;
            difference = difference / 2;
            var transferPage = dashboardPage.replenishCard(DataHelper.getFirstCardInfo(authInfo));
            transferPage.transferMoney(
                    DataHelper.getSecondCardInfo(authInfo).getCardNumber(),
                    String.valueOf(difference));
        }
    }
}