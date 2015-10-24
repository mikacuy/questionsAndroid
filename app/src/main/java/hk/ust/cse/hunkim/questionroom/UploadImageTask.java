package hk.ust.cse.hunkim.questionroom;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadImageTask extends AsyncTask<String, Void, String> {
    private static String crlf = "\r\n";
    private static String twoHyphens = "--";
    private String boundary =  "*****";
    private String imagePath, fileName, responseURL;
    private Bitmap b;
    private final static int IMAGE_MAX_HEIGHT = 1200;
    private final static int IMAGE_MAX_WIDTH = 1600;
    private final static int quality = 75;

    // http://stackoverflow.com/a/3549021
    private Bitmap compressFile(String imagePath){
        //Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, o);

        int scale = 1;
        if (o.outHeight > o.outWidth  && o.outHeight > IMAGE_MAX_HEIGHT) {
            scale = (int)Math.pow(2, (int) Math.ceil(Math.log(IMAGE_MAX_HEIGHT /
                    (double)o.outHeight) / Math.log(0.5)));
        }
        else if (o.outWidth > IMAGE_MAX_WIDTH) {
            scale = (int)Math.pow(2, (int) Math.ceil(Math.log(IMAGE_MAX_WIDTH /
                    (double)o.outWidth) / Math.log(0.5)));
        }

        //Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        b = BitmapFactory.decodeFile(imagePath, o2);

        return b;
    }

    @Override
    protected String doInBackground(String... params) {
        imagePath = params[0];
        fileName = params[1];
        boundary += Long.toString(System.currentTimeMillis());

        // Compress the Image to reduce image size to make upload easy
        Bitmap myImg = compressFile(imagePath);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        myImg.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        byte[] byte_arr = stream.toByteArray();

        try {
            // http://stackoverflow.com/a/11826317

            // Setup HTTPURLConnection
            URL url = new URL("http://questions-backend.herokuapp.com/api/uploadphoto");
            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
            httpUrlConnection.setRequestProperty("Cache-Control", "no-cache");
            httpUrlConnection.setRequestProperty(
                    "Content-Type", "multipart/form-data;boundary=" + boundary);

            // Start content wrapper:
            DataOutputStream request = new DataOutputStream(
                    httpUrlConnection.getOutputStream());

            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"userPhoto\";"
                    + "filename=\"" + fileName + "\"" + crlf);
            request.writeBytes(crlf);

            // Send main data
            request.write(byte_arr);
            request.writeBytes(crlf);

            // End content wrapper:
            request.writeBytes(twoHyphens + boundary + twoHyphens + crlf);

            //Flush output buffer:
            request.flush();
            request.close();

            //Get responseURL:
            InputStream responseStream = new
                    BufferedInputStream(httpUrlConnection.getInputStream());

            BufferedReader responseStreamReader =
                    new BufferedReader(new InputStreamReader(responseStream));

            String line = "";
            StringBuilder responseStringBuilder = new StringBuilder();

            while ((line = responseStreamReader.readLine()) != null) {
                responseStringBuilder.append(line).append("\n");
            }
            //Close the connection and streams:
            responseStreamReader.close();
            responseStream.close();
            httpUrlConnection.disconnect();

            responseURL = (new JSONObject(responseStringBuilder.toString())).getString("Filepath");
            // TODO: REMOVE Debug messages;
            Log.d("TEST", responseURL);
        } catch (Exception e) {
            // TODO: Fix Exception handling;
            Log.e("TEST", "CAUGHT EXCEPTION", e);
            responseURL = null;
        } finally {
            return responseURL;
        }
    }
}
