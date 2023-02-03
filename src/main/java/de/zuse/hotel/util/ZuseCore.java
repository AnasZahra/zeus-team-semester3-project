package de.zuse.hotel.util;

import java.time.LocalDate;
import java.util.function.Consumer;

public class ZuseCore
{
    private static final boolean DEBUG_MODE = true;
    private static Consumer<String> callback;

    public static void setCallbackError(Consumer<String> func)
    {
        coreAssert(func != null, "You have to set a valid callback error function!!");

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

    /**
     * Only for Debugging, it works only in debug mode
     */
    public static void coreAssert(boolean condition, String msg)
    {
        if (DEBUG_MODE)
        {
            if (!condition)
                throw new IllegalArgumentException(msg);
        }
    }

    public static void isValidDate(LocalDate date, String msg)
    {
        if (date.isBefore(LocalDate.now()))
        {
            check(false, msg);
        }

    }


}
