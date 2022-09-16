package br.senai.sp.jandira.imc20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.senai.sp.jandira.imc20.databinding.ActivityCalculateBinding
import br.senai.sp.jandira.imc20.databinding.ActivityMainBinding
import br.senai.sp.jandira.imc20.utils.getBmi
import br.senai.sp.jandira.imc20.utils.getStatusBmi

class CalculateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalculateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Instânciando o Binding
        binding = ActivityCalculateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        loadProfile()

        binding.buttonCalculate.setOnClickListener {
            bmiCalculate()
        }
    }

    private fun loadProfile() {
        // Abrir o arquivo SharedPreferences
        val dados = getSharedPreferences("dados", MODE_PRIVATE)

        binding.textViewUsername.text = dados.getString("name", "")
        binding.textViewEmail.text = dados.getString("email", "")
        binding.textViewWeight.text =  "${resources.getText(R.string.weight)} ${dados.getInt("weight", 0)} Kg"
        binding.textViewHeight.text = "${resources.getText(R.string.height)} ${dados.getFloat("height", 0.0f)}"
    }

    private fun bmiCalculate() {
        val openResultActivity = Intent(this, ResultActivity::class.java)

        val dados = getSharedPreferences("dados", MODE_PRIVATE)
        val editor = dados.edit()

        var height = 0.0f
        if (validate()) {
            if (binding.editTextHeight.text.toString().isEmpty()) {
                height = dados.getFloat("height", 0.0f)
            } else {
                height = binding.editTextHeight.text.toString().toFloat()
            }
            var weight = binding.editTextWeight.text.toString().toInt()

            var bmi = getBmi(weight, height)
            var bmiStatus = getStatusBmi(bmi, this)

            editor.putFloat("height", height)
            editor.putInt("weight", weight)
            editor.commit()

            // Enviar dados de uma Activity para outra
            openResultActivity.putExtra("bmi", bmi)
            openResultActivity.putExtra("bmiStatus", bmiStatus)

            startActivity(openResultActivity)
        }
    }

    private fun validate() : Boolean{
        if (binding.editTextWeight.text.toString().isEmpty()) {
            binding.editTextWeight.error = "Weight is required"
            return false
        } else {
            return true
        }
    }
}