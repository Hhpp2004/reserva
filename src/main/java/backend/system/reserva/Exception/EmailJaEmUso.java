package backend.system.reserva.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class EmailJaEmUso extends ReservaException{
    public ProblemDetail toProblemDetail()
    {
        var pb = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        pb.setTitle("Email em uso");
        return pb;
    }
}
