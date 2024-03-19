package com.example.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private static final String CHANNEL_ID = "CHANNEL_ID";
    private static final int NOTIFICATION_ID = 123;
    Context context;
    ArrayList<User> list;
    private String specificValue = "Done";

    public MyAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.userentry,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    User user = list.get(position);
    holder.name.setText(user.getName());
    holder.status.setText(user.getStatus());
    holder.items.setText(user.getItems());



        String[] ageValues = user.getItems().split(", ");

        StringBuilder formattedAge = new StringBuilder();
        for (String ageValue : ageValues) {
            formattedAge.append(ageValue).append("\n");
        }
        holder.items.setText(formattedAge.toString().trim());




        if (user.getStatus().equals(specificValue)) {
            // Change the background color or any other view properties here

            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.my_color));
            showNotification("Your Shopping List is Ready!", "Your requested items are now available at Smart Shop. Visit us to collect your items conveniently");

        } else {
            //holder.itemView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));

        }
    }

    private void showNotification(String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel Name";
            String description = "Channel Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,status,items;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textname);
            status = itemView.findViewById(R.id.textemail);
            items = itemView.findViewById(R.id.textage);

        }
    }
}
