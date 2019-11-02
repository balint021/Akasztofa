package com.example.akasztofa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private String[] szavakTomb = {"Dishonored","coffee","cheese","telephone","Minecraft","computer","Laptop","table","chair","stairs","tree","interesting", "library", "Physical Education"
    ,"apple","fence","career","team","outside","black","station","Kim Jong Un","feature","Alphabet","third-party"};
    private String[] betukTomb = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
    private boolean[] betukSzinei;
    private int betuk = betukTomb.length;
    private char[] kitalalandoSzo;
    private int index = 0;
    private int akasztofaIndex = 0;
    private String _szo;
    private int szoHossza;
    private Button btn_minus;
    private Button btn_plus;
    private Button btn_tippel;
    private ImageView img_akasztofa;
    private TextView txt_betu;
    private TextView txt_akasztofa;
    private AlertDialog.Builder alert;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        betuValtas();
        tippel();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("akasztofaIndex", akasztofaIndex);
        outState.putCharArray("kitalalandoSzo", kitalalandoSzo);
        outState.putInt("index", index);
        outState.putString("_szo", _szo);
        outState.putInt("szoHossza", szoHossza);
        outState.putBooleanArray("betukSzinei", betukSzinei);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        akasztofaIndex = savedInstanceState.getInt("akasztofaIndex");
        kepCsere();
        kitalalandoSzo = savedInstanceState.getCharArray("kitalalandoSzo");
        index = savedInstanceState.getInt("index");
        txt_betu.setText(betukTomb[index].toUpperCase());
        _szo = savedInstanceState.getString("_szo");
        szoHossza = savedInstanceState.getInt("szoHossza");
        txt_akasztofa.setText(kiir(kitalalandoSzo));
        jatekVege();
        betukSzinei = savedInstanceState.getBooleanArray("betukSzinei");
        szinCsere();
    }

    private void init() {
        btn_minus = findViewById(R.id.btn_minus);
        btn_plus = findViewById(R.id.btn_plus);
        btn_tippel = findViewById(R.id.btn_tippel);
        img_akasztofa = findViewById(R.id.img_akasztofa);
        txt_betu = findViewById(R.id.txt_betu);
        txt_betu.setText(betukTomb[index].toUpperCase());
        txt_akasztofa = findViewById(R.id.txt_akasztofa);
        _szo = szavakTomb[new Random().nextInt(szavakTomb.length)].toUpperCase();
        szoHossza = _szo.length();

        kitalalandoSzo = new char[szoHossza];
        betukSzinei = new boolean[betukTomb.length];
        for (boolean b : betukSzinei) {
            b = false;
        }

        for (int i = 0; i < szoHossza; i++){
            if (_szo.charAt(i) == ' ') {
                kitalalandoSzo[i] = ' ';
            }
            if  (_szo.charAt(i) == '-') {
                kitalalandoSzo[i] = '-';
            }
            if (_szo.charAt(i) != ' ' && _szo.charAt(i) != '-') {
                kitalalandoSzo[i] = '_';
            }
        }

        txt_akasztofa.setText(kiir(kitalalandoSzo));
    }

    private void betuValtas() {
        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index == (betuk-1)){
                    index = 0;
                } else if (index != betuk){
                    index++;
                }
                txt_betu.setText(betukTomb[index].toUpperCase());
                szinCsere();
            }
        });
        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index == 0){
                    index = (betuk-1);
                } else if (index != 0){
                    index--;
                }
                txt_betu.setText(betukTomb[index].toUpperCase());
                szinCsere();
            }
        });
    }

    private void tippel() {
        btn_tippel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean helyes = false;
                for (int i = 0; i < kitalalandoSzo.length; i++){
                    if (kitalalandoSzo[i] == '_' && _szo.charAt(i) == txt_betu.getText().charAt(0)){
                        kitalalandoSzo[i] = txt_betu.getText().charAt(0);
                        helyes = true;
                    }

                }
                if (!helyes && !betukSzinei[index]){
                    akasztofaIndex++;
                }
                betukSzinei[index] = true;
                txt_akasztofa.setText(kiir(kitalalandoSzo));
                szinCsere();
                kepCsere();
                jatekVege();
            }
        });
    }

    private void jatekVege() {
        if (akasztofaIndex == 13) {
            alert = new AlertDialog.Builder(this);
            alert.setTitle("Nem sikerult kitalalni!");
            alert.setMessage("Szeretnel meg egyet jatszani?\n" +
                             "A megoldas " + _szo + " volt.");
            alert.setCancelable(false);
            alert.setNegativeButton("Igen", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    restart();
                }
            });
            alert.setPositiveButton("Nem", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.create().show();
        }
        if (new String(kitalalandoSzo).trim().equals(_szo.trim())) {
            alert = new AlertDialog.Builder(this);
            alert.setTitle("Helyes megfejtes!");
            alert.setMessage("Szeretnel meg egyet jatszani?");
            alert.setCancelable(false);
            alert.setNegativeButton("Igen", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    restart();
                }
            });
            alert.setPositiveButton("Nem", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.create().show();
        }




    }

    private void kepCsere() {
        switch (akasztofaIndex){
            case 0: img_akasztofa.setBackgroundResource(R.drawable.akasztofa00); break;
            case 1: img_akasztofa.setBackgroundResource(R.drawable.akasztofa01); break;
            case 2: img_akasztofa.setBackgroundResource(R.drawable.akasztofa02); break;
            case 3: img_akasztofa.setBackgroundResource(R.drawable.akasztofa03); break;
            case 4: img_akasztofa.setBackgroundResource(R.drawable.akasztofa04); break;
            case 5: img_akasztofa.setBackgroundResource(R.drawable.akasztofa05); break;
            case 6: img_akasztofa.setBackgroundResource(R.drawable.akasztofa06); break;
            case 7: img_akasztofa.setBackgroundResource(R.drawable.akasztofa07); break;
            case 8: img_akasztofa.setBackgroundResource(R.drawable.akasztofa08); break;
            case 9: img_akasztofa.setBackgroundResource(R.drawable.akasztofa09); break;
            case 10: img_akasztofa.setBackgroundResource(R.drawable.akasztofa10); break;
            case 11: img_akasztofa.setBackgroundResource(R.drawable.akasztofa11); break;
            case 12: img_akasztofa.setBackgroundResource(R.drawable.akasztofa12); break;
            case 13: img_akasztofa.setBackgroundResource(R.drawable.akasztofa13); break;
        }

    }

    private void szinCsere(){
        if (betukSzinei[index]){
            txt_betu.setTextColor(Color.BLACK);
        }
        if (!betukSzinei[index]){
            txt_betu.setTextColor(Color.parseColor("#ffa500"));
        }
    }

    private void restart() {
        _szo = szavakTomb[new Random().nextInt(szavakTomb.length)].toUpperCase();
        szoHossza = _szo.length();
        index = 0;
        txt_betu.setText(betukTomb[index].toUpperCase());
        kitalalandoSzo = new char[szoHossza];
        betukSzinei = new boolean[betukTomb.length];
        for (boolean b : betukSzinei) {
            b = false;
        }
        szinCsere();
        akasztofaIndex = 0;
        for (int i = 0; i < szoHossza; i++){
            if (_szo.charAt(i) == ' ') {
                kitalalandoSzo[i] = ' ';
            }
            if  (_szo.charAt(i) == '-') {
                kitalalandoSzo[i] = '-';
            }
            if (_szo.charAt(i) != ' ' && _szo.charAt(i) != '-') {
                kitalalandoSzo[i] = '_';
            }
        }
        kepCsere();
        txt_akasztofa.setText(kiir(kitalalandoSzo));


    }

    private String kiir(char[] s){
        String asd = "";
        for (char c : s) {
            asd += c + " ";
        }
        return asd;
    }
}
