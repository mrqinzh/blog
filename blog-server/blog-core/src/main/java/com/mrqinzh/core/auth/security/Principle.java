package com.mrqinzh.core.auth.security;

public interface Principle<T> {

    String getName();

    T getPrinciple();

}
