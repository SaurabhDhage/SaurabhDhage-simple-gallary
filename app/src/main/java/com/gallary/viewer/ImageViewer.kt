package com.gallary.viewer


import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ImageViewer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_viewer)
        val image=findViewById<ImageView>(R.id.image)
        val intent=getIntent()
        val uri= intent.getParcelableExtra<Uri>("uri");
        image.setImageURI(uri)

    }

    override fun onBackPressed() {
        super.onBackPressed()
       return;
    }

}