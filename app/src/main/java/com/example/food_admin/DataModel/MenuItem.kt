package com.example.food_admin.DataModel

data class MenuItem(val foodName :String?= null,
                    val foodPrice:String?=null,
                    val foodImg :String?=null,
    val dishcount :Int=0

)


// Concept 1
//data class MenuItem(val foodName :String?= null,
//                    val foodPrice:String?=null,
//                    val foodImg :String?=null,
//                    val dishcount :Int=
//
//
//
// )
// If all my variable are not inialized with some value then i need non-argument construtor like i did in concept2.
// But if i inalized the varibel with some value even with null then i use above method
// Note : All varaible should be initalized not just someand some 


// Concept 2
//{
//    // Add a no-argument constructor
//    constructor() : this("", "", "", 0)
//}


//// Rememeber . The name of these varaible would be same as we have in firebase .
// Means if in firebase dish name is store in dish_name varaibel then in this data class it should be also dataclass
// beacuse when we call the firebase in all item activity we are storing the firebase child value in list