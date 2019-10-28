package com.pasarkaget.fajar.pasarkaget.ViewHolder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pasarkaget.fajar.pasarkaget.Interface.ItemClickListener;
import com.pasarkaget.fajar.pasarkaget.R;

public class PaymentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtBankName, txtMyBankName, txtBankAccountName, txtTotalTransfer, txtMetods, txtDate, txtNumberBank;
    private ItemClickListener itemClickListener;
    public Button confirmPayments;

    public PaymentsViewHolder(View itemView)
    {
        super(itemView);

        txtBankName = itemView.findViewById(R.id.payments_bank_destination);
        txtMyBankName = itemView.findViewById(R.id.payments_bank_name);
        txtBankAccountName = itemView.findViewById(R.id.payments_bank_account);
        txtTotalTransfer = itemView.findViewById(R.id.payments_total_transfer);
        txtMetods = itemView.findViewById(R.id.payments_bank_metods);
        txtDate = itemView.findViewById(R.id.payments_date);
        confirmPayments = itemView.findViewById(R.id.confirmPaymentsStatus);
        txtNumberBank = itemView.findViewById(R.id.payments_bank_number);
    }

    @Override
    public void onClick(View view)
    {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
