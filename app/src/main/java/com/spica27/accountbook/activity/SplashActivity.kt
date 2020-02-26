package com.spica27.accountbook.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.spica27.accountbook.BaseActivity
import com.spica27.accountbook.MYACTIVITYS
import com.spica27.accountbook.db.bean.User
import com.spica27.accountbook.db.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //开屏初始化
        val activity = MYACTIVITYS.find { it.activityClass == MainActivity::class }
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(BaseActivity.ACTIVITY_ID, activity?.id)
        val userRepository = UserRepository(this.application)
        //创建一个主线程协程
        val coroutineScope = CoroutineScope(Dispatchers.Main)
        coroutineScope.launch {

            val user = userRepository.getUser()

            if (user == null) {
                //第一次启动
                userRepository.initUser(
                    User(
                        1,
                        Calendar.getInstance().get(Calendar.MONTH),
                        0.0,
                        0.0,0
                    )
                )
            } else {
                //不是第一次启动
                if (user.month != Calendar.getInstance().get(Calendar.MONTH)) {
                    //月份变化了
                    user.income = 0.0
                    user.outCome = 0.0
                    userRepository.updateUser(user)
                }

            }


            startActivity(intent)
            finish()
        }


    }
}