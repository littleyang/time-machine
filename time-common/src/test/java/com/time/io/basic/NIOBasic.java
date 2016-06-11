package com.time.io.basic;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOBasic {
	
	public static void main(String[] args) throws IOException{
		

		RandomAccessFile aFile = new RandomAccessFile("/Users/yangyang/house.rb", "rw");
		// get file channel
		FileChannel inChannel = aFile.getChannel();
		
		// allocate buffer as buffers
		// heap buffer and director buffer
		//ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		
		// read data to buffer
		int byteRead = inChannel.read(buffer);
		
		while(byteRead != -1){
			
			System.out.println("channel read data to buffer from channel " + byteRead);
			
			// buffer to flip to read mode
			buffer.flip();
			
			//read data from buffer
			while(buffer.hasRemaining()){
				System.out.println("read data from buffer"+ (char)buffer.getChar());
			}
			
			// clear buffer
			buffer.clear();
			
			byteRead = inChannel.read(buffer);

		}
		
		// close file
		aFile.close();

	}

}
