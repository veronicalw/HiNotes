package com.example.hinotes.model

data class Notes(
        var titles: String = "",
        var contents: String = ""
) {

    constructor() : this("", "")
}