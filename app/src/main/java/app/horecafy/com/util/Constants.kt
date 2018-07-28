package app.horecafy.com.util

import android.content.Context
import android.net.ConnectivityManager


class Constants {

    companion object {
        val TAG = "HORECAFY"

        val JSON_CONTENT_TYPE = "application/json; charset=utf-8"

        val TYPEUSER_KEY = "TYPEUSER"
        val USERNAME_KEY = "USERNAME"
        val PASSWORD_KEY = "PASSWORD"

        val CONSTANT_CREATE_LIST_FIRST_ELEMENT = "Lista personalizada"

        val CONSTANT_EMPTY_STRING = ""
        val CONSTANT_SELECT_DISTRIBUTOR = "Seleccionar Distribuidor"
        val CONSTANT_SELECT_THE_TYPE_OF_RESTAURANTS = "Seleccione el tipo de restaurantes"
        val SOMETHING_WENT_WRONG = "Algo salió mal. Inténtalo de nuevo."
        val NO_DATA_AVAILABLE = "Datos no disponibles."
        val NO_INTERNET_CONNECTION_AVAILABLE = "No se ha podido establecer conexión. Por favor, intentelo de nuevo."

        val MONDAY = "Lun"
        val TUESDAY = "Mar"
        val WEDNESDAY = "Mie"
        val THURSDAY = "Jue"
        val FRIDAY = "Vie"
        val SATURDAY = "Sab"
        val SUNDAY = "Sol"

        val SLOT_ONE = "00:00 - 02:00"
        val SLOT_TWO = "02:00 - 04:00"
        val SLOT_THREE = "04:00 - 06:00"
        val SLOT_FOUR = "06:00 - 08:00"
        val SLOT_FIVE = "08:00 - 10:00"
        val SLOT_SIX = "10:00 - 12:00"
        val SLOT_SEVEN = "12:00 - 14:00"
        val SLOT_EIGHT = "14:00 - 16:00"
        val SLOT_NINE = "16:00 - 18:00"
        val SLOT_TEN = "18:00 - 20:00"
        val SLOT_ELEVEN = "20:00 - 22:00"
        val SLOT_TWELVE = "22:00 - 00:00"

        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }
}