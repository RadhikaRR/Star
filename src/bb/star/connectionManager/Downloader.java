package bb.star.connectionManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.microedition.io.HttpConnection;

public class Downloader {

	HttpConnection connection = null;
	InputStream inputStream = null;

	public byte[] downloadResourceAsStream(String url) throws IOException {

		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		try {
			connection = ConnectionFectory.createConnection(url, null);
			int respcode = connection.getResponseCode();

			if (respcode == HttpConnection.HTTP_OK) {
				inputStream = connection.openInputStream();
				try {
					int next = -1;
					int i = 0;
					while ((next = inputStream.read()) != -1) {
						i++;
						arrayOutputStream.write(next);
					}
				} catch (Exception e) {
					throw new IOException("Can not parse response.");
				}
			} else if (respcode == HttpConnection.HTTP_INTERNAL_ERROR) {
				throw new IOException("Server is unable to process your request. Please try again after some time.");

			} else if (respcode == HttpConnection.HTTP_GATEWAY_TIMEOUT) {
				throw new IOException("Request timeout. Please try again after some time.");
			} else {
				throw new IOException("Connectivity Problem. Please try after some time.");
			}

		} catch (Exception e) {
			throw new IOException("Connectivity Error:" + e.getMessage());

		} finally {
			close(connection, inputStream);
		}
		return arrayOutputStream.toByteArray();
	}

	private void close(HttpConnection con, InputStream in) {
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (IOException e) {
			}
		}
	}
}