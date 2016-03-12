package com.phoenixkahlo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/*
 * Static class for binary operations
 */
public class BinUtils {

	private BinUtils() {}
	
	public static byte[] intToBytes(int n) {
		return ByteBuffer.allocate(4).putInt(n).array();
	}
	
	public static int bytesToInt(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getInt();
	}
	
	public static void writeInt(OutputStream out, int n) throws IOException {
		out.write(intToBytes(n));
	}
	
	public static int readInt(InputStream in) throws IOException {
		byte[] bytes = new byte[4];
		in.read(bytes);
		return bytesToInt(bytes);
	}
	
	public static byte[] doubleToBytes(double n) {
		return ByteBuffer.allocate(8).putDouble(n).array();
	}
	
	public static double bytesToDouble(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getDouble();
	}
	
	public static void writeDouble(OutputStream out, double n) throws IOException {
		out.write(doubleToBytes(n));
	}
	
	public static double readDouble(InputStream in) throws IOException {
		byte[] bytes = new byte[8];
		in.read(bytes);
		return bytesToDouble(bytes);
	}
	
	public static byte[] stringToBytes(String string) {
    	return string.getBytes(StandardCharsets.UTF_8);
    }
    
	public static String bytesToString(byte[] bytes) {
    	return new String(bytes, StandardCharsets.UTF_8);
    }
	
}
