package tddcourse.basket.exceptions;

abstract class KnownExceptions extends RuntimeException {
    KnownExceptions(String error) {
        super(error);
    }
}
