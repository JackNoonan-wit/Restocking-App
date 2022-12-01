package models



data class Product (var productId: Int = 0, var productName : String,var amountRequired : Int,var costOfOne : Int,var productCategory : String, var isProductOrdered: Boolean = false){

    override fun toString(): String {
        if (isProductOrdered)
            return "$productId: $productName Purchased"
        else
            return "$productId: Category: $productCategory, Name: $productName, Required: $amountRequired, Cost of each: $costOfOne, "
    }

}