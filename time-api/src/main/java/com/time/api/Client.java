package com.time.api;

import java.net.Socket;

public class Client{  
    public static void main(String[] args) {  
           try {  
               Socket socket = new Socket("127.0.0.1", 7070);  
               String res = null;  
               try {  
                   String request = "NIO Test...";  
                   byte[] bts = request.getBytes();  
                   socket.getOutputStream().write(bts);  
                   socket.getOutputStream().flush();             
                   byte[] resb = new byte[60000];  
                   socket.getInputStream().read(resb);  
                   res = new String(resb);  
                   System.out.println(res);  
               } catch (Exception e) {  
                   e.printStackTrace();  
               }  
           } catch (Exception e) {  
               // TODO Auto-generated catch block  
               e.printStackTrace();  
           }  
       }  
}  