package br.senai.sp.jandira.imc20

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.senai.sp.jandira.imc20.databinding.ActivityResultBinding
import br.senai.sp.jandira.imc20.utils.getBmi
import br.senai.sp.jandira.imc20.utils.getStatusBmi

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        val bmi = "%.2f".format(intent.getFloatExtra("bmi", 0.0f))
        val bmiStatus = intent.getStringExtra("bmiStatus")

        binding.textViewResult.text = bmi
        binding.textViewStatus.text = bmiStatus
    }
}