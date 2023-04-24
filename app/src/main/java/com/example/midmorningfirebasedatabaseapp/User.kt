package com.example.midmorningfirebasedatabaseapp

class User {
    var name:String = ""
    var email:String = ""
    var idNumber:String = ""
    var id:String = ""

    constructor(name: String, email: String, idNumber: String, id: String) {
        this.name = name
        this.email = email
        this.idNumber = idNumber
        this.id = id
    }
    constructor()
}