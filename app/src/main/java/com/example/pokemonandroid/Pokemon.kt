package com.example.pokemonandroid

class Pokemon {
    var name: String ?= null
    var des: String ?= null
    var image: Int ?= null
    var power: Double ?= null
    var lat: Double ?= null
    var log: Double ?= null
    var isCatch: Boolean ?= false

    constructor(image: Int, name: String, des: String, power: Double, lat: Double, log: Double) {
        this.name = name
        this.image = image
        this.des = des
        this.power = power
        this.lat = lat
        this.log = log
        this.isCatch = false
    }
}