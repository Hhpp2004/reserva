package backend.system.reserva.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class Inativo extends ReservaException{
    public ProblemDetail tProblemDetail()
    {
        var pb = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        pb.setTitle("Mesa Inativa");
        return pb;
    }
}
