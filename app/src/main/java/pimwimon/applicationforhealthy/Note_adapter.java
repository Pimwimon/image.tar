package pimwimon.applicationforhealthy;

/**
 * Created by Pannawit on 30/7/2560.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import com.squareup.picasso.Picasso;
import android.graphics.Typeface;

public class Note_adapter extends BaseAdapter{
    public static final String TAG = Note_adapter.class.getSimpleName();
    public static final HashMap<String, Integer> LABEL_COLORS = new HashMap<String, Integer>()
    {{
        put("Low-Carb", R.color.colorLowCarb);
        put("Low-Fat", R.color.colorLowFat);
        put("Low-Sodium", R.color.colorLowSodium);
        put("Medium-Carb", R.color.colorMediumCarb);
        put("Vegetarian", R.color.colorVegetarian);
        put("Balanced", R.color.colorBalanced);
    }};

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Note_recipe> mDataSource;


    public Note_adapter(Context context, ArrayList<Note_recipe> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return mDataSource.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Note_adapter.ViewHolder holder;

        // check if the view already exists if so, no need to inflate and findViewById again!
        if (convertView == null) {

            // Inflate the custom row layout from your XML.
            convertView = mInflater.inflate(R.layout.list_item_note_recipe, parent, false);

            // create a new "Holder" with subviews
            holder = new Note_adapter.ViewHolder();
            holder.thumbnailImageView = (ImageView) convertView.findViewById(R.id.recipe_list_thumbnail);
            holder.idTextView = (TextView) convertView.findViewById(R.id.recipe_list_id);
            holder.titleTextView = (TextView) convertView.findViewById(R.id.recipe_list_title);
            holder.subtitleTextView = (TextView) convertView.findViewById(R.id.recipe_list_subtitle);
            holder.detailTextView = (TextView) convertView.findViewById(R.id.recipe_list_detail);

            // hang onto this holder for future recyclage
            convertView.setTag(holder);
        }
        else {

            // skip all the expensive inflation/findViewById and just get the holder you already made
            holder = (Note_adapter.ViewHolder) convertView.getTag();
        }

        // Get relevant subviews of row view
        TextView idTextView = holder.idTextView;
        TextView titleTextView = holder.titleTextView;
        TextView subtitleTextView = holder.subtitleTextView;
        TextView detailTextView = holder.detailTextView;
        ImageView thumbnailImageView = holder.thumbnailImageView;

        //Get corresponding recipe for row
        Note_recipe Note_recipe = (Note_recipe) getItem(position);

        // Update row view's textviews to display recipe information
        idTextView.setText(Note_recipe.id);
        titleTextView.setText(Note_recipe.title);
        subtitleTextView.setText(Note_recipe.description);
        detailTextView.setText(Note_recipe.label);

        // Use Picasso to load the image. Temporarily have a placeholder in case it's slow to load
        Picasso.with(mContext).load(Note_recipe.imageUrl).placeholder(R.mipmap
                .ic_launcher).into(thumbnailImageView);

        return convertView;
    }

    private static class ViewHolder {
        public TextView idTextView;
        public TextView titleTextView;
        public TextView subtitleTextView;
        public TextView detailTextView;
        public ImageView thumbnailImageView;
    }

}
