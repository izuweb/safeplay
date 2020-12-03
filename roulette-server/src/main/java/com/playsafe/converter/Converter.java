package com.playsafe.converter;

import com.playsafe.exceptions.BetParseException;

public interface Converter<S,T> {
    T convert(S source) throws BetParseException;
}
