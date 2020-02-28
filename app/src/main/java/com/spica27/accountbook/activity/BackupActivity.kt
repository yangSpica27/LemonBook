package com.spica27.accountbook.activity

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.spica27.accountbook.BaseActivity
import com.spica27.accountbook.DATABASE_NAME
import com.spica27.accountbook.R
import com.spica27.accountbook.layout.BackupsLayout
import com.spica27.accountbook.utils.FileTools
import com.thegrizzlylabs.sardineandroid.Sardine
import com.thegrizzlylabs.sardineandroid.impl.OkHttpSardine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream

class BackupActivity : BaseActivity(), View.OnClickListener {

    override val contentLayoutId: Int
        get() = -1


    //不需要xml布局
    override val contentNeedsFindlayout: Boolean
        get() = false

    private lateinit var etHost: EditText
    private lateinit var etAccount: EditText
    private lateinit var etpassword: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val backupsLayout = BackupsLayout(this)
        val lpcontainer = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT
        )
        backupsLayout.layoutParams = lpcontainer
        container.addView(backupsLayout)
        initView()

    }

    private fun initView() {
        val itemsave = findViewById<View>(R.id.item_save_db)
        itemsave.setOnClickListener(this)
        val itemBack = findViewById<View>(R.id.item_back_db)
        itemBack.setOnClickListener(this)
        etHost = findViewById(R.id.et_hosts)
        etAccount = findViewById(R.id.et_account)
        etpassword = findViewById(R.id.et_password)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.item_back_db -> {
                val coroutineScope = CoroutineScope(Dispatchers.Main)
                coroutineScope.launch {
                    //创建请求对象
                    val sardine = initSardine()
                    if (isHasRoute(sardine)) {
                        val inputStream = getDbFile(sardine)
                        if (inputStream != null) {
                            val result =
                                FileTools.saveFile(inputStream, getDatabasePath(DATABASE_NAME).path)
                            Toast.makeText(this@BackupActivity, result, Toast.LENGTH_SHORT).show()

                        } else {
                            Toast.makeText(this@BackupActivity, "获取备份文件失败", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        Toast.makeText(this@BackupActivity, "获取备份文件失败", Toast.LENGTH_SHORT).show()
                    }


                }

            }
            R.id.item_save_db -> {
                val coroutineScope = CoroutineScope(Dispatchers.Main)
                coroutineScope.launch {
                    //创建请求对象
                    val sardine = initSardine()
                    //删除原备份
                    if (isHasRoute(sardine)) {
                        deleteBackup(sardine)
                    }
                    //获取数据库文件
                    val dbFile = getDatabasePath(DATABASE_NAME)
                    if (dbFile != null) {
                        //上传文件
                        val data: ByteArray = dbFile.readBytes()
                        upDbFile(data, sardine)
                        Toast.makeText(this@BackupActivity, "备份完成", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@BackupActivity, "获取数据库文件失败", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    suspend fun initSardine(): Sardine = withContext(Dispatchers.Default) {
        val sardine = OkHttpSardine()
        sardine.setCredentials(etAccount.text.toString().trim(), etpassword.text.toString().trim())
        return@withContext sardine
    }

    suspend fun isHasRoute(sardine: Sardine): Boolean = withContext(Dispatchers.IO) {
        //判断是否有该目录
        val ishas = (sardine.exists("${etHost.text.toString()}lemonBook/back.db"))
        if (!ishas) {
            //如果没有目录就创建
            sardine.createDirectory("${etHost.text.toString()}lemonBook/")
        }

        return@withContext ishas
    }

    //删除文件
    suspend fun deleteBackup(sardine: Sardine) = withContext(Dispatchers.IO) {
        sardine.delete("${etHost.text.toString()}lemonBook/back.db")
    }

    //上传文件
    suspend fun upDbFile(fis: ByteArray, sardine: Sardine) = withContext(Dispatchers.IO) {
        sardine.put("${etHost.text.toString()}lemonBook", fis)

    }

    //读取文件
    suspend fun getDbFile(sardine: Sardine): InputStream = withContext(Dispatchers.IO) {
        return@withContext sardine.get("${etHost.text.toString()}lemonBook/back.db")
    }


}
