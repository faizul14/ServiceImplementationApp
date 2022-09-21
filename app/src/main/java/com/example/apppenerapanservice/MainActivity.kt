package com.example.apppenerapanservice

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.apppenerapanservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            startS.setOnClickListener(this@MainActivity)
            stopS.setOnClickListener(this@MainActivity)
            startSf.setOnClickListener(this@MainActivity)
            stopSf.setOnClickListener(this@MainActivity)
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.start_s->{
                startSevices(true)
            }
            R.id.stop_s->{
                startSevices(false)
            }
            R.id.start_sf->{
                startForeGroundServices(true)
            }
            R.id.stop_sf->{
                startForeGroundServices(false)
            }


        }
    }

    private fun startSevices(status : Boolean){
        val service = Intent(this, MyBackgroundService::class.java)
        when(status){
            true -> {
                startService(service)
            }
            false -> {
                stopService(service)
            }

        }
    }

    private fun startForeGroundServices(status: Boolean){
        val service = Intent(this, MyForeGroundService::class.java)
        when(status){
            true -> {
                if (Build.VERSION.SDK_INT >= 26){
                    startForegroundService(service)
                }else{
                    startService(service)
                }
            }
            false -> {
                stopService(service)
            }
        }
    }
}