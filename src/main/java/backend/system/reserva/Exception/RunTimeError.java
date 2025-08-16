package backend.system.reserva.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class RunTimeError extends ReservaException{
    public ProblemDetail tProblemDetail()
    {
        var pb = ProblemDetail.forStatus(HttpStatus.I_AM_A_TEAPOT);
        pb.setTitle("Role não encontrado");
        return pb;
    }
}
