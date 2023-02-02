package de.zuse.hotel.core;

import java.util.function.Consumer;

public class ZuseCore
{
    public static final boolean DEBUG_MODE = true;
    private static Consumer<String> callback;

    public static void setCallbackError(Consumer<String> func)
    {
        if (func == null)
        {
            throw new IllegalArgumentException("You have to set a valid callback error function!!");
        }

        callback = func;
    }

    public static void check(boolean condition, String msg)
    {
        if (!condition)
        {
            if (DEBUG_MODE)
                throw new IllegalArgumentException(msg);
            else
                callback.accept(msg);
        }
    }
}
