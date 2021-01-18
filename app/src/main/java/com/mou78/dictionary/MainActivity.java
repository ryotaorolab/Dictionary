package com.mou78.dictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> adapter;

    EditText wordEditText;
    EditText meanEditText;
    EditText searchWordEditText;

    HashMap<String, String> hashMap;
    TreeSet<String> wordSet;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        wordEditText = (EditText) findViewById(R.id.word);
        meanEditText = (EditText) findViewById(R.id.mean);
        searchWordEditText = (EditText) findViewById(R.id.searchWord);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        this.hashMap = new HashMap<>();
        wordSet = new TreeSet<String>();
        pref = getSharedPreferences("dictionary", MODE_PRIVATE);
        editor = pref.edit();

        editor.putString("technology", "科学技術");
        editor.putString("develop", "開発する");
        editor.commit();

        wordSet.add("technology");
        wordSet.add("develop");

        //Setに保存した英語を取得
        wordSet.addAll(pref.getStringSet("wordSet", wordSet));
        //Mapに追加
        for (String word : wordSet) {
            //SharedPreferences内の日本語を取得
            this.hashMap.put(word, pref.getString(word, null));

            //adapterに追加
            adapter.add(" 【" + word + "】" + pref.getString(word, null));
        }
        //ListViewに表示
        listView.setAdapter(adapter);
    }



        //削除機能作り中　ここから

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ArrayAdapter adapter = (ArrayAdapter) listView.getAdapter();
//
//                String item = (String) adapter.getItem(position);
//                adapter.remove(item);

                //Test
//                String test = (String) adapter.getItem(position);
//                Toast.makeText(getApplicationContext(),test,Toast.LENGTH_SHORT).show();
                //Test終わり

//                SharedPreferences pref= getSharedPreferences("dictionary", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = pref.edit();
//                editor.remove("wordSet");
//                editor.commit();
//            }
//        });
//        //ここまで

    //テストコードの実行ボタン

    public void testbutton(View v){
        String deleteword = searchWordEditText.getText().toString();

        if (wordSet.contains(deleteword)) { //入力した文字が保存されているか
            wordSet.remove(deleteword);
            editor.remove(deleteword);
            editor.putStringSet("wordSet", wordSet);
            editor.commit();
            Intent intent = new Intent(getApplication(),MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "削除しました", Toast.LENGTH_LONG).show();
        } if (deleteword.contains("1111")) { //1111と入力されていたら全て消去する
//            wordSet.clear();
            editor.clear();
//            hashMap.clear();
            editor.commit();
            Intent intent = new Intent(getApplication(),MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "全て消しました", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "該当する単語が見つかりません", Toast.LENGTH_LONG).show();
        }
    }

    public void add(View v) {
        String entryWord = wordEditText.getText().toString();
        String entryMean = meanEditText.getText().toString();
        String entryObject =" 【" + entryWord + "】" + entryMean;

        //Setに入力した英単語をKeyとして保存
        wordSet.add(entryWord);
        //ShardPreferencesに日本語を追加
        editor.putString(entryWord, entryMean);
        //ShardPreferencesにSetの値(英語)を追加
        editor.putStringSet("wordSet", wordSet);

        editor.commit();
        adapter.add(entryObject);
    }

    public void search(View v) {
        String searchWord = searchWordEditText.getText().toString();

        //Setに保存した英単語を取得
        wordSet.addAll(pref.getStringSet("wordSet", wordSet));
        //検索機能のためMapに追加
        for (String word : wordSet) {
            hashMap.put(word, pref.getString(word, null));
        }

        if (hashMap.containsKey(searchWord)) {
            Toast.makeText(this, hashMap.get(searchWord), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "この単語は登録されていません", Toast.LENGTH_LONG).show();
        }
    }
}