package bb.star.connectionManager;

import java.io.IOException;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

import net.rim.device.api.servicebook.ServiceBook;
import net.rim.device.api.servicebook.ServiceRecord;
import net.rim.device.api.synchronization.ConverterUtilities;
import net.rim.device.api.system.CDMAInfo;
import net.rim.device.api.system.CoverageInfo;
import net.rim.device.api.system.DeviceInfo;
import net.rim.device.api.system.GPRSInfo;
import net.rim.device.api.system.RadioInfo;
import net.rim.device.api.system.WLANInfo;
import net.rim.device.api.util.DataBuffer;

public class ConnectionFectory {

	private static final int CONFIG_TYPE_WAP = 0;
	private static final int CONFIG_TYPE_BES = 1;
	private static final int CONFIG_TYPE_WIFI = 3;
	private static final int CONFIG_TYPE_BIS = 4;
	private static final int CONFIG_TYPE_WAP2 = 7;
	private static String TIME_OUT = "30000";

	public static HttpConnection createConnection(String url, byte[] data) throws IOException {

		int[] possibleConnectionList = getPossibleConnectionList();

		if (possibleConnectionList.length == 0) {
			throw new IOException("Connection not found.");
		}
		int connectionType = possibleConnectionList[0];

		switch (connectionType) {
		case ConnectionType.WIFI:
			url = url + ";interface=wifi;deviceside=true";
			break;
		case ConnectionType.BIS:
			break;
		case ConnectionType.BES:
			url = url + ";deviceside=false";
			break;
		case ConnectionType.WAP2:
			ServiceRecord record = getServiceRecord(ConnectionType.WAP2);
			if (record != null) {
				url = url + ";deviceside=true;ConnectionUID=" + record.getUid();
			}
			break;
		case ConnectionType.TCP_IP:
			url = url + ";deviceside=true";
			break;
		case ConnectionType.WAP:
			// url = url + ";deviceside=false";
			break;
		case ConnectionType.UNITE:
			// url = url + ";deviceside=false";
			break;
		}

		url = url + ";ConnectionTimeout=" + TIME_OUT;
		System.out.println("connection url " + url);

		HttpConnection connection = (HttpConnection) Connector.open(url);

		if (data == null) {
			connection.setRequestMethod(HttpConnection.GET);
			// conn.setRequestProperty("User-Agent",
			// "Profile/MIDP-2.0 Configuration/CLDC-1.0");
		} else {
			connection.setRequestMethod(HttpConnection.POST);
			/*
			 * conn.setRequestProperty(
			 * HttpProtocolConstants.HEADER_CONTENT_LENGTH, String
			 * .valueOf(postData.length));
			 * conn.setRequestProperty("Content-Type",
			 * "application/x-www-form-urlencoded");
			 */
			connection.setRequestProperty("Content-Length", Integer.toString(data.length));

			connection.setRequestProperty("Content-Type", "application/binary");
			// conn.setRequestProperty("User-Agent",
			// "Profile/MIDP-2.0 Configuration/CLDC-1.0");

			OutputStream out = connection.openOutputStream();
			out.write(data);
			out.flush();
		}
		return connection;
	}

	public static ServiceRecord getServiceRecord(int connectionType) {

		ServiceBook sb = ServiceBook.getSB();
		ServiceRecord[] records = sb.getRecords();

		for (int i = 0; i < records.length; i++) {
			ServiceRecord myRecord = records[i];
			String cid, uid;

			if (myRecord.isValid() && !myRecord.isDisabled()) {
				cid = myRecord.getCid().toLowerCase();
				uid = myRecord.getUid().toLowerCase();

				switch (connectionType) {

				// BIS
				case ConnectionType.BIS:
					if (cid.indexOf("ippp") != -1 && uid.indexOf("gpmds") != -1) {
						return myRecord;
					}

					// BES
				case ConnectionType.BES:
					if (cid.indexOf("ippp") != -1 && uid.indexOf("gpmds") == -1) {
						return myRecord;
					}
					// WiFi
				case ConnectionType.WIFI:
					if (cid.indexOf("wptcp") != -1 && uid.indexOf("wifi") != -1) {
						return myRecord;
					}
					// Wap1.0
				case ConnectionType.WAP:
					if (getConfigType(myRecord) == CONFIG_TYPE_WAP && cid.equalsIgnoreCase("wap")) {
						return myRecord;
					}
					// Wap2.0
				case ConnectionType.WAP2:
					if (cid.indexOf("wptcp") != -1 && uid.indexOf("wifi") == -1 && uid.indexOf("mms") == -1) {
						return myRecord;
					}
					// Unite
					// case ConnectionType.UNITE:
					// if (getConfigType(myRecord) == CONFIG_TYPE_BES
					// && myRecord.getName().equals(UNITE_NAME)) {
					// return myRecord;
					// }
				}
			}
		}
		return null;
	}

	/**
	 * Gets the config type of a ServiceRecord using getDataInt below
	 * 
	 * @param record
	 *            A ServiceRecord
	 * @return configType of the ServiceRecord
	 */
	private static int getConfigType(ServiceRecord record) {
		return getDataInt(record, 12);
	}

	/**
	 * Gets the config type of a ServiceRecord. Passing 12 as type returns the
	 * configType.
	 * 
	 * @param record
	 *            A ServiceRecord
	 * @param type
	 *            dataType
	 * @return configType
	 */
	private static int getDataInt(ServiceRecord record, int type) {
		DataBuffer buffer = null;
		buffer = getDataBuffer(record, type);

		if (buffer != null) {
			try {
				return ConverterUtilities.readInt(buffer);
			} catch (Throwable e) {
				return -1;
			}
		}
		return -1;
	}

	/**
	 * Utility Method for getDataInt()
	 */
	private static DataBuffer getDataBuffer(ServiceRecord record, int type) {
		byte[] data = record.getApplicationData();
		if (data != null) {
			DataBuffer buffer = new DataBuffer(data, 0, data.length, true);
			try {
				buffer.readByte();
			} catch (Throwable e1) {
				return null;
			}
			if (ConverterUtilities.findType(buffer, type)) {
				return buffer;
			}
		}
		return null;
	}

	public static int[] getPossibleConnectionList() {
		boolean coverageBIS = false;
		boolean coverageTCP = false;
		boolean coverageWAP = false;
		boolean coverageWAP2 = false;
		boolean coverageUnite = false;
		boolean coverageBES = false;
		boolean coverageWiFi = false;

		int connnectionCount = 0;

		if (DeviceInfo.isSimulator()) {
			return new int[] { ConnectionType.TCP_IP };
		} else if (!CoverageInfo.isOutOfCoverage()) {

			if (WLANInfo.getWLANState() == WLANInfo.WLAN_STATE_CONNECTED) {
				coverageWiFi = true;
				connnectionCount++;
			}

			// if
			// (CoverageInfo.isCoverageSufficient(CoverageInfo.COVERAGE_BIS_B))
			// {
			// coverageBIS = true;
			// connnectionCount++;
			// }

			if (CoverageInfo.isCoverageSufficient(CoverageInfo.COVERAGE_DIRECT)) {
				coverageTCP = true;
				connnectionCount++;
				coverageWAP = true;
				connnectionCount++;
				coverageWAP2 = true;
				connnectionCount++;
			}

			if (CoverageInfo.isCoverageSufficient(CoverageInfo.COVERAGE_MDS)) {
				coverageBES = true;
				connnectionCount++;
				coverageUnite = true;
				connnectionCount++;
			}
		}

		int index = 0;
		int connectionArray[] = new int[connnectionCount];

		if (DeviceInfo.isSimulator()) {
			if (coverageTCP) {
				connectionArray[index] = ConnectionType.TCP_IP;
				index++;
			}
		} else {
			if (coverageWiFi) {
				connectionArray[index] = ConnectionType.WIFI;
				index++;
			}

			// if (coverageBIS) {
			// connectionArray[index] = ConnectionType.BIS;
			// index++;
			// }

			if (coverageBES) {
				connectionArray[index] = ConnectionType.BES;
				index++;
			}

			if (coverageTCP) {
				connectionArray[index] = ConnectionType.TCP_IP;
				index++;
			}

			if (coverageWAP2) {
				connectionArray[index] = ConnectionType.WAP2;
				index++;
			}

			if (coverageWAP) {
				connectionArray[index] = ConnectionType.WAP;
				index++;
			}

			if (coverageUnite) {
				connectionArray[index] = ConnectionType.UNITE;
				index++;
			}

		}

		return connectionArray;
	}

	/**
	 * Populates the carrier information.
	 */
	private void populateCarrierInfo() {
		final String carrierName = RadioInfo.getCurrentNetworkName();
		try {
			int mcc;
			int mnc;
			if (RadioInfo.getNetworkType() == RadioInfo.NETWORK_CDMA) {
				String imsi = GPRSInfo.imeiToString(CDMAInfo.getIMSI());
				mcc = Integer.parseInt(imsi.substring(0, 3));
				mnc = Integer.parseInt(imsi.substring(3, 6));
			} else if (RadioInfo.getNetworkType() == RadioInfo.NETWORK_UMTS
					|| RadioInfo.getNetworkType() == RadioInfo.NETWORK_GPRS) {
				mcc = Integer.parseInt(Integer.toHexString(GPRSInfo.getHomeMCC()));
				mnc = Integer.parseInt(Integer.toHexString(GPRSInfo.getHomeMNC()));
			}
		} catch (NullPointerException npe) {

		} catch (NumberFormatException ne) {

		} catch (Throwable t) {
		}

	}

}
