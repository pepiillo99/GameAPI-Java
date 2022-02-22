package me.pepe.GameAPI.Utils;

public interface Callback<T> {

    void done(T result, Exception exception);
}
