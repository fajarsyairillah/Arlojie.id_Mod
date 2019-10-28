package com.pasarkaget.fajar.pasarkaget;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

public class CopyButtonLibrary {
    private ClipboardManager myClipboard;
    private ClipData myClip;
    private Context context;
    private TextView textView;

    public CopyButtonLibrary(Context context,TextView textView)
    {
     this.context = context;
     this.textView = textView;
    }

    public void init()
    {
      myClipboard= (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
      String text;
      text = textView.getText().toString();

      myClip = ClipData.newPlainText("text", text);
      myClipboard.setPrimaryClip(myClip);

        Toast.makeText(context, "No Resi Tersalin", Toast.LENGTH_SHORT).show();
    }
}
