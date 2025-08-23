package backend.system.reserva.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class OAuth2Authentication extends ReservaException{
    public ProblemDetail tProblemDetail()
    {
        var pb = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        pb.setTitle("Email não fornecido pelo Google");
        return pb;
    }
}
