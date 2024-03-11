package akdemy.edu.response;


import io.netty.handler.codec.http.HttpStatusClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum StatusCode {

    WITHDRAW_SUCCESS(200, "WITHDRAW SUCCESS"),

    DEPOSIT_SUCCESS(200, "DEPOSIT SUCCESS"),

    TRANSFER_SUCCESS(200, "TRANSFER SUCCESS");

    private final int code;

    private final String message;
}
