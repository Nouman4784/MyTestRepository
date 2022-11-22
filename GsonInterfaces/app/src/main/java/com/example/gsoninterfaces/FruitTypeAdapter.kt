package com.example.gsoninterfaces

import com.google.gson.*
import java.lang.reflect.Type

private const val CLASSNAME = "CLASSNAME"
private const val DATA = "DATA"

class FruitTypeAdapter : JsonSerializer<Fruit>,JsonDeserializer<Fruit> {
    //As below serialize returns Json element
    // the trick is we want to bundle the classname with json string in preferences
    override fun serialize(src: Fruit?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonObject().apply {
            addProperty(CLASSNAME,src?.javaClass?.name)
            add(DATA,context?.serialize(src))
        }
    }
    // and serialize returns Fruit
    // and then when we are reading, we're gonna read the classname and we instantiate the class not the interface
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Fruit {
        val jsonObject = json?.asJsonObject
        val classname = jsonObject?.get(CLASSNAME)?.asString
        val clazz = classname?.let { getObjectClass(it) }
        return context!!.deserialize(jsonObject?.get(DATA), clazz)
    }

    private fun getObjectClass(className: String): Class<*> {
        try {
            return Class.forName(className)
        } catch (e: ClassNotFoundException){
            throw JsonParseException(e)
        }
    }

}