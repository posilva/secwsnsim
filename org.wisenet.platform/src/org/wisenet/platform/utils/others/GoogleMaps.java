/*
 *  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.platform.utils.others;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

@SuppressWarnings("unchecked")
public class GoogleMaps {

    public static final String GOOGLE_GEOCODE_URL = "http://maps.google.com/maps/geo";
    public static final String GOOGLE_STATIC_MAP_URL = "http://maps.google.com/staticmap";
    private static final String URL_UNRESERVED =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "abcdefghijklmnopqrstuvwxyz"
            + "0123456789-_.~";
    private static final char[] HEX = "0123456789ABCDEF".toCharArray();
    // these 2 properties will be used with map scrolling methods. You can remove them if not needed
    public static final int offset = 268435456;
    public static final double radius = offset / Math.PI;
    private static String apiKey = null;

    /**
     * 
     * @return
     */
    public static String getApiKey() {
        return apiKey;
    }

    /**
     * 
     * @param apiKey
     */
    public static void setApiKey(String apiKey) {
        GoogleMaps.apiKey = apiKey;
    }

    /**
     * 
     * @param key
     */
    public GoogleMaps(String key) {
        apiKey = key;
    }

    /**
     * Get the geoCode search by address criteria
     * @param address
     * @return
     * @throws Exception
     */
    public static double[] geocodeAddress(String address) throws Exception {
        byte[] res = loadHttpFile(getGeocodeUrl(address));
        String[] data = (new String(res)).split(",");
        if (!data[0].equals("200")) {
            int errorCode = Integer.parseInt(data[0]);
            throw new Exception("Google Maps Exception: " + getGeocodeError(errorCode));
        }

        return new double[]{
                    Double.parseDouble(data[2]), Double.parseDouble(data[3])
                };
    }

    /**
     * 
     * @param width
     * @param height
     * @param lat
     * @param lng
     * @param zoom
     * @param format
     * @return
     * @throws Exception
     */
    public static Image retrieveStaticImage(int width, int height, double lat, double lng, int zoom, String format) throws Exception {
        String url = getMapUrl(width, height, lng, lat, zoom, format);
        byte[] imageData = loadHttpFile(url);
        return Toolkit.getDefaultToolkit().createImage(imageData);
    }

    private static String getGeocodeError(int errorCode) {
        switch (errorCode) {
            case 400:
                return "Bad request";
            case 500:
                return "Server error";
            case 601:
                return "Missing query";
            case 602:
                return "Unknown address";
            case 603:
                return "Unavailable address";
            case 604:
                return "Unknown directions";
            case 610:
                return "Bad API key";
            case 620:
                return "Too many queries";
            default:
                return "Generic error";
        }
    }

    private static String getGeocodeUrl(String address) {
        return GOOGLE_GEOCODE_URL + "?q=" + urlEncode(address) + "&output=csv&key=" + apiKey;
    }

    private static String getMapUrl(int width, int height, double lng, double lat, int zoom, String format) {
        return GOOGLE_STATIC_MAP_URL + "?center=" + lat + "," + lng + "&format="
                + format + "&zoom=" + zoom + "&size=" + width + "x" + height + "&maptype=satellite" + "&key=" + apiKey;
    }

    private static String urlEncode(String str) {
        StringBuilder buf = new StringBuilder();
        byte[] bytes = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);
            dos.writeUTF(str);
            bytes = bos.toByteArray();
        } catch (IOException e) {
            // ignore
        }
        for (int i = 2; i < bytes.length; i++) {
            byte b = bytes[i];
            if (URL_UNRESERVED.indexOf(b) >= 0) {
                buf.append((char) b);
            } else {
                buf.append('%').append(HEX[(b >> 4) & 0x0f]).append(HEX[b & 0x0f]);
            }
        }
        return buf.toString();
    }

    public static void readFromUrlToFile(String url, String file) throws Exception {
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(readFromUrlToByteArray(url));
        fos.close();
    }

    private static byte[] readFromUrlToByteArray(String url) throws Exception {
        URL u = new URL(url);
        BufferedInputStream in = new BufferedInputStream(u.openStream());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte buffer[] = new byte[1024];
        int i;
        while ((i = in.read(buffer)) != -1) {
            baos.write(buffer, 0, i);
        }
        return baos.toByteArray();
    }

    private static byte[] loadHttpFile(String url) throws Exception {
        return readFromUrlToByteArray(url);
    }
}
