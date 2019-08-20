package com.ctu.ctu_explorer;

import android.app.Activity;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;

public class DrawerMenu {
    private static Drawer drawer;

    public static void create(Activity activity) {
        DrawerBuilder drawerBuilder = new DrawerBuilder();
        drawerBuilder.withActivity(activity);
        drawerBuilder.addDrawerItems(
                new PrimaryDrawerItem().withName(R.string.first_draweritem).withIcon(R.drawable.ic_info_black_24dp),
                new PrimaryDrawerItem().withName(R.string.second_draweritem).withIcon(R.drawable.ic_assignment_black_24dp),
                new PrimaryDrawerItem().withName(R.string.third_draweritem).withIcon(R.drawable.ic_business_black_24dp)
                        .withSubItems(
                                new SecondaryDrawerItem().withName(R.string.third_subitem1),
                                new SecondaryDrawerItem().withName(R.string.third_subitem2)
                        ),
                new DividerDrawerItem(),
                new PrimaryDrawerItem().withName(R.string.fourth_draweritem).withIcon(R.drawable.ic_report_problem_black_24dp)
        );
        drawerBuilder.withSelectedItem(-1);
        drawerBuilder.withHeader(R.layout.drawer_header);
        drawerBuilder.withStickyFooter(R.layout.drawer_footer);
        drawer = drawerBuilder.build();
    }

    public static void close() {
        drawer.closeDrawer();
    }

    public static void open() {
        drawer.openDrawer();
    }
}
