package com.demo.sweetfish.logic.convert

import com.demo.sweetfish.logic.model.User
import com.demo.sweetfish.logic.model.UserLocation
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class UserAdapter : TypeAdapter<User>() {
    override fun write(out: JsonWriter?, value: User?) {
        if (out != null) {
            out.beginObject()
            if (value != null) {
                out.name("id").value(value.id)
                out.name("account").value(value.account)
                out.name("password").value(value.password)
                out.name("avatar").value(value.avatar)
                out.name("background").value(value.background)
                out.name("name").value(value.name)
                out.name("sex").value(value.sex)
                out.name("location")
                    .value("${value.location?.province} ${value.location?.city} ${value.location?.district}")
                out.name("describe").value(value.describe)
            }
            out.endObject()
        }
    }

    override fun read(`in`: JsonReader?): User {
        val user = User("", "", 0, 0)
        if (`in` != null) {
            `in`.beginObject()
            while (`in`.hasNext()) {
                when (`in`.nextName()) {
                    "id" -> user.id = `in`.nextLong()
                    "account" -> user.account = `in`.nextString()
                    "password" -> user.password = `in`.nextString()
                    "avatar" -> user.avatar = `in`.nextLong()
                    "background" -> user.background = `in`.nextLong()
                    "name" -> user.name = `in`.nextString()
                    "sex" -> user.sex = `in`.nextBoolean()
                    "location" -> {
                        val stringArray = `in`.nextString().split(' ')
                        user.location = UserLocation(stringArray[0], stringArray[1], stringArray[2])
                    }

                    "describe" -> user.describe = `in`.nextString()
                }
            }
            `in`.endObject()
        }
        return user
    }
}