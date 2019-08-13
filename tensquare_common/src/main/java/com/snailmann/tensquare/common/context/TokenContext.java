package com.snailmann.tensquare.common.context;

import org.springframework.stereotype.Component;

@Component
public class TokenContext<T> {

    public static final String Token = "Authorization";

    private ThreadLocal<T> holder = new ThreadLocal<>();

    public void set(T token) {
        holder.set(token);
    }

    public T get() {
        return holder.get();
    }

    public void clear() {
        holder.remove();
    }

}
