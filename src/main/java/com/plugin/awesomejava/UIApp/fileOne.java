package com.plugin.awesomejava.Forecast;

import java.awt.Desktop;
import java.net.URI;
import java.io.*;

class reviseJava {
    public static void main(String args[]){
        try {
            Runtime rt = Runtime.getRuntime();
            String url = "http://localhost:59125";
//            String url = "http://localhost:59125/process?INPUT_TYPE=TEXT&AUDIO=WAVE_FILE&OUTPUT_TYPE=AUDIO&LOCALE=LG&VOICE=blv4-hsmm&INPUT_TEXT=%22" + weatherUpdate + "%22";
            rt.exec("rundll32 url.dll, FileProtocolHandler " + url);
        } catch(IOException exc) {
            System.out.println(exc);
        }
    }
}


