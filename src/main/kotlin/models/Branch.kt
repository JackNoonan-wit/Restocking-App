package models

import models.Product
import persistence.Serializer
import utils.Utilities

data class Branch
    (
                var branchName: String,
                var branchLocation: String,
                var branchManager: String,
                var estDate: String,
                var isBranchArchived: Boolean = false,
                var products : MutableSet<Product> = mutableSetOf()

    )





{
    //private var products = ArrayList<Product>()

    private var lastProductId = 0
    private fun getProductId() = lastProductId++

    fun addProduct(product: Product) : Boolean {
        product.productId = getProductId()
        return products.add(product)
    }

   fun deleteProduct(id: Int): Boolean {
       return products.removeIf { product -> product.productId == id}
   }
    fun findProduct(id: Int): Product?{
        return products.find{ product -> product.productId == id }
    }
    fun listProducts() =
        if (products.isEmpty())  "\tNO PRODUCTS ADDED"
        else  Utilities.formatSetString(products)

   fun updateProduct(indexToUpdate: Int, product: Product?): Boolean {
        val foundProduct = findProduct(indexToUpdate)

        if ((foundProduct != null) && (product != null)) {
            foundProduct.productName = product.productName
            foundProduct.productCategory = product.productCategory
            foundProduct.amountRequired = product.amountRequired
            foundProduct.costOfOne = product.costOfOne
            foundProduct.isProductOrdered = product.isProductOrdered
            return true
        }

        return false
    }


    /*fun updateProduct(id: Int, newProduct : Product): Boolean {
        val foundProduct = findOne(id)

        if (foundProduct != null){
            foundProduct.productName = newProduct.productName
            foundProduct.productCategory = newProduct.productCategory
            foundProduct.amountRequired = newProduct.amountRequired
            foundProduct.costOfOne = newProduct.costOfOne
            foundProduct.isProductOrdered = newProduct.isProductOrdered
            return true
        }

        //if the object was not found, return false, indicating that the update was not successful
        return false
    }*/


    fun numberOfProducts() = products.size

    fun findOne(id: Int): Product?{
        return products.find{ product -> product.productId == id }
    }

}

