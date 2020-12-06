package com.example.a6_lv5;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.a6_lv5.fragment.GeneralFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Tools {
    //得到此时时间
    public static String getTime() {
        Date date = new Date();
        String time = date.toLocaleString();
        return time;
    }


    //初始化标题
    public static ArrayList initTitles(Activity mainActivity, List<String> titles, String allTitles, List<String> localTitles) {
        SharedPreferences dataOfTabsAndFragments = mainActivity.getSharedPreferences("dataOfTabsAndFragments", MODE_PRIVATE);
        allTitles = dataOfTabsAndFragments.getString("titles", "食品饮料,衣服饰品,日常用品");
        localTitles = Arrays.asList(allTitles.split(","));
        titles.removeAll(titles);//清空titles
        for (int i = 0; i < localTitles.size(); i++) {
            titles.add(localTitles.get(i));

        }

        //将需要拿的对象放到list里 再return该list
        ArrayList arrayList = new ArrayList();
        arrayList.add(allTitles);
        arrayList.add(localTitles);
        return arrayList;
    }


    //保存标题
    public static List saveTitles(Activity mainActivity, String allTitles, List<String> localTitles) {
        SharedPreferences.Editor dataOfTabsAndFragmentsEditor = mainActivity.getSharedPreferences("dataOfTabsAndFragments", MODE_PRIVATE).edit();

        dataOfTabsAndFragmentsEditor.putString("titles", allTitles);
        dataOfTabsAndFragmentsEditor.apply();
        localTitles = Arrays.asList(allTitles.split(","));

        return localTitles;
    }


    //保存修改后的标题
    public static String saveRevisedTitle(Activity mainActivity, List<String> titles, String allTitles) {
        SharedPreferences.Editor dataOfTabsAndFragmentsEditor = mainActivity.getSharedPreferences("dataOfTabsAndFragments", MODE_PRIVATE).edit();

        String newAllTitles = "";
        for (int i = 0; i < titles.size(); i++) {
            if (i < titles.size() - 1) {
                newAllTitles += titles.get(i) + ",";
            } else {
                newAllTitles += titles.get(i);
            }
        }
        allTitles = newAllTitles;
        dataOfTabsAndFragmentsEditor.putString("titles", allTitles);
        dataOfTabsAndFragmentsEditor.apply();

        return allTitles;
    }


    //初始化碎片
    public static ArrayList initFragments(Activity mainActivity, ArrayList<Fragment> fragments, List<String> localFragmentId) {
        SharedPreferences dataOfTabsAndFragments = mainActivity.getSharedPreferences("dataOfTabsAndFragments", MODE_PRIVATE);
        SharedPreferences.Editor dataOfTabsAndFragmentsEditor = mainActivity.getSharedPreferences("dataOfTabsAndFragments", MODE_PRIVATE).edit();

        String allFragmentId = dataOfTabsAndFragments.getString("fragmentId", "0,1,2");
        localFragmentId = Arrays.asList(allFragmentId.split(","));
        for (int i = 0; i < dataOfTabsAndFragments.getInt("fragmentsNumber", 3); i++) {
            int id = Integer.parseInt(localFragmentId.get(i));
            fragments.add(new GeneralFragment(id));
        }
        dataOfTabsAndFragmentsEditor.putString("fragmentId", allFragmentId);
        dataOfTabsAndFragmentsEditor.apply();
        return fragments;
    }


    //保存碎片数量
    public static int saveFragmentNumber(Activity mainActivity, int fragmentNumber) {
        SharedPreferences.Editor dataOfTabsAndFragmentsEditor = mainActivity.getSharedPreferences("dataOfTabsAndFragments", MODE_PRIVATE).edit();

        dataOfTabsAndFragmentsEditor.putInt("fragmentsNumber", fragmentNumber);
        dataOfTabsAndFragmentsEditor.apply();

        return fragmentNumber;
    }

    //保存ID
    public static ArrayList saveID(Activity mainActivity, String allFragmentId, List<String> fragmentId, int deleteFragment, List<String> localFragmentId) {
        SharedPreferences.Editor dataOfTabsAndFragmentsEditor = mainActivity.getSharedPreferences("dataOfTabsAndFragments", MODE_PRIVATE).edit();
        fragmentId = Arrays.asList(allFragmentId.split(","));
        for (int i = 0; i < fragmentId.size(); i++) {
            localFragmentId.add(fragmentId.get(i));
        }
        localFragmentId.remove(deleteFragment);

        allFragmentId = "";//清空 否则在下次删除执行242-248行时 得到的allFragmentId是叠加后的
        for (int i = 0; i < localFragmentId.size(); i++) {
            if (i < localFragmentId.size() - 1) {
                allFragmentId += localFragmentId.get(i) + ",";
            } else {
                allFragmentId += localFragmentId.get(i);
            }
        }
        localFragmentId.removeAll(localFragmentId);//清空 否则在下次删除执行236-238行时 得到的localFragmentId是叠加后的
        dataOfTabsAndFragmentsEditor.putString("fragmentId", allFragmentId);
        dataOfTabsAndFragmentsEditor.apply();
        ArrayList arrayList = new ArrayList();
        arrayList.add(allFragmentId);
        arrayList.add(localFragmentId);
        return arrayList;
    }

    //初始化账单
    public static ArrayList initBills(Activity generalFragmentActivity, String allBills, List<String> localBills, ArrayList billArrayList, int fragmentId, int selectedTabPosition) {
        SharedPreferences dataOfPagers = generalFragmentActivity.getSharedPreferences(String.valueOf(fragmentId), MODE_PRIVATE);
        SharedPreferences dataOfTabsAndFragments = generalFragmentActivity.getSharedPreferences("dataOfTabsAndFragments", MODE_PRIVATE);

        billArrayList.removeAll(billArrayList); //清空list 因为该list在Fragment为成员变量 它被赋值后的值不会因为碎片销毁而清空 则当碎片重新加载时 传入该方法中的list不为空 则运行下面代码就不能将其初始化了 而是会加载销毁前内容加上初始化内容
        localBills.removeAll(localBills); //同上
        allBills = dataOfPagers.getString("bills", "");
        if (allBills.equals("")) {
        } else {
            localBills = Arrays.asList(allBills.split(","));
            for (int i = 0; i < localBills.size() / 3; i++) {
                billArrayList.add(new Bill(localBills.get(i * 3), localBills.get(i * 3 + 1), localBills.get(i * 3 + 2)));
            }

            //计算当前界面总金额
            Double totalCost = 0.0;
            for (int i = 0; i < localBills.size() / 3; i++) {
                totalCost += Double.parseDouble(localBills.get(i * 3 + 2));
            }
            String allTitles = dataOfTabsAndFragments.getString("titles", "食品饮料,衣服饰品,日常用品");
            List<String> localTitles = Arrays.asList(allTitles.split(","));
            //在最后显示当前界面总金额
            billArrayList.add(new Bill("", localTitles.get(selectedTabPosition) + "共计:", totalCost.toString()));
        }

        //将需要拿的对象放到list里 再return该list
        ArrayList arrayList = new ArrayList();
        arrayList.add(billArrayList);
        arrayList.add(allBills);
        return arrayList;
    }


    //保存账单
    public static ArrayList saveBills(Activity generalFragmentActivity, String allBills, List<String> localBills, ArrayList billArrayList, EditText editText1, EditText editText2, int fragmentId, int selectedTabPosition) {
        SharedPreferences.Editor dataOfPagersEditor = generalFragmentActivity.getSharedPreferences(String.valueOf(fragmentId), MODE_PRIVATE).edit();
        SharedPreferences dataOfTabsAndFragments = generalFragmentActivity.getSharedPreferences("dataOfTabsAndFragments", MODE_PRIVATE);

        if (allBills.equals("")) {
            allBills += Tools.getTime() + ",";
        } else {
            billArrayList.remove(billArrayList.size() - 1);//因为在初始化碎片的最后实现了显示当前界面总金额 而这项数据会根据账单内容的改变而改变 故不需要被保存
            allBills += "," + Tools.getTime() + ",";
        }
        allBills += editText1.getText() + ",";
        allBills += editText2.getText();
        dataOfPagersEditor.putString("bills", allBills);
        dataOfPagersEditor.apply();
        localBills = Arrays.asList(allBills.split(","));
        billArrayList.add(new Bill(localBills.get(localBills.size() - 3), localBills.get(localBills.size() - 2), localBills.get(localBills.size() - 1)));

        //计算当前界面总金额
        Double totalCost = 0.0;
        for (int i = 0; i < localBills.size() / 3; i++) {
            totalCost += Double.parseDouble(localBills.get(i * 3 + 2));
        }

        String allTitles = dataOfTabsAndFragments.getString("titles", "食品饮料,衣服饰品,日常用品");
        List<String> localTitles = Arrays.asList(allTitles.split(","));

        billArrayList.add(new Bill("", localTitles.get(selectedTabPosition) + "共计:", totalCost.toString()));

        //将需要拿的对象放到list里 再return该list
        ArrayList arrayList = new ArrayList();
        arrayList.add(billArrayList);
        arrayList.add(allBills);
        return arrayList;
    }

    //清空账单
    public static String clearBills(MainActivity mainActivity, String allFragmentId, List<String> fragmentId, int deleteFragment) {
        //将被删除fragment中的内容从SharedPreferences清空 避免新建时内容覆盖
        SharedPreferences dataOfTabsAndFragments = mainActivity.getSharedPreferences("dataOfTabsAndFragments", MODE_PRIVATE);
        allFragmentId = dataOfTabsAndFragments.getString("fragmentId", "0,1,2");
        fragmentId = Arrays.asList(allFragmentId.split(","));
        SharedPreferences.Editor dataOfPagersEditor = mainActivity.getSharedPreferences(fragmentId.get(deleteFragment), MODE_PRIVATE).edit();
        dataOfPagersEditor.putString("bills", "");
        dataOfPagersEditor.apply();
        return allFragmentId;
    }
}
