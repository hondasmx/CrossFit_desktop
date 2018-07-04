package com.company.app

import com.company.view.MainView
import com.company.view.Person
import javafx.collections.ObservableList
import tornadofx.*

class MyApp: App(MainView::class, Styles::class) {
    private val api: Rest by inject()

    fun loadCustomers(): ObservableList<Person> =
            api.get("http://jsonplaceholder.typicode.com/todos").list().toModel()
}