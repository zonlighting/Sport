package ssv.com.exception;

import java.io.FileNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import ssv.com.dto.ResponseQuery;

@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * Tất cả các Exception không được khai báo sẽ được xử lý tại đây
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseQuery<?> handleAllException(Exception ex, WebRequest request) {
        // quá trình kiểm soat lỗi diễn ra ở đây
        return ResponseQuery.faild(ex.getLocalizedMessage(), 400);

    }

    /**
     * IndexOutOfBoundsException sẽ được xử lý riêng tại đây
     */
    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseQuery<?> TodoException(Exception ex, WebRequest request) {
        return ResponseQuery.faild(ex.getLocalizedMessage(), 400);
    }

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseQuery<?> imgException(Exception ex, WebRequest request) {
        return ResponseQuery.faild("invalid Image", 302);
    }
}