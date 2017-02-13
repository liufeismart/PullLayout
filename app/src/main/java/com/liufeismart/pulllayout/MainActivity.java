package com.liufeismart.pulllayout;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.liufeismart.pulllayout.widget.PullLayout;

public class MainActivity extends AppCompatActivity {
    String[] data = new String[30];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final float touchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
        setContentView(R.layout.activity_main);
        final PullLayout pulllayout_ = (PullLayout) this.findViewById(R.id.pulllayout_);
        pulllayout_.setImageBackgroundResource(R.mipmap.bg);
        View layout = LayoutInflater.from(this).inflate(R.layout.pannel, null);
        pulllayout_.setLayout(layout);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int height = 600;
        int layoutHeight = wm.getDefaultDisplay().getHeight()+300;
        pulllayout_.setLayoutHeight(layoutHeight);
        View ll_top = layout.findViewById(R.id.ll_top);
        ViewGroup.LayoutParams params = ll_top.getLayoutParams();

        params.height = 300;
        ll_top.setLayoutParams(params);
        pulllayout_.setImageHeight(height);

        final ListView listview_list1 = (ListView) this.findViewById(R.id.listview_list1);

        for(int i=0; i<data.length; i++) {
            data[i] = "数据"+(i+1);
        }
        listview_list1.setAdapter(new MyAdapter(data,this));
        listview_list1.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem == 0) {
                    View child0 = view.getChildAt(firstVisibleItem);
                    if(child0!=null) {
                        if(child0.getTop()<=0) {
//                            listview_list1.setTag(false);
                            pulllayout_.setChildInTouchEvent(false);
                            return;
                        }

                    }

                }
                pulllayout_.setChildInTouchEvent(true);
            }
        });
        layout.findViewById(R.id.tv_list1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "列表一",Toast.LENGTH_LONG).show();
            }
        });
        layout.findViewById(R.id.tv_list2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "列表二",Toast.LENGTH_LONG).show();
            }
        });
//        listview_list1.setOnTouchListener(new View.OnTouchListener() {
//            private float mDownX,mDownY;
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                switch (motionEvent.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        mDownX = motionEvent.getX();
//                        mDownY = motionEvent.getY();
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        float mMoveX = Math.abs(motionEvent.getX() - mDownX);
//                        float mMoveY = Math.abs(motionEvent.getY() - mDownY);
//                        if(mMoveY>mMoveX&& mMoveY>touchSlop) {
//                           boolean isTop = (boolean) listview_list1.getTag();
//                           if(isTop) {
//                               pulllayout_.setChildInTouchEvent(false);
//                           }
//                        }
//                        else {
//                            pulllayout_.setChildInTouchEvent(true);
//                        }
//                        break;
//
//                }
//
//                return false;
//            }
//        });
    }

    class MyAdapter extends BaseAdapter {

        private String[] data;
        private Context context;


        public MyAdapter(String[] data, Context context) {
            this.data = data;
            this.context =context;
        }

        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public Object getItem(int i) {
            return data[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            ViewHolder viewHolder = null;
            if(convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_lv1, null);
                viewHolder = new ViewHolder();
                viewHolder.tv_ = (TextView) convertView.findViewById(R.id.tv_);
                convertView.setTag(viewHolder);
            }
            else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.tv_.setText(data[i]);
            return convertView;
        }

        class ViewHolder {
            TextView tv_;
        }
    }

}
