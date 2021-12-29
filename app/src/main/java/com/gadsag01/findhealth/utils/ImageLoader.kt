package com.gadsag01.findhealth.utils

import android.graphics.BitmapFactory
import android.widget.ImageView
import coil.load as loadCoil

suspend fun ImageView.load(data: ByteArray) {
    this.loadCoil(BitmapFactory.decodeByteArray(data, 0, data.size))
}