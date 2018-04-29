package com.robyn.myimgurapp.solo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.robyn.myimgurapp.R
import com.robyn.myimgurapp.utils.replaceFragmentInActivity

class SoloActivity : AppCompatActivity() {

    private lateinit var mPresenter: SoloContract.Presenter
    lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.solo_ac)

        val imageId = intent.getStringExtra(EXTRA_SOLO_IMGHASH)
        val caption = intent.getStringExtra(EXTRA_SOLO_CAPTION)

        val soloFragment = supportFragmentManager.findFragmentById(R.id.solo_container)
                as SoloFragment? ?: SoloFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.solo_container)
        }

        mPresenter = SoloPresenter(soloFragment, imageId)

        // Setup toolbar
        mToolbar = findViewById(R.id.toolbar)
        mToolbar.setNavigationIcon(R.drawable.ic_action_back)
        mToolbar.setNavigationOnClickListener { finish() }
        mToolbar.title = caption
    }

    override fun onDestroy() {
        mPresenter.clearCompositeDisposable()
        super.onDestroy()
    }

    companion object {
        const val EXTRA_SOLO_IMGHASH = "EXTRA_SOLO_IMGHASH"
        const val EXTRA_SOLO_CAPTION = "EXTRA_SOLO_CAPTION"
    }
}
