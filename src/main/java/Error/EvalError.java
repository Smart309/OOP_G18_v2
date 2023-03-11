package Error;

public class EvalError extends Throwable {
    public EvalError(String msg) {
        super(msg);
    }
}