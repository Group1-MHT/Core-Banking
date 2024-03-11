package akdemy.edu.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCode {

    WITH_DRAW_SUCCESS(200,"WITH DRAW SUCCESS"),

    DEPOSIT_SUCCESS(200,"DEPOSIT SUCCESS"),

    TRANFER_SUCCESS(200,"TRANFER SUCCESS");

    private final int code;

    private final String message;
}
