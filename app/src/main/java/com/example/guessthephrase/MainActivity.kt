package com.example.guessthephrase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random
import android.R.string



class MainActivity : AppCompatActivity() {
    var message = ArrayList<String>()
    lateinit var header: TextView
    lateinit var submit: Button
    lateinit var textEnter: TextView
    var holdString = " "
    var starredWord =""
    private var beforeWord = ""
    private var guesses = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        header = findViewById(R.id.header)
        submit = findViewById(R.id.submit)
        textEnter = findViewById(R.id.textEnter)

        var randomWordsList = mutableListOf("hello","smile","nice")
        var random = Random.nextInt(0, 2)


        beforeWord = randomWordsList[random]
        starredWord = beforeWord.replaceRange(0 , beforeWord.length , "★".repeat(beforeWord.length))
        println("the before word : $beforeWord and the after word is $starredWord")
        header.text = "guess the word: $starredWord"

        submit.setOnClickListener {
            val myRV = findViewById<RecyclerView>(R.id.rvMain)
            myRV.adapter = RecycleViewAdapter(message)
            myRV.layoutManager = LinearLayoutManager(this) // main activity
            checkAllMessage()
            checkLetter()
            textEnter.text = null
        }
    }

    fun checkAllMessage() {
        val msg = textEnter.text.toString()
        println("here is the message $msg")
        if (msg.isNotEmpty() && msg.length > 1) {
            if (guesses < 3) {
                if (msg == beforeWord) {
                    submit.isEnabled = false
                    submit.isClickable = false
                    textEnter.isEnabled = false
                    textEnter.isClickable = false
                    message.add(" correct answer: $msg")
                    println("correct guess")
                } else {
                    guesses++
                    message.add(
                        "wrong guess, you guessed $msg " +
                                "you have guessed  $guesses/3"
                    )
                    println("wrong guess")

                }
                if (guesses == 3) {
                    submit.isEnabled = false
                    submit.isClickable = false
                    textEnter.isEnabled = false
                    textEnter.isClickable = false
                    message.add("Game over. the correct answer is $beforeWord")
                    println("Game over")
                }
            }
        }
    }

    fun checkLetter(){
        println("we are in the checkLetter")
        var holdIndex = 0
        val msg = textEnter.text.toString()
        if (guesses < 3) {
            if (beforeWord.contains(msg)) {
                holdIndex = beforeWord.indexOf(msg)
                holdString = holdString.replace(beforeWord[holdIndex], starredWord[holdIndex])
                println("Hold $holdString")
                header.text = holdString
            } else {
                guesses++
                message.add(
                    "wrong guess, you guessed $msg " +
                            "you have guessed  $guesses/3"
                )
                println("wrong guess")

            }
            if (guesses == 3) {
                submit.isEnabled = false
                submit.isClickable = false
                textEnter.isEnabled = false
                textEnter.isClickable = false
                message.add("Game over. the correct answer is $beforeWord")
                println("Game over")
            }
        }
    }
}
