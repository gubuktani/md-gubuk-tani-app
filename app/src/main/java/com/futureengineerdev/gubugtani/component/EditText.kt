package com.futureengineerdev.gubugtani.component

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.futureengineerdev.gubugtani.R
import java.util.regex.Pattern


class EditText : AppCompatEditText, View.OnTouchListener {

    private lateinit var clearInput: Drawable
    private lateinit var emailInput: Drawable
    private lateinit var passwordInput: Drawable
    private lateinit var usernameInput: Drawable
    private lateinit var cityInput: Drawable
    private lateinit var borderInput: Drawable
    private lateinit var nameInput: Drawable
    private var inUsername: Boolean = false
    private var inEmail: Boolean = false
    private var inPassword: Boolean = false
    private var inCity: Boolean = false
    private var inName: Boolean = false

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs, defStyleAttr)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        setPadding(32, 32, 32, 32)
        background = borderInput
        gravity = Gravity.CENTER_VERTICAL
        compoundDrawablePadding = 16
    }

    private fun init(attrs: AttributeSet?, defStyleAttr: Int = 0) {
        val cons = context.obtainStyledAttributes(attrs, R.styleable.EditText, defStyleAttr, 0)

        inEmail = cons.getBoolean(R.styleable.EditText_email, false)
        inPassword = cons.getBoolean(R.styleable.EditText_password, false)
        inName = cons.getBoolean(R.styleable.EditText_name, false)
        inUsername = cons.getBoolean(R.styleable.EditText_username, false)
        inCity = cons.getBoolean(R.styleable.EditText_city, false)
        borderInput = ContextCompat.getDrawable(context, R.drawable.border_input) as Drawable
        clearInput = ContextCompat.getDrawable(context, R.drawable.baseline_clear_24) as Drawable
        emailInput =
            ContextCompat.getDrawable(context, R.drawable.baseline_mail_outline_24) as Drawable
        passwordInput = ContextCompat.getDrawable(context, R.drawable.baseline_lock_24) as Drawable
        usernameInput =
            ContextCompat.getDrawable(context, R.drawable.baseline_person_24) as Drawable
        cityInput = ContextCompat.getDrawable(context, R.drawable.outline_city_24) as Drawable
        nameInput = ContextCompat.getDrawable(context, R.drawable.baseline_person_24) as Drawable

        if (inEmail) {
            setButton(startOfTheText = emailInput)
        } else if (inPassword) {
            setButton(startOfTheText = passwordInput)
        } else if (inUsername) {
            setButton(startOfTheText = usernameInput)
        } else if (inCity) {
            setButton(startOfTheText = cityInput)
        } else if (inName){
            setButton(startOfTheText = nameInput)
        }
        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val input = s.toString()
                val errorEmail = "Format Email Salah"
                val errorPassword = "Password Minimal 8 karakter"
                val errorUsername = "Username tidak boleh menggunakan spasi dan huruf kapital"

                if(s.toString().isNotEmpty()) showClearButton() else hideClearButton()
                error =
                    if (inPassword && input.length < 8 && input.isNotEmpty()) {
                        errorPassword
                    } else if (inEmail && !input.isValidEmail()) {
                        errorEmail
                    } else if (inUsername && !input.isUsernameValid(input)){
                        errorUsername
                    }
                    else {
                        null
                    }
            }

            override fun afterTextChanged(s: Editable?) {

            }


        })
    }


    private fun setButton(
        startOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }


    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        if (compoundDrawables[2] != null) {
            val clearButtonStart: Float
            val clearButtonEnd: Float
            var clearButtonClicked = false

            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                clearButtonEnd = (clearInput.intrinsicWidth + paddingStart).toFloat()
                when {
                    event.x < clearButtonEnd -> clearButtonClicked = true
                }
            } else {
                clearButtonStart = (width - paddingEnd - clearInput.intrinsicWidth).toFloat()
                when {
                    event.x > clearButtonStart -> clearButtonClicked = true
                }
            }
            if (clearButtonClicked) {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        clearInput = ContextCompat.getDrawable(
                            context,
                            R.drawable.baseline_clear_24
                        ) as Drawable
                        showClearButton()
                        return true
                    }
                    MotionEvent.ACTION_UP -> {
                        clearInput = ContextCompat.getDrawable(
                            context,
                            R.drawable.baseline_clear_24
                        ) as Drawable
                        when {
                            text != null -> text?.clear()
                        }
                        hideClearButton()
                        return true
                    }
                    else -> return false
                }
            }
            else return false
        }
        return false
    }

    private fun hideClearButton() {
        when {
            inEmail -> setButton(startOfTheText = emailInput)
            inPassword -> setButton(startOfTheText = passwordInput)
            inUsername -> setButton(startOfTheText = usernameInput)
            inCity ->setButton(startOfTheText = cityInput)
            inName -> setButton(startOfTheText = nameInput)
            else -> setButton()
        }
    }

    private fun showClearButton() {
        when {
            inEmail -> setButton(
                startOfTheText = emailInput,
                endOfTheText = clearInput
            )
            inPassword -> setButton(
                startOfTheText = passwordInput,
                endOfTheText = clearInput
            )
            inUsername -> setButton(
                startOfTheText = usernameInput,
                endOfTheText = clearInput
            )
            inCity -> setButton(
                startOfTheText = cityInput,
                endOfTheText = clearInput
            )
            inName -> setButton(
                startOfTheText = nameInput,
                endOfTheText = clearInput
            )
            else -> setButton(endOfTheText = clearInput)
        }
    }

    fun String.isUsernameValid(username: String): Boolean {
        val hasUpperCase = username.any { it.isUpperCase() }
        val hasSpace = username.contains(" ")
        return !hasUpperCase && !hasSpace
    }

    fun String.isValidEmail(): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(this).matches()
    }

}



