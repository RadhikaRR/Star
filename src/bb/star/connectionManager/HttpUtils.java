package bb.star.connectionManager;

import java.io.IOException;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

import net.rim.device.api.io.http.HttpHeaders;
import net.rim.device.api.util.StringUtilities;

public class HttpUtils {
	
	private static long TIME_OUT = 60000;
	public static final int CONNECTION_DEFAULT = 0;
	public static final int CONNECTION_BIS = 1;
	public static final int CONNECTION_BES = 2;
	public static final int CONNECTION_TCPIP = 3;
	public static final int CONNECTION_WIFI = 4;
	
	public static final String TCP_IP_PREFIX = ";deviceside=true";
	public static final String WIFI_PREFIX = ";interface=wifi;deviceside=true";
	public static final String BES_PREFIX = ";deviceside=false";
	public static final String BIS_PREFIX = ";XXXXXXXXXXXXXXXX";

	public static final int DEFAULT = 0;
	public static final int WIFI = 1;
	public static final int NETWORK = 2;

	/**
	 * This method opens a HTTP connection to the given url. The method used is
	 * GET or POST depending on whether postData is null or not. Only the
	 * provided connType is used. For example, if the connType is
	 * CONNECTION_BES, the connection is tried using the BES only. The only time
	 * provided connection type is not used is when the URL contains
	 * ";deviceside=".
	 * 
	 * @param url
	 *            The url to connect to.
	 * @param requestHeaders
	 *            The headers in the request. May be null or empty.
	 * @param postData
	 *            Data to be posted to the server. If null, the GET method used
	 *            for the http connection.
	 * @param connType
	 *            The type of transport (BES / BIS / WIFI / Default) to be used
	 *            for opening connection.
	 * @return Opened HttpConnection object or null if some error occurs.
	 */
	public static HttpConnection makeHttpConnection(String url,
			HttpHeaders requestHeaders, byte[] postData, int connType) {
		HttpConnection conn = null;
		OutputStream out = null;

		if (StringUtilities.startsWithIgnoreCase(url, "www.")) {
			url = "http://" + url;
		}

		try {
			if (url.indexOf(";deviceside=") == -1) {
				switch (connType) {
				case CONNECTION_BES:
					url = url + BES_PREFIX;
					break;
				case CONNECTION_BIS:
					url = url + BIS_PREFIX;
					break;
				case CONNECTION_TCPIP:
					url = url + TCP_IP_PREFIX;
					break;
				case CONNECTION_WIFI:
					url = url + WIFI_PREFIX;
				}
			}
			url = url + ";ConnectionTimeout=" + TIME_OUT;
			conn = (HttpConnection) Connector.open(url);

			if (postData == null) {
				conn.setRequestMethod(HttpConnection.GET);
				// conn.setRequestProperty("User-Agent",
				// "Profile/MIDP-2.0 Configuration/CLDC-1.0");
			} else {
				conn.setRequestMethod(HttpConnection.POST);
				/*conn.setRequestProperty(
						HttpProtocolConstants.HEADER_CONTENT_LENGTH, String
								.valueOf(postData.length));
				conn.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");*/
				conn.setRequestProperty("Content-Length", Integer
						.toString(postData.length));

				conn.setRequestProperty("Content-Type", "application/binary");
				// conn.setRequestProperty("User-Agent",
				// "Profile/MIDP-2.0 Configuration/CLDC-1.0");

				out = conn.openOutputStream();
				out.write(postData);
				out.flush();
			}
		} catch (IOException e1) {
			close(conn, null); // Close the connection
			conn = null;
		} finally {
			close(null, out); // Close the output, but keep connection open
		}

		return conn;
	}

	private static void close(HttpConnection con, OutputStream out) {
		if (out != null) {
			try {
				out.close();
			} catch (IOException e2) {
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
