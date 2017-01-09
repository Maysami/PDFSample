package com.fanavartech.android.pdfsample;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

import java.io.File;


public class DownloadFile extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ProgressBar  progressBar = (ProgressBar) findViewById(R.id.progress_download_pdf);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);


        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + "Android/data/com.taneem.reader/files/pdf/");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        if (success) {
            // Do something on success

            Ion.with(this)
                    .load("http://s8.picofile.com/d/8281817592/b6f8789a-6c10-4bab-b99c-a707b07f9ff1/%D8%AA%D9%86%D8%B9%DB%8C%D9%85_1.pdf")
                    // have a ProgressBar get updated automatically with the percent
                    .progressBar(progressBar)
                    // and a ProgressDialog
                    .progressDialog(progressDialog)
                    // can also use a custom callback
                    .progress(new ProgressCallback() {@Override
                    public void onProgress(long downloaded, long total) {
                        System.out.println("" + downloaded + " / " + total);
                    }
                    })
                    .write(new File(folder+"/%D8%AA%D9%86%D8%B9%DB%8C%D9%85_1.pdf"))
                    .setCallback(new FutureCallback<File>() {
                        @Override
                        public void onCompleted(Exception e, File file) {
                            // download done...
                            // do stuff with the File or error

                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            // set the content type and data of the intent
                            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            // start the intent as a new activity
                            startActivity(intent);
                        }
                    });

        } else {
            // Do something else on failure
        }

    }
}
