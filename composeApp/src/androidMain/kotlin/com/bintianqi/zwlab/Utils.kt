package com.bintianqi.zwlab

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.content.ContextCompat.startActivity

fun copyToClipBoard(context: Context, string: String):Boolean{
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    try {
        if(Build.VERSION.SDK_INT>=23){
            val hasPermission: Boolean = clipboardManager.hasPrimaryClip()
            if(!hasPermission) {
                val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                intent.setData(Uri.parse("package:"+context.packageName))
                startActivity(context,intent,null)
            }
        }
        clipboardManager.setPrimaryClip(ClipData.newPlainText("", string))
    }catch(e:Exception){
        return false
    }
    return true
}
