package com.library.jushi.jushilibrary.calculator

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.jushi.library.base.BaseFragmentActivity
import com.library.jushi.jushilibrary.R
import kotlinx.android.synthetic.main.activity_calculator_layout.*

class CalculatorActivity : BaseFragmentActivity(), View.OnClickListener {

    override fun getLayoutResId(): Int {
        setSystemBarStatus(true, true, true)
        return R.layout.activity_calculator_layout
    }

    override fun initView() {

    }

    override fun initData() {

    }

    override fun setListener() {
        clear.setOnClickListener(this)
        fallback.setOnClickListener(this)
        add_or_.setOnClickListener(this)
        divide.setOnClickListener(this)
        _0.setOnClickListener(this)
        _1.setOnClickListener(this)
        _2.setOnClickListener(this)
        _3.setOnClickListener(this)
        _4.setOnClickListener(this)
        _5.setOnClickListener(this)
        _6.setOnClickListener(this)
        _7.setOnClickListener(this)
        _8.setOnClickListener(this)
        _9.setOnClickListener(this)
        _add.setOnClickListener(this)
        _jian.setOnClickListener(this)
        _x.setOnClickListener(this)
        _sum.setOnClickListener(this)
        inputEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                inputEditText.setSelection(inputEditText.text.length)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private var count = 0

    @SuppressLint("SetTextI18n")
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.clear -> {
                inputEditText.setText("")
            }
            R.id.fallback -> {
                if (inputEditText.text.isNotEmpty())
                    inputEditText.setText(inputEditText.text.substring(0, inputEditText.text.length - 1))
            }
            R.id.add_or_ -> {
//                inputEditText.setText(inputEditText.text.substring(0, inputEditText.text.length - 1))
            }
            R.id.divide -> {
                inputEditText.setText("${inputEditText.text}÷")
            }
            R.id._0 -> {
                inputEditText.setText("${inputEditText.text}0")
            }
            R.id._1 -> {
                inputEditText.setText("${inputEditText.text}1")
            }
            R.id._2 -> {
                inputEditText.setText("${inputEditText.text}2")
            }
            R.id._3 -> {
                inputEditText.setText("${inputEditText.text}3")
            }
            R.id._4 -> {
                inputEditText.setText("${inputEditText.text}4")
            }
            R.id._5 -> {
                inputEditText.setText("${inputEditText.text}5")
            }
            R.id._6 -> {
                inputEditText.setText("${inputEditText.text}6")
            }
            R.id._7 -> {
                inputEditText.setText("${inputEditText.text}7")
            }
            R.id._8 -> {
                inputEditText.setText("${inputEditText.text}8")
            }
            R.id._9 -> {
                inputEditText.setText("${inputEditText.text}9")
            }
            R.id._add -> {
                inputEditText.setText("${inputEditText.text}+")
            }
            R.id._jian -> {
                inputEditText.setText("${inputEditText.text}-")
            }
            R.id._x -> {
                inputEditText.setText("${inputEditText.text}x")
            }
            R.id._sum -> {
                var result = if (count == 0) "3" else "王"
                inputEditText.setText("${inputEditText.text}=$result")
                count += 1
            }

        }
    }
}