package jobninja.eu.apksender;

/**
 * Created by laurentmeyer on 15/12/15.
 */

import android.provider.MediaStore;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpPost;
import com.koushikdutta.async.http.body.MultipartFormDataBody;

import java.io.File;
import java.util.UUID;

import jobninja.eu.apkgetter.Main;

public class ApkSender {

    private static ApkSender instance = null;

    public static ApkSender getInstance() {
        if (instance == null) {
            instance = new ApkSender();
        }
        return instance;
    }

    /**
     * Used to send the apk alone (mainly for test purposes), I doubt it could be needed in the real world
     *
     * @param p
     * @param callback
     */
    public void sendAPK(Main.Package p, AsyncHttpClient.StringCallback callback) {
        String serverAddress = "131.159.216.187";
        AsyncHttpPost post = new AsyncHttpPost("http://" + serverAddress + ":5000/upload");
        MultipartFormDataBody body = new MultipartFormDataBody();
        body.addFilePart("Apk", new File(p.getPath()));
        body.addStringPart("sid", UUID.randomUUID().toString());
        post.setBody(body);
        AsyncHttpClient.getDefaultInstance().executeString(post, callback);
    }

    public void sendFiles(String pathToSources, String pathToSinks, String pathToTaintWrapper, Main.Package apk, AsyncHttpClient.StringCallback callback) {
        String serverAddress = "131.159.216.187";
        AsyncHttpPost post = new AsyncHttpPost("http://" + serverAddress + ":5000/upload");
        MultipartFormDataBody body = new MultipartFormDataBody();
        if (checkPath(apk.getPath()))
            body.addFilePart("Apk", new File(apk.getPath()));
        // TODO: Make it real one way or another
        body.addStringPart("sid", UUID.randomUUID().toString());
        if (checkPath(pathToSources))
            body.addFilePart("Source", new File(pathToSources));
        if (checkPath(pathToSinks))
            body.addFilePart("Sinks", new File(pathToSinks));
        if (checkPath(pathToTaintWrapper))
            body.addFilePart("TaintWrapper", new File(pathToTaintWrapper));
        AsyncHttpClient.getDefaultInstance().executeString(post, callback);
    }

    private boolean checkPath(String path) {
        return new File(path).exists();
    }


}
