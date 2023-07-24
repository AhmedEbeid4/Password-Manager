package com.ebeid.passwordmanager.presentation.adapters

import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.ebeid.passwordmanager.R

@BindingAdapter("titleFirstChar")
fun getFirstChar(textView: TextView, text: String) {
    textView.text = text[0].toString().uppercase()
}

@BindingAdapter("copyRandomB")
fun setButton(button: ImageView, boolean: Boolean) {
    if (boolean) {
        button.setImageResource(R.drawable.baseline_question_mark_24)
    } else {
        button.setImageResource(R.drawable.baseline_content_copy_24)
    }
}

@BindingAdapter("canEdit")
fun editAbility(et: EditText,boolean: Boolean){
    et.isFocusable = boolean;
    et.isCursorVisible = boolean;
}