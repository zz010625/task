package com.example.a6_lv5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.a6_lv5.adapter.FragmentStateAdapter.MyFragmentStateAdapter;
import com.example.a6_lv5.fragment.GeneralFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private ImageView moreOperations;
    private String allTitles = "";
    private String allFragmentId = "";
    private List<String> localFragmentId = new ArrayList<>();
    private List<String> fragmentId = new ArrayList<>();
    private int fragmentNumber = 3;
    private List<String> localTitles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        SharedPreferences dataOfTabsAndFragments = getSharedPreferences("dataOfTabsAndFragments", MODE_PRIVATE);
        SharedPreferences.Editor dataOfTabsAndFragmentsEditor = getSharedPreferences("dataOfTabsAndFragments", MODE_PRIVATE).edit();
        viewPager2 = findViewById(R.id.vp_bills);
        tabLayout = findViewById(R.id.tl_category);

        //调用Tools类中方法初始化titles(需要用到返回的两个值 所以先返回一个集合 再将这两个值取出)
        ArrayList arrayList = Tools.initTitles(this, titles, allTitles, localTitles);
        allTitles = (String) arrayList.get(0);
        localTitles = (List) arrayList.get(1);
        //调用Tools类中方法初始化fragments
        fragments = Tools.initFragments(this, fragments, fragmentId);

        MyFragmentStateAdapter myFragmentStateAdapter = new MyFragmentStateAdapter(this, fragments);
        viewPager2.setAdapter(myFragmentStateAdapter);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        //设置所有界面都预加载 因为在删除fragment后 要使得跳转到的antherFragment的内容能够得到刷新 需要调用Tools中方法 所以需要拿到该antherFragment的对象
        //如果未设置所有界面预加载 可能因为antherFragment还未加载 故从fragments集合里取得的对象中的各种参数都还未初始化
        viewPager2.setOffscreenPageLimit(fragments.size());
        //设置tab标题
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(localTitles.get(position));
            }
        }).attach();


        //当页面滑动时 调用Tools类方法初始化bills
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                dataOfTabsAndFragmentsEditor.putInt("selectedTabPosition", tabLayout.getSelectedTabPosition());
                dataOfTabsAndFragmentsEditor.apply();
                //使pager切换后界面显示的 title"总价": 中的title能得到刷新
                //获取当前fragment对象 并调用Tools类中方法初始化bills 最后notify
                GeneralFragment fragment = (GeneralFragment) fragments.get(tabLayout.getSelectedTabPosition());
                Tools.initBills(MainActivity.this, fragment.getAllBills(), fragment.getLocalBills(), fragment.getBillArrayList(), fragment.getFragmentId(), tabLayout.getSelectedTabPosition());
                fragment.getMyRecyclerViewAdapter().notifyDataSetChanged();
                myFragmentStateAdapter.notifyDataSetChanged();

            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);

            }
        });


        //设置menu
        moreOperations = findViewById(R.id.iv_more_operations);
        moreOperations.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                PopupMenu operationsMenu = new PopupMenu(MainActivity.this, moreOperations);
                operationsMenu.getMenuInflater().inflate(R.layout.titles_menu, operationsMenu.getMenu());
                operationsMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {


                            //设置点击 新建 来add title和fragment
                            case R.id.popup_add:
                                AlertDialog.Builder addTabDialog = new AlertDialog.Builder(MainActivity.this);
                                addTabDialog.setTitle("新建分组:");
                                View addTabView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.et_dialog, null);
                                addTabDialog.setView(addTabView);
                                EditText addTitle = addTabView.findViewById(R.id.et_input);
                                addTitle.setHint("请输入分组名");
                                addTitle.setInputType(InputType.TYPE_CLASS_TEXT);
                                addTabDialog.setCancelable(false);
                                //点击确认
                                addTabDialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (TextUtils.isEmpty(addTitle.getText())) {
                                            Toast.makeText(MainActivity.this, "输入不能为空！", Toast.LENGTH_SHORT).show();
                                        } else {
                                            String input = addTitle.getText().toString();
                                            titles.add(input);
                                            allTitles += "," + titles.get(titles.size() - 1);
                                            //调用Tools类中方法保存titles
                                            localTitles = Tools.saveTitles(MainActivity.this, allTitles, localTitles);
                                            //传入id使得存取数据时 能够与其他fragment分开来存取
                                            allFragmentId = dataOfTabsAndFragments.getString("fragmentId", "0,1,2");
                                            fragmentId = Arrays.asList(allFragmentId.split(","));
                                            int id = Integer.parseInt(fragmentId.get(fragmentId.size() - 1)) + 1;
                                            allFragmentId += "," + id;
                                            dataOfTabsAndFragmentsEditor.putString("fragmentId", allFragmentId);
                                            dataOfTabsAndFragmentsEditor.apply();
                                            myFragmentStateAdapter.addFragment(new GeneralFragment(id));
                                            //更新碎片数量
                                            fragmentNumber = dataOfTabsAndFragments.getInt("fragmentsNumber", 3) + 1;
                                            //调用Tools类中方法保存fragment数量
                                            fragmentNumber = Tools.saveFragmentNumber(MainActivity.this, fragmentNumber);
                                            //跳转到新创建的pager
                                            viewPager2.setCurrentItem(fragments.size() - 1);
                                        }
                                    }
                                });
                                //点击取消
                                addTabDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                addTabDialog.show();
                                return true;


                            //设置点击 编辑 来修改title
                            case R.id.popup_edit:
                                AlertDialog.Builder editTitleDialog = new AlertDialog.Builder(MainActivity.this);
                                editTitleDialog.setTitle("分组名:");
                                View editTitleView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.et_dialog, null);
                                EditText revisedTitle = editTitleView.findViewById(R.id.et_input);
                                revisedTitle.setInputType(InputType.TYPE_CLASS_TEXT);
                                editTitleDialog.setView(editTitleView);
                                revisedTitle.setText(localTitles.get(tabLayout.getSelectedTabPosition()));
                                editTitleDialog.setCancelable(false);
                                //点击确认
                                editTitleDialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (TextUtils.isEmpty(revisedTitle.getText())) {
                                            Toast.makeText(MainActivity.this, "输入不能为空！", Toast.LENGTH_SHORT).show();
                                        } else {
                                            localTitles.set(tabLayout.getSelectedTabPosition(), String.valueOf(revisedTitle.getText()));
                                            titles.set(tabLayout.getSelectedTabPosition(), String.valueOf(revisedTitle.getText()));
                                            //重新设置标题
                                            new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
                                                @Override
                                                public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                                                    tab.setText(localTitles.get(position));
                                                }
                                            }).attach();
                                            //调用Tools类中方法保存修改后的allTitles
                                            allTitles = Tools.saveRevisedTitle(MainActivity.this, localTitles, allTitles);
                                            //使修改title后界面显示的 title"总价": 中的title能得到刷新
                                            //获取当前fragment对象 并调用Tools类中方法初始化bills 最后notify
                                            GeneralFragment fragment = (GeneralFragment) fragments.get(tabLayout.getSelectedTabPosition());
                                            Tools.initBills(MainActivity.this, fragment.getAllBills(), fragment.getLocalBills(), fragment.getBillArrayList(), fragment.getFragmentId(), tabLayout.getSelectedTabPosition());
                                            fragment.getMyRecyclerViewAdapter().notifyDataSetChanged();

                                        }
                                    }
                                });
                                //点击取消
                                editTitleDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                editTitleDialog.show();
                                return true;


                            //设置点击 删除 来删除fragment
                            case R.id.popup_delete:
                                if (fragments.size() > 1) {
                                    int deleteFragment = tabLayout.getSelectedTabPosition(); //将被删除fragment所在的tab序数储存下来
                                    fragments.remove(tabLayout.getSelectedTabPosition());
                                    myFragmentStateAdapter.notifyDataSetChanged();
                                    titles.remove(deleteFragment);
                                    //调用Tools类中方法保存修改后的allTitles
                                    allTitles = Tools.saveRevisedTitle(MainActivity.this, titles, allTitles);
                                    //调用Tools类中方法初始化titles(需要用到返回的两个值 所以先返回一个集合 再将这两个值取出)
                                    ArrayList arrayList = Tools.initTitles(MainActivity.this, titles, allTitles, localTitles);
                                    allTitles = (String) arrayList.get(0);
                                    localTitles = (List) arrayList.get(1);
                                    //调用Tools类中方法保存titles
                                    localTitles = Tools.saveTitles(MainActivity.this, allTitles, localTitles);
                                    //重新设置tab标题
                                    new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
                                        @Override
                                        public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                                            tab.setText(localTitles.get(position));
                                        }
                                    }).attach();
                                    //更新碎片数量
                                    fragmentNumber = dataOfTabsAndFragments.getInt("fragmentsNumber", 3) - 1;
                                    //调用Tools类中方法保存fragment数量
                                    fragmentNumber = Tools.saveFragmentNumber(MainActivity.this, fragmentNumber);
                                    //调用Tools类中方法将被删除fragment中的内容从SharedPreferences清空 避免新建时内容覆盖
                                    allFragmentId = Tools.clearBills(MainActivity.this, allFragmentId, fragmentId, deleteFragment);
                                    //调用Tools类中方法保存新的碎片ID
                                    ArrayList arrayList1 = Tools.saveID(MainActivity.this, allFragmentId, fragmentId, deleteFragment, localFragmentId);
                                    allFragmentId = (String) arrayList1.get(0);
                                    localFragmentId = (List) arrayList1.get(1);
                                } else {
                                    Toast.makeText(MainActivity.this, "至少要有一个分组！", Toast.LENGTH_SHORT).show();
                                }
                                return true;
                        }

                        return true;
                    }
                });
                operationsMenu.show();
            }
        });
    }

}