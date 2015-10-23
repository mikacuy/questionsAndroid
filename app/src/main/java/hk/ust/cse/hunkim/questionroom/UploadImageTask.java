package hk.ust.cse.hunkim.questionroom;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadImageTask extends AsyncTask<String, Void, String> {
    String crlf = "\r\n";
    String twoHyphens = "--";
    String boundary =  "*****";
    String imagePath, fileName, response;

    @Override
    protected String doInBackground(String... params) {
        imagePath = params[0];
        fileName = params[1];
        boundary += Long.toString(System.currentTimeMillis());
        Bitmap myImg = BitmapFactory.decodeFile(imagePath);

        // Compress the Image to reduce image size to make upload easy
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        myImg.compress(Bitmap.CompressFormat.PNG, 50, stream);
        byte[] byte_arr = stream.toByteArray();

        try {
            // http://stackoverflow.com/questions/11766878/sending-files-using-post-with-httpurlconnection

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

            //Get response:
            InputStream responseStream = new
                    BufferedInputStream(httpUrlConnection.getInputStream());

            BufferedReader responseStreamReader =
                    new BufferedReader(new InputStreamReader(responseStream));

            String line = "";
            StringBuilder stringBuilder = new StringBuilder();

            while ((line = responseStreamReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            //Close the connection and streams:
            responseStreamReader.close();
            responseStream.close();
            httpUrlConnection.disconnect();

            response = stringBuilder.toString();
        } catch (java.net.MalformedURLException e) {
            Log.e("TEST", "CAUGHT MALFORMED", e);
            response = null;
        } catch (java.io.IOException e) {
            Log.e("TEST", "CAUGHT IO", e);
            response = null;
        } finally {
            return response;
        }
    }
}
