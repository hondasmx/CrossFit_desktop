package com.company.model

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import javax.json.JsonObject

data class Workout(val workoutName: String, val ascending: Boolean, val timeCap: Int)

class Phone : JsonModel {
    val idProperty = SimpleIntegerProperty()
    var id by idProperty

    val numberProperty = SimpleStringProperty()
    var title by numberProperty

    override fun updateModel(json: JsonObject) {
        with(json) {
            id = int("id")!!
            title = string("title")
        }
    }

    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("id", id)
            add("title", title)
        }
    }
}