package com.example.proj1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private List<ImageItem> imageList;
    private OnItemClickListener onItemClickListener;

    public ImageAdapter(List<ImageItem> imageList) {
        this.imageList = imageList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_button_view, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        ImageItem objmodel=imageList.get(position);
        holder.imageButton.setTag(objmodel.getImageDes());
        holder.imageButton.setImageBitmap(objmodel.getOurImage());
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        TextView imageDesTV;
        ImageView OurImageTV;
        ImageButton imageButton;
        public ImageViewHolder(View itemView){
                super(itemView);

                imageButton = itemView.findViewById(R.id.image_button);

                // Set the click listener to the imageButton only
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get the position of the item that was clicked
                        int position = getAdapterPosition();

                        // Check if the position is valid
                        if (position != RecyclerView.NO_POSITION) {
                            // Get the ImageItem object at the given position
                            ImageItem clickedItem = imageList.get(position);

                            // Create an Intent to start the third activity
                            Intent intent = new Intent(itemView.getContext(), ThirdActivity.class);

                            // Add any necessary data to the Intent
                            intent.putExtra("imageResource", clickedItem.getImageDes());

                            // Start the third activity
                            itemView.getContext().startActivity(intent);
                        }
                    }
                });
            }
    }
}
