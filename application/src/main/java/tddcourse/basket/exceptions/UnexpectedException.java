package tddcourse.basket.exceptions;

import java.util.List;

public final class UnexpectedException extends KnownExceptions {
    private static final long serialVersionUID = 7822388817225467939L;

    private UnexpectedException(String error) {
        super(error);
    }

    public static UnexpectedException withMsg(String msg) {
        return new UnexpectedException(msg);
    }

    public static UnexpectedException withMsgAndStacktrace(String msg, List<String> stacktrace) {
        return new UnexpectedException(msg + "\n" + String.join("\n", stacktrace));
    }
}
