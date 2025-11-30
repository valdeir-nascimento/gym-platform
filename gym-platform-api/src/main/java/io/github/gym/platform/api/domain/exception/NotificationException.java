package io.github.gym.platform.api.domain.exception;


import io.github.gym.platform.api.domain.validation.handler.Notification;

public class NotificationException extends DomainException {

    public NotificationException(final String message, final Notification notification) {
        super(message, notification.getErrors());
    }

}
