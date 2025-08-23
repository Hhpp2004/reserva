package backend.system.reserva.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class NotFound extends ReservaException {
    public ProblemDetail tProblemDetail()
    {
        var pb = ProblemDetail.forStatus(HttpStatus.I_AM_A_TEAPOT);
        pb.setDetail("Email não encontrado");
        return pb;
    }
}
