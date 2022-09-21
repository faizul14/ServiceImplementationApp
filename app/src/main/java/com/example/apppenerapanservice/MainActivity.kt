package com.example.apppenerapanservice

import android.content.Intent
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
}