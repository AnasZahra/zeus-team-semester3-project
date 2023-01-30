package de.zuse.hotel.core;

public class ZuseCore
{

    public static void setCallbackError()
    {
    }

    public static void check(boolean condition, String msg)
    {
        if (!condition)
        {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void checkFatal(boolean condition, String msg)
    {
        // only In Debug Mode
        if (!condition)
        {
            throw new IllegalArgumentException(msg);
        }
    }

}
