package com.example.a6_lv5.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a6_lv5.Tools;
import com.example.a6_lv5.adapter.RecycleViewAdapter.MyRecyclerViewAdapter;
import com.example.a6_lv5.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class GeneralFragment extends Fragment {
    private View view;
    private ArrayList billArrayList = new ArrayList();
    private List<String> localBills = new ArrayList();
    private String allBills = "";
    private int fragmentId = 0;
    private FloatingActionButton floatingActionButton;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;
    private Activity generalFragmentActivity;
    private TabLayout tabLayout;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public GeneralFragment(int fragmentId) {
        this.fragmentId = fragmentId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        generalFragmentActivity = getActivity();
        view = inflater.inflate(R.layout.general_fragment, container, false);
        recyclerView = view.findViewById(R.id.rv_first);
        myRecyclerViewAdapter = new MyRecyclerViewAdapter(billArrayList);
        recyclerView.setAdapter(myRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        tabLayout = view.findViewById(R.id.tl_category);
        //调用Tools类中方法初始化bills(需要用到返回的两个值 所以先返回一个集合 再将这两个值取出)
        SharedPreferences dataOfTabsAndFragments = getActivity().getSharedPreferences("dataOfTabsAndFragments", MODE_PRIVATE);
        ArrayList arrayList = Tools.initBills(getActivity(), allBills, localBills, billArrayList, fragmentId, dataOfTabsAndFragments.getInt("selectedTabPosition", 0));
        billArrayList = (ArrayList) arrayList.get(0);
        allBills = (String) arrayList.get(1);
        myRecyclerViewAdapter.notifyDataSetChanged();
        floatingActionButton = view.findViewById(R.id.fb_add);
        //点击加号
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog1 = new AlertDialog.Builder(getActivity());
                dialog1.setTitle("消费类别:");
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.et_dialog, null);
                dialog1.setView(view);
                EditText inputConsumptionCategory = view.findViewById(R.id.et_input);
                inputConsumptionCategory.setHint("请输入消费类别");
                inputConsumptionCategory.setInputType(InputType.TYPE_CLASS_TEXT);
                dialog1.setCancelable(false);
                //点击确认
                dialog1.setPositiveButton("下一步", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (TextUtils.isEmpty(inputConsumptionCategory.getText())) {
                            Toast.makeText(getContext(), "输入不能为空！", Toast.LENGTH_SHORT).show();
                        } else {
                            AlertDialog.Builder dialog2 = new AlertDialog.Builder(getActivity());
                            dialog2.setTitle("金额:");
                            View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.et_dialog, null);
                            dialog2.setView(view1);
                            EditText inputPrice = view1.findViewById(R.id.et_input);
                            inputPrice.setHint("请输入金额(元)");
                            dialog2.setCancelable(false);
                            //点击确认
                            dialog2.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (TextUtils.isEmpty(inputPrice.getText())) {
                                        Toast.makeText(getContext(), "输入不能为空！", Toast.LENGTH_SHORT).show();
                                    } else {
                                        //调用Tools类中方法保存bills(需要用到返回的两个值 所以先返回一个集合 再将这两个值取出)
                                        ArrayList arrayList = Tools.saveBills(getActivity(), allBills, localBills, billArrayList, inputConsumptionCategory, inputPrice, fragmentId, dataOfTabsAndFragments.getInt("selectedTabPosition", 0));
                                        billArrayList = (ArrayList) arrayList.get(0);
                                        allBills = (String) arrayList.get(1);
                                        myRecyclerViewAdapter.notifyDataSetChanged();//因为在保存过程中涉及到了 billArrayList.remove 即原内容被更改了 所以要通知adapter 使内容刷新
                                    }
                                }
                            });
                            //点击取消
                            dialog2.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dialog2.show();
                        }
                    }
                });
                //点击取消
                dialog1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog1.show();

            }
        });
        return view;
    }


    public MyRecyclerViewAdapter getMyRecyclerViewAdapter() {
        return myRecyclerViewAdapter;
    }

    public ArrayList getBillArrayList() {
        return billArrayList;
    }

    public List<String> getLocalBills() {
        return localBills;
    }

    public String getAllBills() {
        return allBills;
    }

    public Integer getFragmentId() {
        return fragmentId;
    }
}