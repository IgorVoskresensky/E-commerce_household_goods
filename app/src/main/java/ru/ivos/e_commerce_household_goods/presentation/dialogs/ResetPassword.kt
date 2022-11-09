package ru.ivos.e_commerce_household_goods.presentation.dialogs

import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import ru.ivos.e_commerce_household_goods.R

fun Fragment.setBottomSheetDialog(
    onSendClick: (String) -> Unit,
) {

    val dialog = BottomSheetDialog(requireContext(), R.style.DialogStyle)
    val view = layoutInflater.inflate(R.layout.reset_password_dialog, null)
    dialog.setContentView(view)
    dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    dialog.show()

    val etEmail = view.findViewById<EditText>(R.id.etResetPasswordEmail)
    val btnCancel = view.findViewById<AppCompatButton>(R.id.btnResetCancel)
    val btnConfirm = view.findViewById<AppCompatButton>(R.id.btnResetConfirm)

    btnConfirm.setOnClickListener {
        val email = etEmail.text.toString().trim()
        onSendClick(email)
        dialog.dismiss()
    }

    btnCancel.setOnClickListener {
        dialog.dismiss()
    }
}