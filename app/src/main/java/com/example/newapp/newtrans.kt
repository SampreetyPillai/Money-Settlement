package com.example.newapp

data class newtrans(var USERNAME: String? =null ,
                    var RECIPIENT: String? = null,
                    var Amount: String? =null,
                    var Time:String? = null,
                    var Settled:Boolean = false,
                    var Occasion:String? = null)
