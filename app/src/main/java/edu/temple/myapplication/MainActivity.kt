package edu.temple.myapplication

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import java.sql.Time

class MainActivity : AppCompatActivity() {
    private lateinit var timerBinder: TimerService.TimerBinder
    private var mBound: Boolean = false

    val connection = object : ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            timerBinder = service as TimerService.TimerBinder
            mBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mBound = false
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindService(
            Intent(this, TimerService::class.java),
            connection,
            BIND_AUTO_CREATE
        )

        findViewById<Button>(R.id.startButton).setOnClickListener {
            if(mBound){
                timerBinder.start(100)
            }
        }

        findViewById<Button>(R.id.pauseButton).setOnClickListener {
            if(mBound){
                timerBinder.pause()
            }
        }
        
        findViewById<Button>(R.id.stopButton).setOnClickListener {
            if(mBound){
                timerBinder.stop()
            }
        }
    }
}