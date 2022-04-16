package com.example.donateanything

data class HistoryList(
    var Date: String ?= null,
    var Item:String ?= null,
    var ItemType:String ?= null,
    var Value:String ?= null,
    var btnCheck:String = "View",
    var pointsGain:String ?= null,
    var status:String = "[Completed]",
    var Bank:String ?= null,
    var Payment:String ?= null
)
