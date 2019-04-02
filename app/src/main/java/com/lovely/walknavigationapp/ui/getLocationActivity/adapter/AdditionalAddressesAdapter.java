package com.lovely.walknavigationapp.ui.getLocationActivity.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.lovely.walknavigationapp.data.model.directionResult.Location;
import com.lovely.walknavigationapp.databinding.ItemAdditionalAddressBinding;

import java.util.ArrayList;
import java.util.List;

public class AdditionalAddressesAdapter extends RecyclerView.Adapter<AdditionalAddressesAdapter.AddressViewHolder> {

    private List<Location> additionalAddresses;

    public AdditionalAddressesAdapter(List<Location> additionalAddresses) {
        this.additionalAddresses = additionalAddresses;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        ItemAdditionalAddressBinding itemActivityListBinding = ItemAdditionalAddressBinding.inflate(layoutInflater, viewGroup, false);
        return new AdditionalAddressesAdapter.AddressViewHolder(itemActivityListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder addressViewHolder, int i) {

        ((AddressViewHolder) addressViewHolder).bindTo(additionalAddresses.get(addressViewHolder.getAdapterPosition()).getFormattedAddress());
    }

    @Override
    public int getItemCount() {

        Log.e("itemCount", "" + additionalAddresses.size());
        return additionalAddresses != null ? additionalAddresses.size() : 0;
    }

    /**
     * add the addresses..
     */
    public void addTheAddresses(List<Location> additionalAddresses) {

        additionalAddresses = new ArrayList<>();
        this.additionalAddresses.addAll(additionalAddresses);

        notifyDataSetChanged();
    }


    public class AddressViewHolder extends RecyclerView.ViewHolder {

        private ItemAdditionalAddressBinding binding;

        public AddressViewHolder(ItemAdditionalAddressBinding itemActivityListBinding) {
            super(itemActivityListBinding.getRoot());

            this.binding = itemActivityListBinding;
        }

        public void bindTo(String formattedString) {

            // displaying name in the entry
            binding.setFormattedAddressString(formattedString);

        }
    }

}
