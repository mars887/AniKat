package daxo.services

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ClipBoardService @Inject constructor(
    @ApplicationContext context: Context
) {

    private val clipboard = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager


    fun pasteToClipboard( textTitle: String = "text",text: String) {
        clipboard.setPrimaryClip(ClipData.newPlainText(textTitle, text))
    }

    fun getCurrent(): String? {
        return if ((clipboard.primaryClip?.itemCount ?: -1) > 0) {
            return clipboard.primaryClip?.getItemAt(0)?.text.toString()
        } else null
    }
}