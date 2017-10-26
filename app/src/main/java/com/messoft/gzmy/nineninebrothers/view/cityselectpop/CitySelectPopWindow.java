package com.messoft.gzmy.nineninebrothers.view.cityselectpop;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.messoft.gzmy.nineninebrothers.R;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * 弹出城市的popwindow
 * Created by Administrator on 2016/4/29.
 */
public class CitySelectPopWindow extends PopupWindow implements OnWheelChangedListener {

    private Button btnCancel;
    private Button btnConfirm;

    /**
     * 所有省 + 省ID
     */
    protected String[] mProvinceDatas;
    protected String[] mProvinceID;
    /**
     * key - 省 value - 市 + 城市ID
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    protected Map<String, String[]> mCitisDatasMapID = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区
     */
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();
    protected Map<String, String[]> mDistrictDatasMapID = new HashMap<String, String[]>();

    /**
     * key - 区 values - 邮编
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

    /**
     * 当前省的名称和省ID
     */
    protected String mCurrentProviceName;
    protected String mCurrentProviceID;
    /**
     * 当前市的名称和城市ID
     */
    protected String mCurrentCityName;
    protected String mCurrentCityID;
    /**
     * 当前区的名称
     */
    protected String mCurrentDistrictName = "";

    /**
     * 当前区的市区ID
     */
    protected String mCurrentDistrictCode = "";

    /**
     * 当前的邮编码
     */
    protected String mCurrentZipCode = "";

    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;

    private Context context;

    public CitySelectPopWindow(Context context, final Handler handler) {
        super(context);
        this.context = context;

        View content = LayoutInflater.from(context).inflate(R.layout.city_select_pop_window, null);
        setContentView(content);
        //设置pop宽高,不设置不会显示
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //点击空白消失
        setFocusable(true);
        //设置进出动画
        setAnimationStyle(R.style.DefaultAnimation);

        mViewProvince = (WheelView) content.findViewById(R.id.id_province);
        mViewCity = (WheelView) content.findViewById(R.id.id_city);
        mViewDistrict = (WheelView) content.findViewById(R.id.id_district);
        btnCancel = (Button) content.findViewById(R.id.btn_cancel);
        btnConfirm = (Button) content.findViewById(R.id.btn_confirm);

        setUpListener();

        setUpData(context);

        //取消
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //确定
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 选择后获得省市区ID
                showSelectedResult();
                //发给activity
                Message msg = new Message();
                msg.what = 0;
                handler.sendMessage(msg);
            }
        });
    }

    private void showSelectedResult() {
//        Toast.makeText(context, mCurrentProviceName+","+mCurrentProviceID+","+mCurrentCityName+","+mCurrentCityID+","
//                +mCurrentDistrictName+","+mCurrentDistrictCode, Toast.LENGTH_SHORT).show();
    }

    //提供数据给外界调用，由于后台要求传递的数据很恶心，所以这里写的我好烦，直接用对象传也行
    public String getCurrentProviceName() {
        return mCurrentProviceName;
    }

    public String getCurrentProviceID() {
        return mCurrentProviceID;
    }

    public String getCurrentCityName() {
        return mCurrentCityName;
    }

    public String getCurrentCityID() {
        return mCurrentCityID;
    }

    public String getCurrentDistrictName() {
        return mCurrentDistrictName;
    }

    public String getCurrentDistrictCode() {
        return mCurrentDistrictCode;
    }

    private void setUpListener() {
        // 添加change事件
        mViewProvince.addChangingListener(this);
        // 添加change事件
        mViewCity.addChangingListener(this);
        // 添加change事件
        mViewDistrict.addChangingListener(this);
    }

    private void setUpData(Context context) {
        initProvinceDatas(context);
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(context, mProvinceDatas));
        // 设置可见条目数量
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        updateCities(context);
        updateAreas(context);
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities(Context context) {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        mCurrentProviceID = mProvinceID[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(context, cities));
        mViewCity.setCurrentItem(0);
        updateAreas(context);
    }

    /**
     * 解析省市区的XML数据
     */
    protected void initProvinceDatas(Context context) {
        List<ProvinceModel> provinceList = null;
        AssetManager asset = context.getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            provinceList = handler.getDataList();
            //*/ 初始化默认选中的省、市、区
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                mCurrentProviceID = provinceList.get(1).getProvinceID();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    mCurrentCityID = cityList.get(1).getCityID();
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                    mCurrentDistrictCode = districtList.get(1).getZipcode();
                }
            }
            //*/
            mProvinceDatas = new String[provinceList.size()];
            mProvinceID = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                // 遍历所有省的数据
                mProvinceDatas[i] = provinceList.get(i).getName();
                mProvinceID[i] = provinceList.get(i).getProvinceID();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                String[] cityIDs = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    // 遍历省下面的所有市的数据
                    cityNames[j] = cityList.get(j).getName();
                    cityIDs[j] = cityList.get(j).getCityID();
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    String[] distrinctNameArray = new String[districtList.size()];
                    String[] distrinctIdArray = new String[districtList.size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                    for (int k = 0; k < districtList.size(); k++) {
                        // 遍历市下面所有区/县的数据
                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        // 区/县对于的邮编，保存到mZipcodeDatasMap
                        mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                        distrinctIdArray[k] = districtModel.getZipcode();
                    }
                    // 市-区/县的数据，保存到mDistrictDatasMap
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                    mDistrictDatasMapID.put(cityIDs[j], distrinctIdArray);
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
                //城市id保存到集合中
                mCitisDatasMapID.put(provinceList.get(i).getName(), cityIDs);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas(Context context) {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        mCurrentCityID = mCitisDatasMapID.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);
        String[] areasID = mDistrictDatasMapID.get(mCurrentCityID);

        if (areas == null||areas.length==0) {
            areas = new String[]{""};
        }
        if (areasID == null || areasID.length == 0) {
            areasID = new String[]{""};
        }
        mCurrentDistrictName = areas[0];
        mCurrentDistrictCode = areasID[0];
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(context, areas));
        mViewDistrict.setCurrentItem(0);
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        // TODO Auto-generated method stub
        if (wheel == mViewProvince) {
            updateCities(context);
        } else if (wheel == mViewCity) {
            updateAreas(context);
        } else if (wheel == mViewDistrict) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            mCurrentDistrictCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        }
    }
}
