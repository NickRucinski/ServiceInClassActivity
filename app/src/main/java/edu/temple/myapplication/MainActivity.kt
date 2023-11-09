package edu.temple.myapplication

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import java.sql.Time

class MainActivity : AppCompatActivity() {
    private lateinit var timerBinder: TimerService.TimerBinder
    private var mBound: Boolean = false
    lateinit var timerTextView: TextView
    val timerHandler = Handler(Looper.getMainLooper()){
        timerTextView.text = it.what.toString()
        true
    }

    val connection = object : ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            timerBinder = service as TimerService.TimerBinder
            timerBinder.setHandler(timerHandler)
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

        timerTextView = findViewById<TextView>(R.id.textView)

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
                timerTextView.text = ""
            }
        }
    }
}