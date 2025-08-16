package backend.system.reserva.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class Capacidade extends ReservaException{
    public ProblemDetail tProblemDetail()
    {
        var pb = ProblemDetail.forStatus(HttpStatus.IM_USED);
        pb.setTitle("Essa mesa não tem capacidade para a sua demanda");
        return pb;
    }
}
