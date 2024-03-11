package akdemy.edu.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    ACCOUNT_NOT_FOUND(1001, "Account has not found."),

    ACCOUNT_NOT_ENOUGH_MONEY(1002, "There is not enough money in account.");

    private final int code;

    private final String message;
}
