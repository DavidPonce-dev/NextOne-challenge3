package com.NextOne.Challenge3.Services.exceptions;

public record DuplicateError(String error, String field, String value, String message, int code) { }