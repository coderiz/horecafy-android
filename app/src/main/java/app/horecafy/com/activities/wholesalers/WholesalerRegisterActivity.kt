package app.horecafy.com.activities.wholesalers

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import app.horecafy.com.R
import app.horecafy.com.activities.MainActivity
import app.horecafy.com.models.TypeOfBusiness
import app.horecafy.com.models.Wholesaler
import app.horecafy.com.services.AuthService
import app.horecafy.com.services.CommonService
import app.horecafy.com.services.WholesalerService
import app.horecafy.com.util.UiHelpers
import app.horecafy.com.util.Validators
import kotlinx.android.synthetic.main.activity_wholesaler_register.*


class WholesalerRegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wholesaler_register)

        buttonSave.setOnClickListener(View.OnClickListener {
            UiHelpers.showProgessBar(window, progressBar)
            if (isValid()) {
                val vat = editVat.text.toString()
                val email = editMail.text.toString()
                val password = editPassword.text.toString()
                val name = editName.text.toString()
                val typeOfBusinessId = 1
                val contactName = editContactName.text.toString()
                val contactMobile = editContactMobile.text.toString()
                val address = editAddress.text.toString()
                val city = editCity.text.toString()
                val zipCode = editZipCode.text.toString()
                val province = editProvince.text.toString()
                val country = editCountry.text.toString()

                val wholesaler = Wholesaler(VAT = vat,
                        email = email,
                        password = password,
                        name = name,
                        typeOfBusinessId = typeOfBusinessId,
                        contactName = contactName,
                        contactEmail = "",
                        contactMobile = contactMobile,
                        address = address,
                        city = city,
                        zipCode = zipCode,
                        province = province,
                        country = country)

                WholesalerService.create(this, wholesaler) { status, data, error ->
                    UiHelpers.hideProgessBar(window, progressBar)
                    if (status) {
                        Toast.makeText(this, "Distribuidor creado correctamente", Toast.LENGTH_LONG).show()

                        AuthService.wholesaler = data

                        val intent = Intent(applicationContext, WholesalerMainActivity::class.java)
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                    } else {
                        when (error) {
                            "VAT_DUPLICATED" -> {
                                editVat.setError("VAT ya en uso")
                                requestFocus(editVat, true)
                            }
                            "EMAIL_DUPLICATED" -> {
                                editMail.setError("Correo electrónico ya en uso")
                                requestFocus(editMail, true)
                            }
                            else -> Toast.makeText(this, error, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            } else {
                UiHelpers.hideProgessBar(window, progressBar)
            }
        })
    }

    override fun onBackPressed() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    private fun isValid(): Boolean {
        var isValid = true

        val vat = editVat.text.toString()
        val email = editMail.text.toString()
        val password = editPassword.text.toString()
        val repeatPassword = editRepeatPassword.text.toString()
        val name = editName.text.toString()
        val contactName = editContactName.text.toString()
        val contactMobile = editContactMobile.text.toString()
        val address = editAddress.text.toString()
        val city = editCity.text.toString()
        val zipCode = editZipCode.text.toString()
        val province = editProvince.text.toString()
        val country = editCountry.text.toString()

        if (vat.isNullOrEmpty()) {
            editVat.setError("Este campo es obligatorio")
            requestFocus(editVat, isValid)
            isValid = false
        }

        if (email.isNullOrEmpty()) {
            editMail.setError("Este campo es obligatorio")
            requestFocus(editMail, isValid)
            isValid = false
        }

        if (!Validators.isEmail(email)) {
            editMail.setError("El correo es incorrecto")
            requestFocus(editMail, isValid)
            isValid = false
        }

        if (password.isNullOrEmpty()) {
            editPassword.setError("Este campo es obligatorio")
            requestFocus(editPassword, isValid)
            isValid = false
        }

        if (password != repeatPassword) {
            editPassword.setError("Las contraseñas no son iguales")
            requestFocus(editPassword, isValid)
            isValid = false
        }

        if (name.isNullOrEmpty()) {
            editName.setError("Este campo es obligatorio")
            requestFocus(editName, isValid)
            isValid = false
        }

        if (contactName.isNullOrEmpty()) {
            editContactName.setError("Este campo es obligatorio")
            requestFocus(editContactName, isValid)
            isValid = false
        }

        if (contactMobile.isNullOrEmpty()) {
            editContactMobile.setError("Este campo es obligatorio")
            requestFocus(editContactMobile, isValid)
            isValid = false
        }

        if (address.isNullOrEmpty()) {
            editAddress.setError("Este campo es obligatorio")
            requestFocus(editAddress, isValid)
            isValid = false
        }

        if (city.isNullOrEmpty()) {
            editCity.setError("Este campo es obligatorio")
            requestFocus(editCity, isValid)
            isValid = false
        }

        if (zipCode.isNullOrEmpty()) {
            editZipCode.setError("Este campo es obligatorio")
            requestFocus(editZipCode, isValid)
            isValid = false
        }

        if (province.isNullOrEmpty()) {
            editProvince.setError("Este campo es obligatorio")
            requestFocus(editProvince, isValid)
            isValid = false
        }

        if (country.isNullOrEmpty()) {
            editCountry.setError("Este campo es obligatorio")
            requestFocus(editCountry, isValid)
            isValid = false
        }

        return isValid
    }

    private fun requestFocus(view: View, setFocus: Boolean) {
        if (setFocus) {
            view.requestFocus()
        }
    }
}
