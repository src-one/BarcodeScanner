package com.src_one.barcodescanner;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    private IntentIntegrator qrScan;
    Repository repository = new Repository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        qrScan = new IntentIntegrator(this);
        qrScan.initiateScan();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                showToast("Result not found");
            } else {
                showToast(result.getContents());

                qrScan.initiateScan();

                BarcodeParams params = new BarcodeParams(result.getFormatName(), result.getContents());
                SubmitBarcodeTask task = new SubmitBarcodeTask();
                task.execute(params);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    private static class BarcodeParams {
        String type;
        String value;

        BarcodeParams(String type, String value) {
            this.type = type;
            this.value = value;
        }
    }

    private class SubmitBarcodeTask extends AsyncTask<BarcodeParams, Void, String> {
        @Override
        protected String doInBackground(BarcodeParams... params) {
            return repository.submitBarcode(params[0].type, params[0].value);
        }

        @Override
        protected void onPostExecute(String result) {
            showToast(result);
        }
    }
}
