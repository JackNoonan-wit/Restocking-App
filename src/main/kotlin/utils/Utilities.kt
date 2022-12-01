
package utils

import models.Branch
import models.Product


object Utilities {

    // NOTE: JvmStatic annotation means that the methods are static i.e. we can call them over the class
    //      name; we don't have to create an object of Utilities to use them.

    @JvmStatic
    fun formatListString(branchesToFormat: List<Branch>): String =
        branchesToFormat
            .joinToString(separator = "\n") { branch ->  "$branch" }

    @JvmStatic
    fun formatSetString(productsToFormat: Set<Product>): String =
        productsToFormat
            .joinToString(separator = "\n") { product ->  "\t$product" }

}
