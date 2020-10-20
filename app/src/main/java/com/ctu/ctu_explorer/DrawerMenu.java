package com.ctu.ctu_explorer;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

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
                                new SecondaryDrawerItem().withName(R.string.third_subitem2),
                                new SecondaryDrawerItem().withName(R.string.sub_item_3)
                        ),
                new DividerDrawerItem(),
                new PrimaryDrawerItem().withName(R.string.drawer_item_4).withIcon(R.drawable.ic_report_problem_black_24dp)
        );
        drawerBuilder.withSelectedItem(-1);
        drawerBuilder.withHeader(R.layout.drawer_header);
        drawerBuilder.withStickyFooter(R.layout.drawer_footer);
        drawerBuilder.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                switch (position) {
                    case 1: // About
                        view.getContext().startActivity(new Intent(view.getContext(), About.class));
                        return true;
                    case 2: // Instructions
//                        view.getContext().startActivity(new Intent(view.getContext(), About.class));
                        return true;
                    case 3: // Building info
                        return true;
                    case 4: // CICT
                        view.getContext().startActivity(new Intent(view.getContext(), BuildingsActivity.class));
                        return true;
                    case 5: // LAW
                        view.getContext().startActivity(new Intent(view.getContext(), BuildingsActivity.class));
                        return true;
                    case 6: // CE
                        view.getContext().startActivity(new Intent(view.getContext(), BuildingsActivity.class));
                        return true;
                    case 8: // Report problem
                        view.getContext().startActivity(new Intent(view.getContext(), ReportForm.class));
                        return true;
                    default:
                        return false;
                }
            }
        });
        drawer = drawerBuilder.build();
    }

    public static void close() {
        drawer.closeDrawer();
    }

    public static void open() {
        drawer.openDrawer();
    }
}
