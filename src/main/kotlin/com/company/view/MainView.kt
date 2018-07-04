package com.company.view

import com.company.model.Result
import com.company.model.Workout
import com.company.model.convertKgToLb
import javafx.collections.ObservableList
import tornadofx.*
import javax.json.JsonObject

class MainView : View("Hello TornadoFX") {

    private var isLbsWeight = true
    private val persons = mutableListOf<Person>().observable()
    private val workout1 = Workout("Workout 1", true, 500)
    private val workout2 = Workout("Workout 2", true, 500)
    private val workout3 = Workout("Workout 3", false, 500)
    private val workouts = mutableListOf(workout1, workout2, workout3).observable()

    override val root = vbox {
        tableview(persons) {
            generatePersons()
            column("Name", Person::name)
            for (i in 0 until workouts.size) {
                nestedColumn(workouts[i].workoutName) {
                    column("Result", Person::results).cellFormat {
                        text = if (workouts[i].timeCap < it[i].result) {
                            "CAP\n+"
                        } else {
                            if (isLbsWeight) {
                                it[i].result.toString()
                            } else {
                                convertKgToLb(it[i].result.toDouble())
                            }
                        }
                    }
                    column("Points", Person::results).cellFormat {
                        text = it[i].points.toString()
                    }
                }
            }
            column("Summary", Person::totalPoints)
            columnResizePolicy = SmartResize.POLICY

        }
    }


    private fun generatePersons() {
        val person1 = Person("Samantha Stuart")
        person1.results = listOf(
                Result(workout1, 100, 1),
                Result(workout2, 80, 2),
                Result(workout3, 110, 3))
        val person2 = Person("Tom Marks")
        person2.results = listOf(
                Result(workout1, 110, 1),
                Result(workout2, 90, 1),
                Result(workout3, 60, 1))
        val person3 = Person("Stuart Gills")
        person3.results = listOf(
                Result(workout1, 120, 1),
                Result(workout2, 100, 1),
                Result(workout3, 60, 1))
        val person4 = Person("Nicole Williams")
        person4.results = listOf(
                Result(workout1, 130, 1),
                Result(workout2, 70, 1),
                Result(workout3, 100, 1))
        persons.addAll(person1, person2, person3, person4)

        for (workout in workouts) {

            if (workout.ascending) {
                persons.sortWith(Comparator.comparingInt { it.results[workouts.indexOf(workout)].result })
            } else {
                persons.sortWith(Comparator.comparingInt { -it.results[workouts.indexOf(workout)].result })
            }

            for (person in persons) {
                val currentIndex = persons.indexOf(person)
                if (currentIndex == 0) {
                    person.results.find { it.workout == workout }!!.points = 1
                }
                if (currentIndex > 0) {
                    val currentResult = person.results.find { it.workout == workout }
                    val previousResult = persons[currentIndex - 1].results.find { it.workout == workout }
                    if (currentResult != null) {
                        if (currentResult.result == previousResult?.result) {
                            person.results.find { it.workout == workout }!!.points = previousResult.points
                        } else {
                            person.results.find { it.workout == workout }!!.points = currentIndex + 1
                        }
                    }

                }
            }
        }

        for (person in persons) {
            person.results.forEach { result -> person.totalPoints += result.points }
        }
    }
}


data class Person(var name: String): JsonModel {
    var totalPoints: Int = 0
    var results: List<Result> = listOf()

    override fun updateModel(json: JsonObject) {
        super.updateModel(json)
    }
}


