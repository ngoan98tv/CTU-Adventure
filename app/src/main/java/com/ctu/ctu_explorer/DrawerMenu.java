package com.ctu.ctu_explorer;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DrawerMenu {
    private static Drawer drawer;
    private static Buildings buildings;

    private static List<IDrawerItem> buildingItems() {
        List<IDrawerItem> items = new ArrayList<>();
        for (int i = 1; i < buildings.getCodes().size(); i++) {
            String name = buildings.getNames().get(i);
            String code = buildings.getCodes().get(i);
            items.add(new SecondaryDrawerItem().withName(name).withIdentifier(code.hashCode()));
        }
        return items;
    }

    public static void create(Activity activity) {
        buildings = new Buildings(activity);
        DrawerBuilder drawerBuilder = new DrawerBuilder();
        drawerBuilder.withActivity(activity);
        drawerBuilder.addDrawerItems(
                new PrimaryDrawerItem()
                        .withName(R.string.first_draweritem)
                        .withIcon(R.drawable.ic_info_black_24dp)
                        .withIdentifier(1),
                new PrimaryDrawerItem()
                        .withName(R.string.second_draweritem)
                        .withIcon(R.drawable.ic_assignment_black_24dp)
                        .withIdentifier(2),
                new PrimaryDrawerItem()
                        .withName(R.string.third_draweritem)
                        .withIcon(R.drawable.ic_business_black_24dp)
                        .withIdentifier(3)
                        .withSubItems(buildingItems()),
                new DividerDrawerItem(),
                new PrimaryDrawerItem()
                        .withName(R.string.drawer_item_4)
                        .withIcon(R.drawable.ic_report_problem_black_24dp)
                        .withIdentifier(4)
        );
        drawerBuilder.withSelectedItem(-1);
        drawerBuilder.withHeader(R.layout.drawer_header);
        drawerBuilder.withStickyFooter(R.layout.drawer_footer);
        drawerBuilder.withOnDrawerItemClickListener((view, position, drawerItem) -> {
            switch ((int) drawerItem.getIdentifier()) {
                case 1: // About
                    view.getContext().startActivity(new Intent(view.getContext(), About.class));
                    return true;
                case 2: // Instructions
//                        view.getContext().startActivity(new Intent(view.getContext(), About.class));
                    return true;
                case 3:
                    return false;
                case 4: // Report problem
                    view.getContext().startActivity(new Intent(view.getContext(), ReportForm.class));
                    return true;
                default: // Resolve building code and start building detail activity
                    long id = drawerItem.getIdentifier();
                    String itemCode = null;
                    Iterator<String> codeIterator = buildings.getCodes().iterator();
                    while (codeIterator.hasNext() && itemCode == null) {
                        String item = codeIterator.next();
                        if (item.hashCode() == id) itemCode = item;
                    }
                    Intent intent = new Intent(view.getContext(), BuildingsActivity.class);
                    intent.putExtra("code", itemCode);
                    view.getContext().startActivity(intent);
                    return true;
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
