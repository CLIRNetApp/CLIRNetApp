package app.clirnet.com.clirnetapp.logfilesending;

import android.content.Context;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import app.clirnet.com.clirnetapp.app.AppConfig;
import app.clirnet.com.clirnetapp.app.AppController;
import app.clirnet.com.clirnetapp.helper.SQLiteHandler;
import app.clirnet.com.clirnetapp.utility.UploadProgressListener;

/**
 * Created by Ashish on 12/19/2016.
 */
public class LogFileAsyncTask {

    private final String mPassword;
    private final String mUsername;
    private final Context mContext;
    private final String imeiNo;
    private String logPath;
    private String logName;
    private String start_time;
    private String docMemId;
    private String docId;

    public LogFileAsyncTask(Context context, String username, String password,String docMemId, String docId,String start_time) {

        this.mPassword = password;
        this.mUsername = username;
        this.start_time = start_time;
         mContext = context;
        this.docMemId=docMemId;
        this.docId=docId;

        logPath = "sdcard/PatientsImages/log.txt";

        // IMAGE NAME
        logName = "log.txt";
        TelephonyManager manager = (TelephonyManager)mContext.getSystemService(Context.TELEPHONY_SERVICE);
        imeiNo = manager.getDeviceId();

       //  long fileSize = this.getFileSize(logPath);
        new FileUploader().execute();

    }

    private class FileUploader extends AsyncTask<Void, Integer, Boolean> implements UploadProgressListener {

        @Override
        protected Boolean doInBackground(Void... params) {

            try {

                InputStream inputStream = new FileInputStream(new File(logPath));

                //*** CONVERT INPUTSTREAM TO BYTE ARRAY

                byte[] data = this.convertToByteArray(inputStream);

                HttpClient httpClient = new DefaultHttpClient();
                httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, System.getProperty("http.agent"));

                // HttpPost httpPost = new HttpPost(AppConfig.UPLOAD_LOGFILE_TOSERVER);
                HttpPost httpPost = new HttpPost(AppConfig.UPLOAD_LOG_FILE);

                // STRING DATA
                StringBody apikey = new StringBody("PFFt0436yjfn0945DevOp0958732Cons3214556");
                StringBody passord = new StringBody(mPassword);
                StringBody username = new StringBody(mUsername);
                StringBody dataString = new StringBody("simple log file");
                StringBody membershipid = new StringBody(docMemId);
                StringBody docid = new StringBody(docId);
                StringBody imei_no=new StringBody(imeiNo);

                // FILE DATA OR IMAGE DATA
                InputStreamBody inputStreamBody = new InputStreamBody(new ByteArrayInputStream(data), logName);

                // MultipartEntity multipartEntity = new MultipartEntity();
                CustomMultiPartEntity multipartEntity = new CustomMultiPartEntity();

                // SET UPLOAD LISTENER
                multipartEntity.setUploadProgressListener(this);

                //*** ADD THE FILE
                multipartEntity.addPart("file", inputStreamBody);

                //*** ADD STRING DATA
                multipartEntity.addPart("description", dataString);
                multipartEntity.addPart("username", username);
                multipartEntity.addPart("password", passord);
                multipartEntity.addPart("apikey", apikey);
                multipartEntity.addPart("membershipid", membershipid);
                multipartEntity.addPart("docId",docid);
                multipartEntity.addPart("imei_no",imei_no);

                httpPost.setEntity(multipartEntity);
                httpPost.setEntity(multipartEntity);

                // EXECUTE HTTPPOST
               // HttpResponse httpResponse = httpClient.execute(httpPost);
                // THE RESPONSE FROM SERVER
              //  String stringResponse = EntityUtils.toString(httpResponse.getEntity());
            //    Log.e("stringResponse"," "+stringResponse);


            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
                new AppController().appendLog(new AppController().getDateTime() + " " + "/ " + "Log File Async Task : Messaage: " + e1);

                return false;

            } catch (ClientProtocolException e) {

                e.printStackTrace();
                new AppController().appendLog(new AppController().getDateTime() + " " + "/ " + "Log File Async Task : Messaage: " + e);

                return false;

            } catch (IOException e) {
                new AppController().appendLog(new AppController().getDateTime() + " " + "/ " + "Log File Async Task : Messaage: " + e);

                e.printStackTrace();

                return false;
            }

            return true;
        }

        /**
         *
         */
        @Override
        public void transferred(long num) {

            // COMPUTE DATA UPLOADED BY PERCENT

            // long dataUploaded = ((num / 1024) * 100 ) / imageSize;
            long dataUploaded = ((num / 1024) * 100) / 1000;

            // PUBLISH PROGRESS

            this.publishProgress((int) dataUploaded);

        }


        private byte[] convertToByteArray(InputStream inputStream) throws IOException {

            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            int next = inputStream.read();
            while (next > -1) {
                bos.write(next);
                next = inputStream.read();
            }

            bos.flush();

            return bos.toByteArray();
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            // UPDATE THE PROGRESS DIALOG

            // progressDialog.setProgress(values[0]);

        }

        @Override
        protected void onPostExecute(Boolean uploaded) {

            super.onPostExecute(uploaded);
            new AppController().appendLog(new AppController().getDateTime() + " " + "/ " + "Log File Async Task : File Uploaded : " + uploaded);


            if (uploaded) {
                AppController appController = new AppController();
                SQLiteHandler dbController = new SQLiteHandler(mContext);
                String end_time = appController.getDateTimenew();
                dbController.addAsynctascRun_status("Log File Sending", start_time, end_time, "");

            }
        }
    }

    private long getFileSize(String imagePath) {
        long length = 0;

        try {

            File file = new File(imagePath);
            length = file.length();
            length = length / 1024;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return length;
    }

}
