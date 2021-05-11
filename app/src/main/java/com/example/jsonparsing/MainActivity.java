package com.example.jsonparsing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
/*참고링크
*https://www.youtube.com/watch?v=ZJepo2wRiBk (JSON 파일 parcing 하는 법)
* */
public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> name = new ArrayList<>();//휴게소명
    ArrayList<String> road_type = new ArrayList<>();//도로종류
    ArrayList<String> road_route_name = new ArrayList<>();//도로노선명
    ArrayList<Float> latitude = new ArrayList<>();//위도
    ArrayList<Float> longitude = new ArrayList<>();//경도
    ArrayList<String> gas_station = new ArrayList<>();//주유소 유무
    ArrayList<String> lpg_charge = new ArrayList<>();//LPG충전소유무
    ArrayList<String> electric_charge = new ArrayList<>();//전기차충전소유무

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView= findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);


        //Service.json 파일에서 json객체 가져오기
        try {
            JSONObject jsonObject = new JSONObject(JsonDataFromAsset()); //jsonObject는 ServicArea.json파일
            JSONArray jsonArray = jsonObject.getJSONArray("ServiceArea");
            for ( int i =0 ;i<jsonArray.length();i++){
                JSONObject service_area_data = jsonArray.getJSONObject(i);
                name.add(service_area_data.getString("휴게소명"));
                road_type.add(service_area_data.getString("도로종류"));
                road_route_name.add(service_area_data.getString("도로노선명"));
                latitude.add((float) service_area_data.getDouble("위도"));
                longitude.add((float) service_area_data.getDouble("경도"));
                gas_station.add(service_area_data.getString("주유소유무"));
                lpg_charge.add(service_area_data.getString("LPG충전소유무"));
                electric_charge.add(service_area_data.getString("전기차충전소유무"));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //어뎁터 객체만들기
        HelperAdapter helperAdapter = new HelperAdapter(name,road_type,road_route_name,latitude,longitude,gas_station,lpg_charge,electric_charge,MainActivity.this);
        recyclerView.setAdapter(helperAdapter);


    }

    //json 파일 접근하는 메소드
    private String JsonDataFromAsset(){

        String json = null;
        try {
            InputStream inputStream = getAssets().open("ServiceArea.json");
            int sizeOfFile = inputStream.available();
            byte[] bufferData = new byte[sizeOfFile];
            inputStream.read(bufferData);
            inputStream.close();
            json = new String(bufferData,"UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }
}