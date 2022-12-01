
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import persistence.JSONSerializer

import controllers.BranchAPI
import models.Branch
import models.Product
import java.io.File


private val branchAPI = BranchAPI(JSONSerializer(File("branches.json")))
fun mainMenu() : Int {
    return ScannerInput.readNextInt("""
       >----------------------------------------------------------------
       >|                 TESCO RESTOCKING APPLICATION                 |
       >----------------------------------------------------------------
       >|                           Main Menu                          |
       >|   1) Branch Options   2) List Options   3) Search Options    |
       >|                            0) Exit                           |
       >|                                                              |
       >----------------------------------------------------------------
   ==>> """.trimMargin(">"))
}

fun runMenu(){
    do {
        val option = mainMenu()
        when (option) {
            1 -> branchOptions()
            2 -> listOptions()
            3 -> searchOptions()
            else -> println("Invalid option entered: ${option}")
        }
    } while (true)
}

fun main(args: Array<String>) {
    runMenu()
}



// Branch Options functions-------------------------------------------------------------------------------
fun branchOptions(){
    listAllBranches()
    val option = readNextInt(
        """
                  > ---------------------------------
                  > |   1) Add new Branch           |
                  > |   2) Remove existing Branch   |
                  > |   3) Update existing Branch   |
                  > |   4) Product Options          |
                  > ---------------------------------
         > ==>> """.trimMargin(">"))

    when (option) {
        1 -> addBranch();
        2 -> removeBranch();
        3 -> updateBranch();
        4 -> productOptions();
        else -> println("Invalid option entered: " + option);
    }
}
fun addBranch(){
    listAllBranches()
    val branchName = readNextLine("Enter a name for the branch:")
    val branchLocation = readNextLine("Enter the branch location:")
    val branchManager = readNextLine("Enter the current manager:")
    val estDate = readNextLine("Enter the branch's establishment date:")
    val isAdded = branchAPI.add(Branch(branchName, branchLocation, branchManager, estDate))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}
fun removeBranch(){
    listAllBranches()
    if (branchAPI.numberOfBranches() > 0) {
        val indexToDelete = readNextInt("Enter the index of the branch to delete: ")
        val branchToDelete = branchAPI.deleteBranch(indexToDelete)
        if (branchToDelete != null) {
            println("Delete Successful! Deleted Branch: ${branchToDelete.branchName}")
        } else {
            println("Delete Failed")
        }
    }
}
fun updateBranch(){
    // listBranches()
    if (branchAPI.numberOfBranches() > 0) {
        val indexToUpdate = readNextInt("Enter the index of the branch to update: ")
        if (branchAPI.isValidIndex(indexToUpdate)) {
            val branchName = readNextLine("Enter a name for the branch:")
            val branchLocation = readNextLine("Enter the branch location:")
            val branchManager = readNextLine("Enter the current manager:")
            val estDate = readNextLine("Enter the branch's establishment date:")

            if (branchAPI.updateBranch(indexToUpdate, Branch(branchName, branchLocation, branchManager, estDate))){
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no branches for this index number")
        }
    }
}
//--------------------------------------------------------------------------------------------------------




// Product Options functions------------------------------------------------------------------------------
fun productOptions(){
    val option = readNextInt(
        """
                  > --------------------------------------
                  > |   1) Add new Products to Branch    |
                  > |   2) Delete Products from Branch   |
                  > |   3) Update existing Products      |
                  > --------------------------------------
         > ==>> """.trimMargin(">"))

    when (option) {
        1 -> addProductToBranch();
        2 -> removeProductFromBranch();
        3 -> updateProduct();
        else -> println("Invalid option entered: " + option);
    }
}
private fun addProductToBranch() {
    val branch: Branch? = askUserToChooseBranch()
    if (branch != null) {
        if (branch.addProduct(Product(productName = readNextLine("\t Product Name: "),
                amountRequired = readNextInt("\t Amount Required: "),
                costOfOne = readNextInt("\t Cost of One(in cents): "),
                productCategory = readNextLine("\t Product Category: "))))
            println("Add Successful!")
        else println("Add NOT Successful")
    }
}
fun removeProductFromBranch() {
    val branch: Branch? = askUserToChooseBranch()
    if (branch != null) {
        val product: Product? = askUserToChooseProduct(branch)
        if (product != null) {
            val isDeleted = branch.deleteProduct(product.productId)
            if (isDeleted) {
                println("Delete Successful!")
            } else {
                println("Delete NOT Successful")
            }
        }
    }
}
private fun askUserToChooseProduct(branch: Branch): Product? {
    if (branch.numberOfProducts() > 0) {
        print(branch.listProducts())
        return branch.findOne(readNextInt("\nEnter the id of the Product: "))
    }
    else{
        println ("No Products for chosen Branch")
        return null
    }
}
/*fun updateProduct(){
    val branch: Branch? = askUserToChooseBranch()
    if (branch != null) {
        if (branch.numberOfProducts() > 0) {
            val indexToUpdate = readNextInt("Enter the index of the Product to update: ")
            if (branch.isValidIndex(indexToUpdate)) {
                val productId = readNextInt("Enter the Product's new ID:")
                val productName = readNextLine("Enter the Product's Name:")
                val amountRequired = readNextInt("Enter the amount required:")
                val costOfOne = readNextInt("Enter the cost of just one of the required Product:")
                val productCategory = readNextLine("Enter the Product's Category:")
                //val isProductOrdered = readNextLine("Is the Product ordered? Yes / No:")

                if (branch.updateProduct(indexToUpdate, Product(productId, productName,amountRequired,costOfOne,productCategory))) {
                    println("Update Successful")
                } else {
                    println("Update Failed")
                }
            } else {
                println("There are no products for this index number")
            }
        }
    }
}*/
fun updateProduct() {
    val branch: Branch? = askUserToChooseBranch()
    if (branch != null) {
        val product: Product? = askUserToChooseProduct(branch)
        if (product != null) {
            val newProductId = readNextInt("Enter the Product's new ID:")
            val newProductName = readNextLine("Enter the Product's Name:")
            val newAmountRequired = readNextInt("Enter the amount required:")
            val newCostOfOne = readNextInt("Enter the cost of just one of the required Product:")
            val newProductCategory = readNextLine("Enter the Product's Category:")
            if (branch.updateProduct(product.productId, Product(productId = newProductId,productName=newProductName,amountRequired=newAmountRequired,costOfOne=newCostOfOne,productCategory=newProductCategory))) {
                println("Item contents updated")
            } else {
                println("Item contents NOT updated")
            }
        } else {
            println("Invalid Item Id")
        }
    }
}
private fun askUserToChooseBranch(): Branch? {
    listAllBranches()
    if (branchAPI.numberOfBranches() > 0) {
        val branch = branchAPI.findBranch(readNextInt("\nEnter the id of the branch: "))
        if (branch != null) {
            if (branch.isBranchArchived) {
                println("Branch is NOT Active, it is Archived")
            } else {
                return branch
            }
        } else {
            println("Branch id is not valid")
        }
    }
    return null
}
//-------------------------------------------------------------------------------------------------------



// List Options functions--------------------------------------------------------------------------------
fun listOptions(){
    if (branchAPI.numberOfBranches() > 0) {
        val option = readNextInt(
            """
                  > --------------------------------
                  > |   1) List all Branches       |
                  > --------------------------------
         > ==>> """.trimMargin(">"))

        when (option) {
            1 -> listAllBranches();
            else -> println("Invalid option entered: " + option);
        }
    } else {
        println("Please Add at-least one(1) Branch before moving to this section");
    }
}
fun listAllBranches() {
    println(branchAPI.listAllBranches())
}
//-------------------------------------------------------------------------------------------------------





//Search Options functions-------------------------------------------------------------------------------
fun searchOptions(){
    if (branchAPI.numberOfBranches() > 0) {
        val option = readNextInt(
            """
                  > -----------------------------------
                  > |   1) Search Branch by Name      |
                  > |   2) Search Branch by Location  |
                  > |   3) Search Branch by Manager   |
                  > -----------------------------------
         > ==>> """.trimMargin(">"))

        when (option) {
            1 -> searchBranches();
            2 -> searchBranchLocation();
            3 -> searchBranchManager();
            else -> println("Invalid option entered: " + option);
        }
    } else {
        println("Please Add at-least one(1) Branch before moving to this section");
    }
}
fun searchBranches() {
    val searchName = readNextLine("Please enter  the name you wish to search Branches using: ")
    val searchResults = branchAPI.searchByName(searchName)
    if (searchResults.isEmpty()) {
        println("No branches found under that name")
    } else {
        println(searchResults)
    }
}
fun searchBranchLocation() {
    val searchLocation = readNextLine("Please enter the Location you wish to search Branches using: ")
    val searchResults = branchAPI.searchByLocation(searchLocation)
    if (searchResults.isEmpty()) {
        println("No branches found with that Location")
    } else {
        println(searchResults)
    }
}
fun searchBranchManager() {
    val searchManager = readNextLine("Please enter the Manager you wish to search Branches using: ")
    val searchResults = branchAPI.searchByManager(searchManager)
    if (searchResults.isEmpty()) {
        println("No branches found with that Manager")
    } else {
        println(searchResults)
    }
}
//-------------------------------------------------------------------------------------------------------




