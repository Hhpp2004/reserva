package backend.system.reserva.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import backend.system.reserva.Exception.ReservaException;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(ReservaException.class)
    public ProblemDetail tProblemDetail(ReservaException e)
    {
        return e.tProblemDetail();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handProblemDetail(MethodArgumentNotValidException e)
    {
        var fielderros = e.getFieldErrors().stream()
            .map(f -> new InvalidParam(f.getField(), f.getDefaultMessage()))
            .toList();
        var pb = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pb.setTitle("400 - Your Request parameters didn't validate");
        pb.setProperty("invalid-params",fielderros);
        return pb;
    }

    private record InvalidParam(String name, String reason){}
}
