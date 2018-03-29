package com.carteresto.igr230.carteresto.MenuDetail;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.carteresto.igr230.carteresto.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by paul-elian on 26/03/18.
 */

public class DishesListAdapter extends BaseExpandableListAdapter
implements View.OnClickListener {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<DishDataModel>> _listDataChild ;
    private AlertDialog.Builder dialogBuilder ;
    private MenuDetailActivity parent ;

    // Cached data of a view item : one dish in practice
    private static class ViewHolder {
        ImageButton dishPreview ;
        TextView dishName ;
        Button dishLessQuantity, dishMoreQuantity ;
        TextView dishValueQuantity ;
    }

    // Use : get item group and position in the group. Associated to each item view.
    private class Position {
        private Integer childPosition ;
        private Integer groupPosition ;

        public Position(Integer childPosition, Integer groupPosition) {
            this.childPosition = childPosition ;
            this.groupPosition = groupPosition ;
        }

        public Integer getChildPosition() {
            return childPosition;
        }

        public Integer getGroupPosition() {
            return groupPosition;
        }
    }

    public DishesListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<DishDataModel>> listChildData) {
        this._context = context;
        this.parent = (MenuDetailActivity)_context ;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        // DialogBuilder is useful to notify problems in quantity bw number of menus
        // & number of chosen dishes per step.
        this.dialogBuilder = new AlertDialog.Builder(_context) ;
        this.dialogBuilder.setPositiveButton(R.string.menu_detail_more_impossible_positive_btn, new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                             // nothing special to do there
                         }
                     });
    }

    /**
     * Called each time the user wants to increment / decrement the quantity of a given dish
     * while choosing.
     * @param v The dish item view
     */
    @Override
    public void onClick(View v) {
        Position p = (Position) v.getTag() ;
        Object object = getChild(p.getGroupPosition(), p.getChildPosition()) ;
        DishDataModel dataModel = (DishDataModel) object ;
        int maxQuantity = ((MenuDetailActivity)_context).getMaxQuantity() ;

        switch(v.getId()) {
            case R.id.menu_detail_dish_less_btn :
                dataModel.decrementNumber() ;
                notifyDataSetChanged();
                break ;
            case R.id.menu_detail_dish_more_btn :
                String currentHeader = _listDataHeader.get(p.getGroupPosition()) ;
                List<DishDataModel> currentDishesList = _listDataChild.get(currentHeader) ;
                int curQuantity = 0 ;
                for(DishDataModel data : currentDishesList) {
                    curQuantity += data.getNumber() ;
                }
                if(curQuantity < maxQuantity) {
                    dataModel.incrementNumber() ;
                    notifyDataSetChanged();
                }
                else {
                    String message = parent.getString(R.string.menu_detail_more_impossible_message, maxQuantity) ;
                    dialogBuilder.setTitle(parent.getString(R.string.menu_detail_more_impossible))
                            .setMessage(message) ;
                    AlertDialog dialog = dialogBuilder.create() ;
                    dialog.show() ;
                }
                break ;
        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    // Links each item view with its data, stored into the dishData attribute
    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        DishDataModel dishData = (DishDataModel)getChild(groupPosition, childPosition) ;
        ViewHolder viewHolder ;

        final View result ;

        if (convertView == null) {
            viewHolder = new ViewHolder() ;
            LayoutInflater inflater = LayoutInflater.from(_context) ;
            convertView = inflater.inflate(R.layout.activity_menu_detail_list_item, parent, false) ;
            viewHolder.dishPreview = (ImageButton) convertView.findViewById(R.id.menu_detail_dish_preview) ;
            viewHolder.dishName = (TextView) convertView.findViewById(R.id.menu_detail_dish_title) ;
            viewHolder.dishLessQuantity = (Button) convertView.findViewById(R.id.menu_detail_dish_less_btn) ;
            viewHolder.dishMoreQuantity = (Button) convertView.findViewById(R.id.menu_detail_dish_more_btn) ;
            viewHolder.dishValueQuantity = (TextView) convertView.findViewById(R.id.menu_detail_dish_quantity_value) ;
            result = convertView ;
            convertView.setTag(viewHolder) ;
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag() ;
            result = convertView ;
        }

        viewHolder.dishPreview.setImageBitmap(dishData.getPreview()) ;
        viewHolder.dishName.setText(dishData.getTitle()) ;
        viewHolder.dishValueQuantity.setText(String.format(Locale.FRANCE, "%d", dishData.getNumber())) ;
        viewHolder.dishMoreQuantity.setOnClickListener(this) ;
        viewHolder.dishMoreQuantity.setTag(new Position(childPosition, groupPosition)) ;
        viewHolder.dishLessQuantity.setOnClickListener(this) ;
        viewHolder.dishLessQuantity.setTag(new Position(childPosition, groupPosition)) ;

        //txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.activity_menu_detail_list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.menu_detail_list_group);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /**
     * Used to verify if we are actually able to decrement the number of menus
     * while keeping data coherent (number of dishes per step).
     * @return true if operation succeeded
     */
    public boolean decrementMenusNumber() {
        for (String header : this._listDataHeader) {
            int sum = 0 ;
            List<DishDataModel> dishes = this._listDataChild.get(header) ;
            for (DishDataModel dish : dishes) {
                sum += dish.getNumber() ;
            }
            if(sum > parent.getMaxQuantity()-1) {
                String message = parent.getString(R.string.menu_detail_less_menus_impossible_message, header) ;
                CharSequence styledMessage = Html.fromHtml(message) ;
                dialogBuilder.setTitle(R.string.menu_detail_less_menus_impossible)
                        .setMessage(styledMessage) ;
                AlertDialog errorDialog = dialogBuilder.create() ;
                errorDialog.show() ;
                return false ;
            }
        }
        return true ;
    }

    public boolean isChoiceComplete() {
        for(String header : this._listDataHeader) {
            int sum = 0 ;
            List<DishDataModel> dishes = this._listDataChild.get(header) ;
            for (DishDataModel dish : dishes) {
                sum+= dish.getNumber() ;
            }
            if(sum < parent.getMaxQuantity()) {
                String message = parent.getString(R.string.menu_detail_validate_error_message, header) ;
                dialogBuilder.setTitle(R.string.menu_detail_validate_error_title)
                        .setMessage(message) ;
                AlertDialog errorDialog = dialogBuilder.create() ;
                errorDialog.show() ;
                return false ;
            }
        }
        return true ;
    }
}
