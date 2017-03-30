package com.example.mac.twid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {
    TextView showID, showTitle, showInfo, showArea, showSex;
    Button createID;
    EditText inputArea, inputSex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findview();
        showInfo.setText("各縣市代碼(請輸入英文大寫):\nＡ台北市\tＢ台中市\tＣ基隆市\t" +
                "Ｄ台南市\tＥ高雄市\nＦ台北縣\tＧ宜蘭縣\tＨ桃園縣\tＩ嘉義市\tＪ新竹縣\n" +
                "Ｋ苗栗縣\tＬ台中縣\tＭ南投縣\tＮ彰化縣\tＯ新竹市\nＰ雲林縣\tＱ嘉義縣\t" +
                "Ｒ台南縣\tＳ高雄縣\tＴ屏東縣\nＵ花蓮縣\tＶ台東縣\tＷ金門縣\tＸ澎湖縣\t" +
                "Ｙ陽明山\nＺ連江縣\n性別: 男=1, 女=2\n若要隨機產生,請直接按下按鈕,不必輸入任何代碼!");
    }

    private void findview() {
        showID = (TextView)findViewById(R.id.textView4);
        showTitle = (TextView)findViewById(R.id.textView);
        showInfo = (TextView)findViewById(R.id.textView5);
        showArea = (TextView)findViewById(R.id.textView2);
        showSex = (TextView)findViewById(R.id.textView3);
        createID = (Button)findViewById(R.id.button);
        inputArea = (EditText)findViewById(R.id.editText);
        inputSex = (EditText)findViewById(R.id.editText2);
        createID.setOnClickListener(c1);
    }

    View.OnClickListener c1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            create();
        }
    };
    public void create(){
        try {
            String letters = "ABCDEFGHJKLMNPQRSTUVXYWZIO";
            String a = inputArea.getText().toString();
            String s = inputSex.getText().toString();

            if (a.equals("") && s.equals("")) {
                TWId id = new TWId();
                showID.setText(id.getId());
            } else if (!a.equals("") && s.equals("")) {
                int area = letters.indexOf(a);
                TWId id = new TWId(area);
                showID.setText(id.getId());
            } else if (a.equals("") && !s.equals("")) {
                boolean isFemale = true;
                isFemale = (s.equals("2")) ? true : false;
                TWId id = new TWId(isFemale);
                showID.setText(id.getId());
            } else {
                int area = letters.indexOf(a);
                boolean isFemale = true;
                isFemale = (s.equals("2")) ? true : false;
                TWId id = new TWId(isFemale, area);
                showID.setText(id.getId());
            }
        }catch(Exception e){
            Toast toast = Toast.makeText(this,
                    "輸入錯誤!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    class TWId extends MainActivity{

        String letters ="ABCDEFGHJKLMNPQRSTUVXYWZIO";
        private String id;

        TWId(){
            this((int)(Math.random()*26));
        }

        TWId(boolean isFemale){
            this(isFemale,(int)(Math.random()*26));
        }

        TWId(int area){
            this((int)(Math.random()*2)==0,area);
        }

        TWId(boolean isFemale, int area){
            //super();
            char f0 =letters.charAt(area);
            char f1 = isFemale?'2':'1';
            StringBuffer sb = new StringBuffer(""+f0+f1);
            for(int i=0;i<7;i++){
                sb.append((int)(Math.random()*10));
            }
            for(int i = 0;i<10;i++){
                if(isCheckOK(sb.toString()+i)){
                    id = sb.append(i).toString();
                    break;
                }
            }
        }

        TWId(String id){
            this.id = id;
        }

        boolean isFemale(){
            char gender = id.charAt(1);
            if(gender =='2'){
                return true;
            }
            return false;
        }

        boolean isCheckOK(String id){
            if(!id.matches("^[A-Z][12][0-9]{0,8}$")) return false;

            char f0 = id.charAt(0);
            int n12 = letters.indexOf(f0) + 10;
            int n1 = n12/10;
            int n2 = n12 % 10;
            int n3 = parseInt((id.substring(1,2)));
            int n4 = parseInt((id.substring(2,3)));
            int n5 = parseInt((id.substring(3,4)));
            int n6 = parseInt((id.substring(4,5)));
            int n7 = parseInt((id.substring(5,6)));
            int n8 = parseInt((id.substring(6,7)));
            int n9 = parseInt((id.substring(7,8)));
            int n10 = parseInt((id.substring(8,9)));
            int n11 = parseInt((id.substring(9,10)));
            int sum = n1 + n2*9 + n3*8 + n4*7 + n5*6 + n6*5 +
                    n7*4 + n8*3 + n9*2 + n10 + n11;
            return sum%10==0;
        }
        String getId(){
            return id;
        }
    }

}
