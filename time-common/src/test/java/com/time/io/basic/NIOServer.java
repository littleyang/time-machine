package com.time.io.basic;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NIOServer {

	// port
	private int port = 8888;
	
	//decode
	private CharsetDecoder decode = Charset.forName("UTF-8").newDecoder();
	
	// send buffer
	private ByteBuffer sendBuffer = ByteBuffer.allocate(1024);
	// receive buffer
	private ByteBuffer recBuffer = ByteBuffer.allocate(1024);
	
	/*映射客户端channel */
    private HashMap<String, SocketChannel> clientsMap = new HashMap<String, SocketChannel>(); 
    
    private Selector selector;
    
    private SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss", java.util.Locale.US);
    
    
    public NIOServer(){
    	try {
            init();
            listen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


	private void listen() throws IOException {
		// TODO Auto-generated method stub
		// 监听所有的事件
		while(true){
			selector.select();//返回值为本次触发的事件数
			Set<SelectionKey> selectionKeys = selector.keys();
			for(SelectionKey key : selectionKeys){
				handle(key);
			}
			selectionKeys.clear();//清除处理过的事件 
		}
	}


	private void init() throws IOException {
		// TODO Auto-generated method stub
		// 端口初始化，注册channnel等
		//SocketChannel channel = SocketChannel.open();
		ServerSocketChannel socketChannel = ServerSocketChannel.open();
		// 将 channel 设置为非阻塞的
		socketChannel.configureBlocking(false);
		
		// get socket
		ServerSocket socket = socketChannel.socket();
		socket.bind(new InetSocketAddress(port));
		
		// open selector and register,accept event
		selector = Selector.open();  
		socketChannel.register(selector, SelectionKey.OP_ACCEPT);
		
		System.out.println("server start on port:"+port);  
		
	}
    
	private void handle(SelectionKey key) throws IOException {
		// TODO Auto-generated method stub
		
		ServerSocketChannel server = null;
        SocketChannel client = null;
        String receiveText=null;
        int count=0;
        
        if(key.isAcceptable()){
        	// client require accept events
        	server = (ServerSocketChannel) key.channel();
        	client = server.accept();
        	client.configureBlocking(false);
        	client.register(selector, SelectionKey.OP_READ);
        }else if(key.isReadable()){
        	// 如果是read事件，则直接读取
        	 client = (SocketChannel) key.channel();
        	 recBuffer.clear();
        	 count = client.read(recBuffer);
        	 if (count > 0) {
        		 recBuffer.flip();
                 receiveText = decode.decode(recBuffer.asReadOnlyBuffer()).toString();
                 System.out.println(client.toString()+":"+receiveText);
                 sendBuffer.clear();
                 sendBuffer.put((sdf.format(new Date())+"服务器收到你的消息").getBytes());
                 sendBuffer.flip();
                 client.write(sendBuffer);
                 dispatch(client, receiveText);
                 client = (SocketChannel) key.channel();
                 client.register(selector, SelectionKey.OP_READ);
             }  
        }
		
	}
	
	 /** 
     * 把当前客户端信息 推送到其他客户端 
     */
    private void dispatch(SocketChannel client,String info) throws IOException{  

        Socket s = client.socket();  
        String name = "["+s.getInetAddress().toString().substring(1)+":"+Integer.toHexString(client.hashCode())+"]";  
        if(!clientsMap.isEmpty()){
            for(Map.Entry<String, SocketChannel> entry : clientsMap.entrySet()){
                SocketChannel temp = entry.getValue();
                if(!client.equals(temp)){
                    sendBuffer.clear();
                    sendBuffer.put((name+":"+info).getBytes());
                    sendBuffer.flip();
                    //输出到通道
                    temp.write(sendBuffer);
                }
            }
        }
        clientsMap.put(name, client);
    }
    
    
    public static void main(String[] args){
    	
    	 new NIOServer();
    	
    }
}
