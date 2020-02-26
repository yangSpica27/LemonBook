package com.spica27.accountbook.activity

import android.widget.ImageView
import android.widget.TextView
import com.drakeet.about.*
import com.spica27.accountbook.BuildConfig
import com.spica27.accountbook.R


class AboutActivity : AbsAboutActivity() {
    override fun onItemsCreated(items: MutableList<Any>) {
        items.add(Category("介绍与帮助"))
        items.add(Card(getString(R.string.card_content)))
        items.add(Category("开发者"))
        items.add(
            Contributor(
                R.mipmap.ic_avatar,
                "Spica 27",
                "Developer",
                "https://yangspica27.github.io/"
            )
        )
        items.add(Contributor(R.mipmap.ic_launcher_round, "隔壁班同学", "部分图标绘制"))

        items.add(Category("Open Source Licenses"))

        items.add(
            License(
                "Jetpack",
                "Google",
                License.APACHE_2,
                "https://developer.android.google.cn/jetpack/docs/getting-started"
            )
        )

        items.add(
            License(
                "MpAndroidChart",
                "PhilJay",
                License.APACHE_2,
                "https://github.com/tuteng/MPAndroidChart/blob/master/mpandroid.mkd"
            )
        )
        items.add(
            License(
                "Glide",
                "bumptech",
                License.APACHE_2,
                "https://github.com/bumptech/glide"
            )
        )




        items.add(
            License(
                "about-page",
                "drakeet",
                License.APACHE_2,
                "https://github.com/drakeet/about-page"
            )
        )
        items.add(
            License(
                "MultiType",
                "drakeet",
                License.APACHE_2,
                "https://github.com/drakeet/MultiType"
            )
        )
    }

    override fun onCreateHeader(icon: ImageView, slogan: TextView, version: TextView) {
        icon.setImageResource(R.mipmap.ic_launcher)
        slogan.text = "About Page By Spica 27"
        version.text = "v" + BuildConfig.VERSION_NAME
    }

}