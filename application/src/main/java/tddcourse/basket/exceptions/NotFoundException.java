package tddcourse.basket.exceptions;

public final class NotFoundException extends KnownExceptions {
  private static final long serialVersionUID = 7822388817225467939L;

  private NotFoundException(String error) {
    super(error);
  }

  public static NotFoundException withMsg(String error) {
    return new NotFoundException(error);
  }
}
