package com.jefisu.mobtape.service.constants

class MobConstants {

    companion object {

        // SharedPreferences
        object SHARED {
            const val TOKEN_KEY = "tokenkey"
            const val USER_KEY = "personkey"
            const val USER_NAME = "personname"
        }

        object RETROFIT {
            const val BASE_URL = "http://devmasterteam.com/CursoAndroidAPI/"
        }

        object HTTP {
            const val SUCCESS = 200
        }

        object SERVICES {

            object COLUMNS {
                const val ID = "_id"
                const val CLIENT = "client"
                const val CPF = "cpf"
                const val PHONE = "phone"
                const val TYPE = "typeService"
                const val CATEGORY = "category"
            }
        }
    }
}