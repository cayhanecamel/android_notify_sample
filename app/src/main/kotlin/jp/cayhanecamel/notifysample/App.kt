package jp.cayhanecamel.notifysample

import android.app.Application


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (instance == null) {
            instance = this
        }
    }

    companion object {

        private var instance: App? = null

        fun get(): App {
            return instance!!
        }
    }
}
