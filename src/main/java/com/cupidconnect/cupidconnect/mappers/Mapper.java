package com.cupidconnect.cupidconnect.mappers;

public interface Mapper<A, B> {
    B mapTo(A a);

    A mapFrom(B b);

    A registerMapFrom(B userDTO);
}