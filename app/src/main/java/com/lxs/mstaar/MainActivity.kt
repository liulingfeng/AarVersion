package com.lxs.mstaar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.lxs.career.CareerActivity
import com.lxs.pay.TestActivity
import kotlinx.android.synthetic.main.activity_main.*
import top.zibin.luban.Luban
import java.lang.Exception
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.locks.ReentrantLock

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Luban.with(this)
        tv.setOnClickListener {
            val intent = Intent(this, TestActivity::class.java)
            startActivity(intent)
        }

        tv2.setOnClickListener {
            val intent = Intent(this, CareerActivity::class.java)
            startActivity(intent)
        }
    }

    public @Synchronized
    fun getName(): String {
        return "德玛西亚娶你的"
    }

    private var lock: ReentrantLock = ReentrantLock()
    public fun modifyPublicResources() {
        try {
            lock.lock()
            //操作同步资源
        } catch (e: Exception) {

        } finally {
            lock.unlock()
        }
    }

    private var atomicInteger: AtomicInteger = AtomicInteger()
    fun test(): Int {
        return atomicInteger.incrementAndGet()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.e("德玛","去除存储数据")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.e("德玛","存储")
    }
}
