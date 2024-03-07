package akdemy.edu.share;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.Exceptions;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseException extends RuntimeException {
    private int status;
    private String message;
}
