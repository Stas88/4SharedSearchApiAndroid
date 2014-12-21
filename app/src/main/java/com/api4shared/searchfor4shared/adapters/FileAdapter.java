package com.api4shared.searchfor4shared.adapters;

import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.api4shared.searchfor4shared.R;
import com.api4shared.searchfor4shared.models.IFileModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapter for Files from search
 */
public class FileAdapter extends ArrayAdapter<IFileModel> {


    private final Context context;
    private final List<IFileModel> fileModels;
    private LayoutInflater inflater;
    private Fragment fragment;


    public FileAdapter(Context context, List<IFileModel> fileModels, Fragment fragment) {
        super(context, R.layout.files_list_item, fileModels);
        this.context = context;
        this.fileModels = fileModels;
        this.fragment = fragment;
        inflater = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (null == rowView) {
            rowView = inflater.inflate(R.layout.files_list_item, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.thumbnail = (ImageView) rowView.findViewById(R.id.file_thumbnail);
            viewHolder.name = (TextView) rowView.findViewById(R.id.file_name);
            viewHolder.link = (TextView) rowView.findViewById(R.id.file_link);

            rowView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) rowView.getTag();

        IFileModel fileModel = fileModels.get(position);
        String fileName = fileModel.getName();
        String link = fileModel.getDownloadPage();
        holder.name.setText(fileName);
        holder.link.setText(link);
        String imageUrl = fileModel.getThumbnailUrl();


        // Load the file thumbnail image on a background thread
        if(imageUrl != null) {
            Picasso.with(context)
                    .load(imageUrl).centerCrop().resize(100,100)
                    .into(holder.thumbnail);
        }

        return rowView;
    }

    private static class ViewHolder {
        public TextView name;
        public TextView link;
        public ImageView thumbnail;


    }

    public List<IFileModel> getFileModelList()
    {
        return fileModels;
    }

}

