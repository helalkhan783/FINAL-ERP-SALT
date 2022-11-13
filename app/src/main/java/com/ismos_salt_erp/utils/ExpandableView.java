package com.ismos_salt_erp.utils;

import android.content.Context;

import net.cachapa.expandablelayout.ExpandableLayout;

public class ExpandableView {
    private ExpandableLayout expandableLayout;
    private Context context;

    public ExpandableView(Context context, ExpandableLayout expandableLayout) {
        this.context = context;
        this.expandableLayout = expandableLayout;
    }

    public boolean response() {
        if (expandableLayout.isExpanded()) {
            expandableLayout.setExpanded(false);
            return false;
        }
        expandableLayout.setExpanded(true);
        return true;
    }

}
