package ru.netology.web.data;

import lombok.Value;

import java.util.Random;

public class DataHelper {
  private DataHelper() {}

  @Value
  public static class AuthInfo {
    private String login;
    private String password;
  }

  public static AuthInfo getAuthInfo() {
    return new AuthInfo("vasya", "qwerty123");
  }

  @Value
  public static class CardNumber {
    private String number;
    private String id;
  }

  public static CardNumber getFirstCardNumber() {
    return new CardNumber ("5559 0000 0000 0001", "92df3f1c-a033-48e6-8390-206f6b1f56c0");
  }

  public static CardNumber getSecondCardNumber() {
    return new CardNumber("5559 0000 0000 0002", "0f3f5c2a-249e-4c3d-8287-09f7a039391d");
  }

  public static int ValidAmount(int amount) {
    return new Random().nextInt(amount) + 3;
  }

  public static int InvalidAmount(int amount) {
    return Math.abs(amount) + new Random().nextInt(20000);
  }

  @Value
  public static class VerificationCode {
    private String code;
  }

  public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
    return new VerificationCode("12345");
  }
}
