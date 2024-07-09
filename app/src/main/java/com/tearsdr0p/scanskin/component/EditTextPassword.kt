package com.tearsdr0p.scanskin.component

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.tearsdr0p.scanskin.R

class EditTextPassword: AppCompatEditText, View.OnTouchListener{

    private lateinit var showPasswordImage : Drawable
    private var isPasswordVisible = false

    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        setOnTouchListener(this)
        showPasswordImage = ContextCompat.getDrawable(context, if (!isPasswordVisible) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24 ) as Drawable
        setEditCompoundDrawables(endOfTheText = showPasswordImage)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Do nothing.
//                if (s.toString().length < 8) {
//                    setError(resources.getString(R.string.password_require), null)
//                } else {
//                    error = null
//                }
            }
            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })

    }

    private fun setEditCompoundDrawables(
        startOfTheText: Drawable? = null,
        topOfTheText:Drawable? = null,
        endOfTheText:Drawable? = null,
        bottomOfTheText: Drawable? = null
    ){
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        if(compoundDrawables[2] != null){
            val passwordButtonStart: Float
            val passwordButtonEnd: Float
            var isPasswordButtonClicked = false
            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                passwordButtonEnd = (showPasswordImage.intrinsicWidth + paddingStart).toFloat()
                when {
                        event.x < passwordButtonEnd -> isPasswordButtonClicked = true
                    }
            } else {
                passwordButtonStart = (width - paddingEnd - showPasswordImage.intrinsicWidth).toFloat()
                when {
                        event.x > passwordButtonStart -> isPasswordButtonClicked = true
                    }
            }
            if(isPasswordButtonClicked){
                when(event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        showPasswordImage = ContextCompat.getDrawable(context, if(isPasswordVisible) R.drawable.baseline_visibility_off_24 else R.drawable.baseline_visibility_24) as Drawable
                        setEditCompoundDrawables(endOfTheText = showPasswordImage)
                        transformationMethod = if(isPasswordVisible) null else PasswordTransformationMethod.getInstance()
                        isPasswordVisible = !isPasswordVisible
                    }
                }
                return false
            }
            return false
        }
        return false
    }
}
