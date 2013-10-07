package quique.laboratorio.simon;

import android.util.Log;
/*
 * Esta es un clase que permitirá enviar información al log y poder filtrarla con la palabra clave info.
 * */
public final class Debug{
    private Debug (){}

    public static void out (Object msg){
        Log.i ("info", msg.toString ());///Envia al log el msg con titulo info, para poder despulgar el código.
    }
}