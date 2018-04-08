package com.humble.gongxi.test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import com.humble.gongxi.PubLogger;

//import android.text.TextUtils;
//import android.util.Log;

/**
 * Implementation of a very basic HTTP server. The contents are loaded from the
 * assets folder. This server handles one request at a time. It only supports
 * GET method.
 */
public class SimpleWebServerTest implements Runnable {

	private static final String TAG = "SimpleWebServerTest";
	private sattic final String SERV_LOC = "";
	
	/**
	 * The port number we listen to
	 */
	private int mPort;
//	private final int mPort;

	/**
	 * {@link android.content.res.AssetManager} for loading files to serve.
	 */
//	private AssetManager mAssets;
//	private final AssetManager mAssets;

	/**
	 * True if the server is running.
	 */
	private boolean mIsRunning;

	/**
	 * The {@link java.net.ServerSocket} that we listen to.
	 */
	private ServerSocket mServerSocket;

	/**
	 * WebServer constructor.
	 */
	public SimpleWebServerTest(int port) {
//	public SimpleWebServerTest(int port, AssetManager assets) {
		this.mPort = port;
//		this.mAssets = assets;
	}

	/**
	 * This method starts the web server listening to the specified port.
	 */
	public void start() {
		mIsRunning = true;
		new Thread(this).start();
	}

	/**
	 * This method stops the web server
	 */
	public void stop() {
		try {
			mIsRunning = false;
			if (null != mServerSocket) {
				mServerSocket.close();
				mServerSocket = null;
			}
		} catch (IOException e) {
			PubLogger.error(TAG + ", Error closing the server socket. ex =%s", e);
		}
	}

	public int getPort() {
		return mPort;
	}

	@Override
	public void run() {
		try {
			mServerSocket = new ServerSocket(mPort);
			while (mIsRunning) {
				Socket socket = mServerSocket.accept();
				handle(socket);
				socket.close();
			}
		} catch (SocketException e) {
			// The server was stopped; ignore.
		} catch (IOException e) {
			PubLogger.error(TAG + ", Web server error.", e);
		}
	}

	/**
	 * Respond to a request from a client.
	 *
	 * @param socket
	 *            The client socket.
	 * @throws IOException
	 */
	private void handle(Socket socket) throws IOException {
		BufferedReader reader = null;
		PrintStream output = null;
		try {
			String route = null;

			// Read HTTP headers and parse out the route.
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line;
			while (!SimpleWebServerTest.isEmpty(line = reader.readLine())) {
				PubLogger.info("//-- %s", line);
				if (line.startsWith("GET /")) {
					int start = line.indexOf('/') + 1;
					int end = line.indexOf(' ', start);
					route = line.substring(start, end);
					break;
				}
			}

			// Output stream that we send the response to
			output = new PrintStream(socket.getOutputStream());

			// Prepare the content to send.
			if (null == route) {
				writeServerError(output);
				return;
			}
//			byte[] bytes = loadContent(route);
			byte[] bytes = loadContent(SERV_LOC + route);
			if (null == bytes) {
				writeServerError(output);
				return;
			}

			// Send out the content.
			output.println("HTTP/1.0 200 OK");
			output.println("Content-Type: " + detectMimeType(route));
			output.println("Content-Length: " + bytes.length);
			output.println();
			output.write(bytes);
			output.flush();
		} finally {
			if (null != output) {
				output.close();
			}
			if (null != reader) {
				reader.close();
			}
		}
	}

	/**
	 * Writes a server error response (HTTP/1.0 500) to the given output stream.
	 *
	 * @param output
	 *            The output stream.
	 */
	private void writeServerError(PrintStream output) {
		output.println("HTTP/1.0 500 Internal Server Error. ERON-ZZNG.!!");
		output.flush();
	}

	/**
	 * Loads all the content of {@code fileName}.
	 *
	 * @param fileName
	 *            The name of the file.
	 * @return The content of the file.
	 * @throws IOException
	 */
	private byte[] loadContent(String fileName) throws IOException {
		InputStream input = null;
		try {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
//			input = mAssets.open(fileName);
			input = new FileInputStream(new File(fileName));
			byte[] buffer = new byte[1024];
			int size;
			while (-1 != (size = input.read(buffer))) {
				output.write(buffer, 0, size);
			}
			output.flush();
			return output.toByteArray();
		} catch (FileNotFoundException e) {
			PubLogger.error("//-- WebServer.loadContent(..).fileName =[%s]", fileName);
			PubLogger.error("//-- NotFoundEx.Msg =[%s]", e.getMessage());
			return null;
		} finally {
			if (null != input) {
				input.close();
			}
		}
	}

	/**
	 * Detects the MIME type from the {@code fileName}.
	 *
	 * @param fileName
	 *            The name of the file.
	 * @return A MIME type.
	 */
	private String detectMimeType(String fileName) {
		if (SimpleWebServerTest.isEmpty(fileName)) {
			return null;
		} else if (fileName.endsWith(".html")) {
			return "text/html";
		} else if (fileName.endsWith(".js")) {
			return "application/javascript";
		} else if (fileName.endsWith(".css")) {
			return "text/css";
		} else {
			return "application/octet-stream";
		}
	}
    /**
     * Returns true if the string is null or 0-length.
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    public static boolean isEmpty(CharSequence str) {
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }
    
	public static void main(String...args){
		new SimpleWebServerTest(8082).start();
	}
}
