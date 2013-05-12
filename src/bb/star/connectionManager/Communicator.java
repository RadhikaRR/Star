package bb.star.connectionManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.microedition.io.HttpConnection;

import net.rim.device.api.io.http.HttpHeaders;
import net.rim.device.api.system.CoverageInfo;
import net.rim.device.api.system.RadioInfo;

public class Communicator {

	private String URL;
	private byte[] postdata;

	private static HttpConnection connection;
	private static InputStream inputStream;

	int connectionType = HttpUtils.CONNECTION_DEFAULT;

	public Communicator(String url, byte[] postdata) {
		this.URL = url;
		this.postdata = postdata;
	}

	public String communicate() throws Exception {
		String response = service(URL, postdata);
		return response;
	}

	public String service(String url, byte[] data) throws IOException {

		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		String result = null;
		try {
			connection = ConnectionFectory.createConnection(URL, data);
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
					result = new String(arrayOutputStream.toByteArray());
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

		} catch (IOException e) {
			System.out.println("aaaa-------------------------------"+ e.getMessage());
			throw new IOException("Connectivity Error");			
		} catch (Exception e) {
			System.out.println("bbbb-------------------------------"+ e.getMessage());
		} finally {
			close(connection, inputStream);
		}
		return result;
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

	protected final HttpConnection makeDefaultConnection(String URL, byte[] data) {
		HttpConnection con;
		try {
			con = HttpUtils.makeHttpConnection(URL, new HttpHeaders(), data, HttpUtils.CONNECTION_DEFAULT);
			con.getResponseCode();
			connectionType = HttpUtils.CONNECTION_DEFAULT;
			return con;
		} catch (Exception ex) {
			System.out.println("TCP IP connection failed: " + ex);
			con = null;
		}
		return null;
	}

	protected final HttpConnection makeBESConnection(String URL, byte[] data) {
		if (CoverageInfo.isCoverageSufficient(CoverageInfo.COVERAGE_MDS)) {
			HttpConnection con;
			try {
				con = HttpUtils.makeHttpConnection(URL, new HttpHeaders(), data, HttpUtils.CONNECTION_BES);
				con.getResponseCode();
				connectionType = HttpUtils.CONNECTION_BES;
				return con;
			} catch (Exception ex) {
				System.out.println("TCP IP connection failed: " + ex);
				con = null;
			}
		}
		return null;
	}

	protected final HttpConnection makeBISConnection(String URL2, byte[] data) {
		// TODO Not implemented
		// Will return NULL
		return null;
	}

	protected final HttpConnection makeDirectTCPConnection(String URL, byte[] data) {
		int coverageType = 1;
		// for CoverageInfo.COVERAGE_DIRECT(jde 4.5+ ) and
		// CoverageInfo.COVERAGE_CARRIER(old jde)

		if (CoverageInfo.isCoverageSufficient(coverageType)) {
			HttpConnection con;
			try {
				con = HttpUtils.makeHttpConnection(URL, new HttpHeaders(), data, HttpUtils.CONNECTION_TCPIP);
				con.getResponseCode();
				connectionType = HttpUtils.CONNECTION_TCPIP;
				return con;
			} catch (Exception ex) {
				System.out.println("TCP IP connection failed: " + ex);
				con = null;
			}
		}
		return null;
	}

	protected final HttpConnection makeWiFiConnection(String URL, byte[] data) {
		if ((RadioInfo.getActiveWAFs() & RadioInfo.WAF_WLAN) != 0) {
			HttpConnection con;
			try {
				int coverageType = 1;
				// for CoverageInfo.COVERAGE_DIRECT(jde 4.5+ ) and
				// CoverageInfo.COVERAGE_CARRIER(old jde)

				if (CoverageInfo.isCoverageSufficient(coverageType, RadioInfo.WAF_WLAN, true)) {
					con = HttpUtils.makeHttpConnection(URL, new HttpHeaders(), data, HttpUtils.CONNECTION_WIFI);
					con.getResponseCode();
					connectionType = HttpUtils.CONNECTION_WIFI;
					return con;
				}
			} catch (Exception ex) {
				System.out.println("WiFI connection failed: " + ex);
				con = null;
			}
		}
		return null;
	}
}
