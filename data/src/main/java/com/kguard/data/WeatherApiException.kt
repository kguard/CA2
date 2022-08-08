package com.kguard.data

sealed class WeatherApiException(message: String): Throwable(message=message) {
    class ApplicationErrorException(message: String):WeatherApiException(message)
    class DbErrorException(message: String):WeatherApiException(message)
    class NoDataErrorException(message: String):WeatherApiException(message)
    class HttpErrorException(message: String):WeatherApiException(message)
    class ServiceTimeOutException(message: String):WeatherApiException(message)
    class InvalidRequestErrorException(message: String):WeatherApiException(message)
    class NoRequestErrorException(message: String):WeatherApiException(message)
    class NoOpenApiErrorException(message: String):WeatherApiException(message)
    class ServiceDeniedErrorException(message: String):WeatherApiException(message)
    class TemporarilyServiceKeyErrorException(message: String):WeatherApiException(message)
    class LimitedServiceErrorException(message: String):WeatherApiException(message)
    class NotRegisteredServiceKeyErrorException(message: String):WeatherApiException(message)
    class DeadLineErrorException(message: String):WeatherApiException(message)
    class UnregisteredIpErrorException(message: String):WeatherApiException(message)
    class UnsignedCallErrorException(message: String):WeatherApiException(message)
    class UnknownErrorException(message: String):WeatherApiException(message)
}