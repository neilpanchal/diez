package org.diez.examples.loremipsum

import android.content.res.Resources
import android.graphics.drawable.PaintDrawable
import android.graphics.drawable.shapes.RectShape
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import kotlinx.android.synthetic.main.activity_main.*
import org.diez.loremIpsum.*
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Here we are observing hot updates to our design system.
        //
        // Since this instance of Diez is initialized with a DesignSystem, it will deliver updates to the DesignSystem
        // object described in `src/DesignSystem.ts` (relative to the root of the Diez project).
        Diez(DesignSystem(), root).attach(fun(designSystem) {
            runOnUiThread {
                apply(designSystem)
            }
        })
    }

    private fun apply(designSystem: DesignSystem) {
        root.setBackgroundColor(designSystem.palette.contentBackground.color)

        headerLayout.background = backgroundFromGradient(designSystem.palette.headerBackground)

        headerView.loadBackgroundImage(designSystem.images.masthead)

        imageView.load(designSystem.images.logo)

        imageView.afterLayout {
            val paddingBottom = -imageView.width / 2
            imageView.setPadding(designSystem.layoutValues.contentMargin.left.dpToPx(), 0, 0, paddingBottom)
        }

        val padding = designSystem.layoutValues.contentMargin
        contentLayout.setPadding(padding.left.dpToPx(), padding.top.dpToPx(), padding.right.dpToPx(), padding.bottom.dpToPx())

        titleTextView.text = designSystem.strings.title
        titleTextView.apply(designSystem.typography.heading1)
        titleSpacer.layoutParams.height = designSystem.layoutValues.spacingSmall.dpToPx()

        captionTextView.text = designSystem.strings.caption
        captionTextView.apply(designSystem.typography.caption)
        captionSpacer.layoutParams.height = designSystem.layoutValues.spacingSmall.dpToPx()

        animationView.load(designSystem.loadingAnimation)
        animationSpacer.layoutParams.height = designSystem.layoutValues.spacingMedium.dpToPx()

        animationTextView.text = designSystem.strings.helper
        animationTextView.apply(designSystem.typography.body)
    }

    private fun backgroundFromGradient(gradient: LinearGradient): PaintDrawable {
        val drawable = PaintDrawable()
        drawable.shape = RectShape()
        drawable.shaderFactory = gradient.shaderFactory
        return drawable
    }

    inline private fun View.afterLayout(crossinline afterLayout: () -> Unit) {
        viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (measuredWidth <= 0 || measuredHeight <= 0) {
                    return
                }

                viewTreeObserver.removeOnGlobalLayoutListener(this)
                afterLayout()
            }
        })
    }
}