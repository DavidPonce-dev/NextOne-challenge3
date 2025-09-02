package com.NextOne.Challenge3.infra.exceptions;

public record DuplicateError(String error, String field, String value, String message, int code) { }