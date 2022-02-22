package com.example.videoapp

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import com.example.videoapp.databinding.ActivityMainBinding
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    private val STORAGE_PERMISSION_CODE: Int = 1000

    private lateinit var binding: ActivityMainBinding

//    var mydownloadId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.downloadButton.setOnClickListener{

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {

                    requestPermissions(
                        arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        STORAGE_PERMISSION_CODE
                    )

                }
                else {
                    startDownloading();
                }
            }
                else{
                    startDownloading()
                }

//            var request: DownloadManager.Request = DownloadManager.Request(
//                Uri.parse("https://steamledge.com/allonfasaha/assets/video/new/number/1/1/21_30.mp4")
//            )
//                .setTitle("Allonfasaha")
//                .setDescription("Numeracy/Numbers")
//                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
//                .setAllowedOverMetered(true)
//
//            var dm: DownloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
//            mydownloadId = dm.enqueue(request)
//        }
//
//        var br = object: BroadcastReceiver(){
//            override fun onReceive(p0: Context?, p1: Intent?) {
//                var id: Long? = p1?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
//                if (id==mydownloadId) {
//                    Toast.makeText(applicationContext,"Video Download Successfully", Toast.LENGTH_LONG).show()
//                }
//            }
        }

//        registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    private fun startDownloading() {
        val request: DownloadManager.Request = DownloadManager.Request(
            Uri.parse("https://steamledge.com/allonfasaha/assets/video/new/number/1/1/21_30.mp4")
        )
            .setTitle("Allonfasaha")
            .setDescription("The file is downloading...")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setAllowedOverMetered(true)

            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "try.mp4")

        val manager: DownloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            STORAGE_PERMISSION_CODE ->{
                if (grantResults.isNotEmpty() && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    startDownloading()
                }
                else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}