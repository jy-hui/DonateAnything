package com.example.donateanything

import com.google.firebase.firestore.QuerySnapshot

data class DonateList(
    var Name: String ?= null,
    var ID:String ?= null,
    var NoIC:String ?= null,
    var Date:String ?= null,
    var Email:String ?= null,
    var Title:String ?= null,
    var ItemType:String ?= null,
    var AccountNo:String ?= null,
    var Bank:String ?= null,
    var Payment:String?=null,
    var Item:String?=null,
    var Value:String?=null,
    var Unit:String?=null,
    var Transportation:String?=null,
    var Address:String?=null

)
