package com.softflow.exception;

/**
 * Exception thrown when a business rule gate is violated.
 * E.g., trying to create a DFD when Requirement is not approved.
 */
public class BusinessRuleViolationException extends RuntimeException {
    public BusinessRuleViolationException(String message) {
        super(message);
    }
}
