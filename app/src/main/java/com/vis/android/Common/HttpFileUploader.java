package com.vis.android.Common;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//import org.apache.commons.codec.binary.Base64;

public class HttpFileUploader implements Runnable {

    private static String s;
    URL connectURL;
    String mediaType;
    String responseString;
    String userId;
    String Type;
    //long FeedId;
    // InterfaceHttpUtil ifPostBack;
    String fileName;
    byte[] dataToServer;
    FileInputStream fileInputStream = null;

    //public HttpFileUploader(String urlString, String mediaType, String fileName, String userid/*, long Feedid*/,String type, String upload_type) {
    public HttpFileUploader(String urlString, String fileName) {

    //    loader.show();

        try {

            //	connectURL = new URL(urlString +"/"+userid+"/"+upload_type /*+ "&id="+ userid + "&media=" + mediaType+"&type="+upload_type+"&uploadedfile="+"100.png"+"&Submit=Submit"*/);
            //connectURL = new URL(urlString +""+fileName /*+ "&id="+ userid + "&media=" + mediaType+"&type="+upload_type+"&uploadedfile="+"100.png"+"&Submit=Submit"*/);
            connectURL = new URL(urlString  /*+""+fileName+ "&id="+ userid + "&media=" + mediaType+"&type="+upload_type+"&uploadedfile="+"100.png"+"&Submit=Submit"*/);

            Log.v("final url", connectURL.toString());

        } catch (Exception ex) {
            Log.i("URL FORMATION", "MALFORMATED URL");
        }

        this.mediaType = mediaType;
        this.fileName = fileName;
        //	this.userId = userid;
        //	this.Type = type;
        //this.FeedId = Feedid;

    }

    public static String updateCall() {
        String st = s;
        s = "";
        return st;

    }

    public void doStart(FileInputStream stream) {
        fileInputStream = stream;
        thirdTry();
    }

    void thirdTry() {

        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        String Tag = "Uploading Document";
        try {
            // ------------------ CLIENT REQUEST

            Log.e(Tag, "Starting to bad things");
            // Open a HTTP connection to the URL

            HttpURLConnection conn = (HttpURLConnection) connectURL.openConnection();

            // Allow Inputs
            conn.setDoInput(true);

            // Allow Outputs
            conn.setDoOutput(true);

            // Don't use a cached copy.
            conn.setUseCaches(false);

            // Use a post method.
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Connection", "Keep-Alive");

            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

            dos.writeBytes(twoHyphens + boundary + lineEnd);

		/*	dos.writeBytes("Content-Disposition: form-data; name=\"upload_file\"; " +
					"media=\""+ mediaType+ "\"; uuid=\""+ userId+ "\";type=\""+ Type+ "\";" +
					"filename=\"" + fileName + "\"" + lineEnd);*/

            dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\"; " +
                    "filename=\"" + fileName + "\"" + lineEnd);

          /*  dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\"" +
                    ";filename=\"" + fileName + "\"" + lineEnd);*/

            dos.writeBytes(lineEnd);

            //dos.writeBytes(lineEnd);

            Log.e(Tag, "Headers are written");

            // create a buffer of maximum size

            int bytesAvailable = fileInputStream.available();
            int maxBufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);
            byte[] buffer = new byte[bufferSize];

            // read file and write it into form...

            int bytesRead = fileInputStream.read(buffer, 0, bufferSize);


            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            // send multipart form data necesssary after file data...

            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // close streams
            Log.e(Tag, "File is written");
            fileInputStream.close();
            dos.flush();

            InputStream is = conn.getInputStream();
            // retrieve the response from server
            int ch;

            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
            s = b.toString();

            Log.i("Media Uploading", s);

            dos.close();

        } catch (MalformedURLException ex) {
            Log.e(Tag, "error: " + ex.getMessage(), ex);
        } catch (IOException ioe) {
            Log.e(Tag, "error: " + ioe.getMessage(), ioe);
        }

    }

    @Override
    public void run() {

    }
}