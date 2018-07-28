package app.horecafy.com

import android.app.Application
import android.content.Intent
import app.horecafy.com.activities.MainActivity
import app.horecafy.com.activities.customers.CustomerMainActivity
import app.horecafy.com.activities.wholesalers.WholesalerMainActivity
import app.horecafy.com.models.Login
import app.horecafy.com.services.AuthService
import app.horecafy.com.util.Constants
import app.horecafy.com.util.SharedPreferencesHelper
import app.horecafy.com.util.TypeUser


class HorecafyApp: Application() {

    override fun onCreate() {
        val preferences = SharedPreferencesHelper()
        val typeUser =  preferences.getValue(this, Constants.TYPEUSER_KEY)
        val username = preferences.getValue(this, Constants.USERNAME_KEY)
        val password = preferences.getValue(this, Constants.PASSWORD_KEY)

        if (!typeUser.isNullOrEmpty() && !username.isNullOrEmpty() && !password.isNullOrEmpty()) {
            if (typeUser == TypeUser.CUSTOMER.value) {
                AuthService.loginAsCustomer(this, Login(username, password,
                        TypeUser.CUSTOMER.value), { status, data, error ->
                    if (data != null) {
                        val intent = Intent(this, CustomerMainActivity::class.java)
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                    }
                })
            } else {
                AuthService.loginAsWholesaler(this, Login(username, password, TypeUser.CUSTOMER.value), { status, data, error ->
                    if (data != null) {
                        val intent = Intent(this, WholesalerMainActivity::class.java)
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                    }
                })
            }
        }

        super.onCreate()
    }
}