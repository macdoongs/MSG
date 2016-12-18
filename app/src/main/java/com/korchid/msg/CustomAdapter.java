package com.korchid.msg;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mac0314 on 2016-12-19.
 */

public class CustomAdapter extends PagerAdapter {
    LayoutInflater inflater;

    public CustomAdapter(LayoutInflater inflater) {
        // TODO Auto-generated constructor stub

        //전달 받은 LayoutInflater를 멤버변수로 전달
        this.inflater=inflater;
    }

    //PagerAdapter가 가지고 잇는 View의 개수를 리턴
    //Tab에 따른 View를 보여줘야 하므로 Tab의 개수인 3을 리턴..
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 3; //보여줄 View의 개수 리턴(Tab이 3개라서 3을 리턴)
    }

    //ViewPager가 현재 보여질 Item(View객체)를 생성할 필요가 있는 때 자동으로 호출
    //쉽게 말해, 스크롤을 통해 현재 보여져야 하는 View를 만들어냄.
    //첫번째 파라미터 : ViewPager
    //두번째 파라미터 : ViewPager가 보여줄 View의 위치(가장 처음부터 0,1,2,3...)
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub

        View view=null;//현재 position에서 보여줘야할 View를 생성해서 리턴...

        //새로운 View 객체를 Layoutinflater를 이용해서 생성
        //position마다 다른 View를 생성
        //만들어질 View의 설계는 res폴더>>layout폴더안에 3개의 레이아웃 파일 사용
        switch( position ){
            case 0: //첫번째 Tab을 선택했을때 보여질 뷰
                view= inflater.inflate(R.layout.tab_user_info_0, null);
                break;
            case 1: //두번째 Tab을 선택했을때 보여질 뷰
                view= inflater.inflate(R.layout.tab_user_info_1, null);
                break;
            case 2: //세번째 Tab을 선택했을때 보여질 뷰
                view= inflater.inflate(R.layout.tab_user_info_2, null);
                break;
        }

        //ViewPager에 위에서 만들어 낸 View 추가
        if(view != null) container.addView(view);

        //세팅된 View를 리턴
        return view;
    }

    //화면에 보이지 않은 View는파쾨를 해서 메모리를 관리함.
    //첫번째 파라미터 : ViewPager
    //두번째 파라미터 : 파괴될 View의 인덱스(가장 처음부터 0,1,2,3...)
    //세번째 파라미터 : 파괴될 객체(더 이상 보이지 않은 View 객체)
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub

        //ViewPager에서 보이지 않는 View는 제거
        //세번째 파라미터가 View 객체 이지만 데이터 타입이 Object여서 형변환 실시
        container.removeView((View)object);

    }

    //instantiateItem() 메소드에서 리턴된 Ojbect가 View가  맞는지 확인하는 메소드
    @Override
    public boolean isViewFromObject(View v, Object obj) {
        // TODO Auto-generated method stub
        return v==obj;
    }

}
