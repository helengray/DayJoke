package com.helen.dayjoke.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.helen.dayjoke.R;
import com.helen.dayjoke.entity.Mito;
import com.helen.dayjoke.ui.adapter.JokePagerAdapter;

/**
 * Created by Helen on 2016/4/29.
 *
 */
public class WelfareContentFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.welfare_content_fragment,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager_child);
        JokePagerAdapter pagerAdapter = new JokePagerAdapter(getFragmentManager());
        pagerAdapter.addFragment(WelfareFragment.newInstance(0),getString(R.string.sui_ji));
        pagerAdapter.addFragment(WelfareFragment.newInstance(Mito.TYPE_DA_MEI),getString(R.string.da_mei));
        pagerAdapter.addFragment(WelfareFragment.newInstance(Mito.TYPE_XIAO_QING_XIN),getString(R.string.xiao_qing_xin));
        pagerAdapter.addFragment(WelfareFragment.newInstance(Mito.TYPE_WEN_YI),getString(R.string.wen_yi));
        pagerAdapter.addFragment(WelfareFragment.newInstance(Mito.TYPE_XING_GAN),getString(R.string.xing_gan));
        pagerAdapter.addFragment(WelfareFragment.newInstance(Mito.TYPE_DA_CHANGG_TUI),getString(R.string.da_chang_tui));
        pagerAdapter.addFragment(WelfareFragment.newInstance(Mito.TYPE_HEI_SI),getString(R.string.hei_si));
        pagerAdapter.addFragment(WelfareFragment.newInstance(Mito.TYPE_XIAO_QIAO_TUN),getString(R.string.xiao_qiao_tun));
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(4);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
