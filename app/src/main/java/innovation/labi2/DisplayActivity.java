package innovation.labi2;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class DisplayActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

    private TextToSpeech tts;
    private Poster poster;
    private ImageView imageView;
    private WebView webView;
    private Button speakBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        imageView = (ImageView) findViewById(R.id.imageView);
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        speakBtn = (Button) findViewById(R.id.speakBtn);


    }

    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();
        Intent intent = getIntent();

        tts = new TextToSpeech(DisplayActivity.this, DisplayActivity.this);

        /*
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent event) {
                speakIt();
                return false;
            }
        });
        */


        speakBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                speakIt();
            }
        });



        poster = (Poster) intent.getExtras().getSerializable("result");
        System.out.println(findViewById(R.id.imageView));
        webView.loadUrl(poster.getUrl());
        new DownloadImageTask((ImageView) findViewById(R.id.imageView)).execute(poster.getUrl());
    }



    public void onInit(int status) {

        Log.e("text2speech", tts.toString());
        System.out.println("text2speech " + tts.toString());

        if(status == TextToSpeech.SUCCESS)
        {
            int result = tts.setLanguage(Locale.GERMANY);
            //tts.setSpeechRate(3); //speed rate

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
            {
                Log.e("tts", "language is not supported");
            }
            else
            {
                speakBtn.setEnabled(true);
            }
        }
        else
        {
            Log.e("tts", "init failed");
        }
    }




    @Override
    public void onDestroy() {
        if(tts != null)
        {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }


    private void speakIt() {
        String txt = poster.getInhalt();
        System.out.println("SPEAK IT " + txt);
        tts.speak(txt, TextToSpeech.QUEUE_FLUSH, null);
    }





    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            InputStream in = null;
            try {
                in = new java.net.URL(urldisplay).openStream();
                System.out.println("?????????????????????????");
                mIcon11 = BitmapFactory.decodeStream(in);
                System.out.println(mIcon11.getByteCount());
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            finally {
                if(in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            System.out.println(result.getByteCount());
            System.out.println(bmImage);
            bmImage.setImageBitmap(result);
        }
    }

}
