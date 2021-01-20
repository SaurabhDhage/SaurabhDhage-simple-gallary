package com.gallary.viewer

import android.content.ContentUris
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), RCViewListener {
    private lateinit var pgbar:ProgressBar



    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)





        pgbar=findViewById(R.id.pgbar)
       val rcView=findViewById<RecyclerView>(R.id.rcView)
        rcView.layoutManager=GridLayoutManager(this, 3)
        val items=getMedia()
        Log.w("msg","much time required")
        pgbar.visibility=View.GONE
        val adpater=RcViewAdaptor(items, this)
        rcView.adapter=adpater
    }

    private fun showInContextUI() {
        Toast.makeText(this,"Permission is mendatory",Toast.LENGTH_LONG).show()
    }


    fun getMedia(): MutableList<Image>
    {
        // Load thumbnail of a specific media item.
            val imageList = mutableListOf<Image>()
            val projection = arrayOf(
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.SIZE

            )
            val selection = "${MediaStore.Images.Media.SIZE}"

            val sortOrder = "${MediaStore.Images.Media.DISPLAY_NAME} ASC"

            applicationContext.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                sortOrder
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val nameColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
                val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
                while (cursor.moveToNext()) {
                    // Use an ID column from the projection to get
                    // a URI representing the media item itself.

                    val id = cursor.getLong(idColumn)
                    val name = cursor.getString(nameColumn)
                    val size = cursor.getInt(sizeColumn)
                    GlobalScope.launch(Dispatchers.IO){
                    val contentUri: Uri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                    )



                    imageList.add(Image(contentUri, name, size,null))


                }

            }
        }

            Log.w("msg","much time required")
            return imageList

    }

    override fun onItemClicked(item: Image) {
       val intent=Intent(this, ImageViewer::class.java)
        intent.putExtra("uri", item.uri)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
       startActivity(intent)

    }
}