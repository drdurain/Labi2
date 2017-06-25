package innovation.labi2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;



public class MainActivity extends Activity{



    private EditText editText;
    private Button btn_Search;
    private ListView listView;
    private ArrayList<Poster> dbList = new ArrayList<>();
    private ArrayList<Poster> resultList = new ArrayList<>();
    private ArrayList<String> titleList = new ArrayList<>();
    private BufferedReader br;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.suchText);
        btn_Search = (Button) findViewById(R.id.btn_search);
        listView = (ListView) findViewById(R.id.listView_results);

        fillDB();
        btn_Search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                searchPosters();
            }
        });


    }

    private void fillDB() {
        try {
            br = new BufferedReader(new InputStreamReader(getAssets().open("DataWithTilde.txt")));

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                Log.e("Reader stuff", sCurrentLine);
                String[] strArr = sCurrentLine.split("\\~");
                dbList.add(new Poster(strArr[0], strArr[1], strArr[2], strArr[3], strArr[4], strArr[5], strArr[6], strArr[7]));
            }

        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
        finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    private void searchPosters() {

        String searchTxt = editText.getText().toString();


        // Fill listView with matches if available (using the ArrayAdapter instead of the CursorAdapter since working with a list)
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titleList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Set the text size in ListView for each item
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,40);
                tv.setSingleLine(true);
                tv.setMaxLines(1);
                tv.setHorizontallyScrolling(true);
                tv.setHorizontalScrollBarEnabled(true);
                tv.setTypeface(Typeface.MONOSPACE);
                //tv.getLayoutParams().width = ConstraintLayout.LayoutParams.WRAP_CONTENT;
                //tv.setEllipsize(MARQUEE);
                //tv.setMarqueeRepeatLimit(1000);


                // Return the view
                return view;
            }
        };
        adapter.clear();
        adapter.notifyDataSetChanged();
        resultList.clear();


        // Search for matches
        for (int i = 0; i < dbList.size(); i++) {
            if (dbList.get(i).getTitel().toLowerCase().contains(searchTxt.toLowerCase())) {
                resultList.add(dbList.get(i));
                titleList.add(dbList.get(i).getTitel());
            }
        }

        titleList = expandListRow(titleList);
        listView.setAdapter(adapter);

        // Hide keyboard after pressing Suchen
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != this.getCurrentFocus()) {
            imm.hideSoftInputFromWindow(this.getCurrentFocus().getApplicationWindowToken(), 0);
        }


        // React on selections in the listView
         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(),DisplayActivity.class);
                intent.putExtra("result", resultList.get(position));
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Click ListItem Number " + position, Toast.LENGTH_LONG).show();
            }
        });


    }



    public ArrayList<String> expandListRow(ArrayList<String> list) {

        int rowSize = 0;
        String rowContent = "";
        String blankFill = "";

        for (int i = 0; i < list.size(); i++) {
            if (rowSize < list.get(i).length()) {
                rowSize = list.get(i).length();
            }
        }

        if (rowSize > list.get(0).length()) {

            for (int i = 0; i < (rowSize-list.get(0).length()); i++) {
                blankFill += " ";
            }

            rowContent = list.get(0) + blankFill;
            list.set(0,rowContent);
        }

        return list;
    }


}
