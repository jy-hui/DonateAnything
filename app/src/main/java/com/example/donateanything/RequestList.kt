package com.example.donateanything

import com.google.firebase.firestore.QuerySnapshot

data class RequestList(
    var FirstName: String ?= null,
    var LastName:String ?= null,
    var Specific:String ?= null,
    var Reason:String ?= null,
    var DailySupply:String ?= null,
    var Money:String ?= null,
    var Other:String ?= null,
    var FoodDrink:String ?= null,
    var ICNo:String?=null,
    var HouseAddress:String?=null,
    var Email:String?=null,
    var Phone:String?=null,
    var ID:String?=null

)
