package backend.system.reserva.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class Reservado extends ReservaException{
    public ProblemDetail toProblemDetail()
    {
        var pb = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        pb.setTitle("Mesa reservado");
        return pb;
    }
}
